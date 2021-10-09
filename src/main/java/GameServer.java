import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/*
 * Interface:
 * 1. draw
 * 2. play melds from hand OR table
 *      - Then you can stop editing the table once you type end
 */

/**
 * GameServer designed for Rummikub
 *
 * @author Sebastian Gadzinski
 */
public class GameServer implements Runnable{
    private Player[] players = new Player[Config.GAME_NUMBER_OF_PLAYERS];
    private Socket[] sockets = new Socket[Config.GAME_NUMBER_OF_PLAYERS];
    private ServerSocket server;
    private String[] table = {"1st", "2nd", "3rd"};
    private Game game;
    private boolean isRunning = true;
    private int numPlayers;

    public GameServer() {
        System.out.println("Starting the game server");
        this.numPlayers = 0;

        try {
            this.server = new ServerSocket(Config.GAME_SERVER_PORT_NUMBER);
        } catch (IOException e) {
            System.out.println("Server fails to open");
        }
    }

    /**
     * ================================================_GS_================================================
     */

    public Player getPlayer(int i) {
        return players[i % Config.GAME_NUMBER_OF_PLAYERS];
    }

    public Game getGame() {
        return game;
    }

    /*
     * ================================================_CLASS_RELATED_================================================
     */

    /**
     * Removes all players and sockets
     *
     */
    public void hardReset(){
        this.players = new Player[Config.GAME_NUMBER_OF_PLAYERS];
        this.sockets = new Socket[Config.GAME_NUMBER_OF_PLAYERS];
        this.numPlayers = 0;
    }

    /**
     * Resets players tiles and game
     *
     */
    public void reset(){
        for (Player p : players){
            p.reset();
        }
        this.game = new Game(players);
    }

    /**
     * Ends the current players turn
     *
     */
    private void endTurn() {
        System.out.println("Current table at the end of turn: " + game.getTableString(game.getTable()));
        System.out.println("Current hand of current player " + players[game.getCurr() % Config.GAME_NUMBER_OF_PLAYERS].getName() + " at the end of turn: " + players[game.getCurr() % 3].getTilesString());
        System.out.println("\r\n");
        game.endTurn();
    }

    /*
     * ================================================_NETWORKING_================================================
     */
    /**
     * Runs rounds till someone wins or out of cards
     *
     */
    private void start() {
        while (round()){}
    }

    /**
     * Plays a round of Rummikub for the current player
     *
     */
    private boolean round(){
        for (Socket s: sockets) {
            BufferedWriter writer;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                writer.write("Player " + players[game.getCurr() % Config.GAME_NUMBER_OF_PLAYERS].getName() + "'s turn" + "\r\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Player " + players[game.getCurr() % Config.GAME_NUMBER_OF_PLAYERS].getName() + "'s turn");
        // write info to specific player
        Socket currPlayer = sockets[game.getCurr() % Config.GAME_NUMBER_OF_PLAYERS];
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(currPlayer.getOutputStream()));
            writer.write("Current Table: " + game.getTableString(game.getTable()) + "\r\n");
            writer.write("Current tiles in hand: " + players[game.getCurr() % Config.GAME_NUMBER_OF_PLAYERS].getTilesString() + "\r\n");
            writer.write("Type 1: Draw from the deck\r\n");
            if (!players[game.getCurr() % Config.GAME_NUMBER_OF_PLAYERS].hasToDraw() || !game.getInitial()) {
                writer.write("Type 2: Play melds from hand OR table\r\n");
            }
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Receive a decision from a player
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(currPlayer.getInputStream()));
            String str = reader.readLine();
            doOperation(Integer.parseInt(str));
            if (Integer.parseInt(str) == 1) {
                writer = new BufferedWriter(new OutputStreamWriter(currPlayer.getOutputStream()));
                writer.write("Current tiles in hand: " + players[(game.getCurr() - 1) % Config.GAME_NUMBER_OF_PLAYERS].getTilesString() + "\r\n");
                writer.flush();
            }
            if (game.getWinner() != null) {
                System.out.println("We have a winner: " + game.getWinner().getName());
                for (int i = 0; i < sockets.length; i++) {
                    writer = new BufferedWriter(new OutputStreamWriter(sockets[i].getOutputStream()));
                    if (players[i] == game.getWinner()) {
                        writer.write("You are the winner" + "\r\n");
                    } else {
                        writer.write("Player: " + game.getWinner().getName() + " is the winner" + "\r\n");
                    }
                    writer.write("END");
                    writer.write("\n");
                    writer.flush();
                }
                return false;
            }
            if(game.isOutOfCards()){
                System.out.println("We Ran Out Of Cards: ");
                for (int i = 0; i < sockets.length; i++) {
                    writer = new BufferedWriter(new OutputStreamWriter(sockets[i].getOutputStream()));
                    writer.write("Nobody Wins!");
                    writer.write("END");
                    writer.write("\n");
                    writer.flush();
                }
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Plays a round of Rummikub for the current player
     *
     * @param op - Operation requested by the player
     */
    private void doOperation(int op) {
        switch (op) {
            case 1:
                game.draw();
                break;
            case 2:
                if(!players[game.getCurr() % Config.GAME_NUMBER_OF_PLAYERS].hasToDraw() || !game.getInitial())
                    play();
                break;
        }
        game.endTurn();
    }

    /**
     * Allows current user to play with table
     *
     */
    private void play() {
        Socket currPlayer = sockets[game.getCurr() % Config.GAME_NUMBER_OF_PLAYERS];
        try {
            while(true) {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(currPlayer.getOutputStream()));
                writer.write("Current Table: " + game.getTableString(game.getTable()) + "\r\n");
                writer.write("Current Hand: " + players[game.getCurr() % Config.GAME_NUMBER_OF_PLAYERS].getTilesString() + "\r\n");
                BufferedReader reader = new BufferedReader(new InputStreamReader(currPlayer.getInputStream()));
                writer.write("EDIT" + "\r\n");
                writer.flush();

                String newHand = reader.readLine();
                if(newHand.equals("end")) break;
                String newTable = reader.readLine();

                System.out.println("Received possible updated Table: " + newTable + "\r\n");
                System.out.println("Received possible updated Hand: " + newHand + "\r\n");

                if (game.updateTable(newTable, newHand)) {
                    //If user has the right to update the table he must have put over 30 points
                    game.setInitial(game.getCurr() % Config.GAME_NUMBER_OF_PLAYERS);
                    writer.write("Update Table Successful" + "\r\n");
                    writer.flush();
                    break;
                } else {
                    writer.write("Update Table Incorrect" + "\r\n");
                    writer.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts accepting connections to GameServers server
     *
     */
    public void acceptConnections() {
        //To give each player when they connect start tiles, this needs to be at the start
        this.game = new Game(players);

        System.out.println("Waiting for players...");
        while (numPlayers < Config.GAME_NUMBER_OF_PLAYERS) {
            try {
                Socket s = server.accept();
                sockets[numPlayers] = s;
                ObjectInputStream dIn = new ObjectInputStream(s.getInputStream());
                players[numPlayers] = (Player) dIn.readObject();
                System.out.println("Player " + players[numPlayers].getName() + " has joined the game");
                ObjectOutputStream dOut = new ObjectOutputStream(s.getOutputStream());
                players[numPlayers].setTiles(game.getStartTilesFromDeck());
                dOut.writeObject("You are " + table[numPlayers] + " player in this game");
                dOut.flush();
                numPlayers++;
            } catch (IOException e) {
                System.out.println("Fail to have " + Config.GAME_NUMBER_OF_PLAYERS + "players");
            } catch (ClassNotFoundException e) {
                System.out.println("Player cannot connect");
            }
        }

        System.out.println("We have three connected players, now let's get started");
    }

    /**
     * Closes the server
     *
     */
    private void close() {
        try {
            server.close();
        } catch (IOException e) {
            System.out.println("Error happened when closing the game");
        }
    }

    /*
     * ================================================_RUNNERS_================================================
     */

    /**
     * For testing purposes
     *
     */
    @Override
    public void run() {
        Config.TESTING_MODE = true;
        ServerSocket gameCommanderServerSocket = null;
        try {
            gameCommanderServerSocket = new ServerSocket(Config.GAME_SERVER_TEST_PORT_NUMBER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(isRunning){
            try {
                //Wait for a test case to connect
                Socket gameCommanderSocket = gameCommanderServerSocket.accept();
                ObjectInputStream din = new ObjectInputStream(gameCommanderSocket.getInputStream());
                ObjectOutputStream dOut = new ObjectOutputStream(gameCommanderSocket.getOutputStream());

                acceptConnections();
                System.out.println("Game Commander Socket : " + gameCommanderSocket.isClosed());

                //While the test case is connected allow it to make commands
                while(!gameCommanderSocket.isClosed()){
                    String testCommand = din.readUTF();

                    System.out.println("\nGame Server received command : " + testCommand);

                    if(testCommand.equals("round")) round();
                    else if (testCommand.equals("endTurn")) endTurn();
                    else if (testCommand.equals("reset")) reset();
                    else if (testCommand.contains("draw")){
                        if (testCommand.equals("draw")) game.draw();
                        else{
                            String tile = testCommand.split(" ")[1];
                            if (game.verifyTile(tile)){
                                game.draw(tile);
                            }
                        }
                    }
                    else if (testCommand.equals("hardReset")){
                        hardReset();
                        //Send to allow players to join
                        dOut.writeUTF("ok");
                        dOut.flush();
                        acceptConnections();
                        continue;
                    }
                    else if (testCommand.contains("getIfPlayerCanPlayMelds")){
                        if (players[game.getCurr() % Config.GAME_NUMBER_OF_PLAYERS].hasToDraw()){
                            dOut.writeUTF("no");
                            dOut.flush();
                            continue;
                        }
                    }
                    else if (testCommand.contains("getPlayer")){
                        ObjectMapper objectMapper = new ObjectMapper();
                        dOut.writeUTF(objectMapper.writeValueAsString(getPlayer(Integer.parseInt(testCommand.replaceAll("[^0-9]", "")))));
                        dOut.flush();
                        continue;
                    }
                    else if (testCommand.contains("getGameTable")){
                        ObjectMapper objectMapper = new ObjectMapper();
                        dOut.writeUTF(objectMapper.writeValueAsString(getGame().getTable()));
                        dOut.flush();
                        continue;
                    }
                    else if (testCommand.contains("getWinner")){
                        ObjectMapper objectMapper = new ObjectMapper();
                        dOut.writeUTF(objectMapper.writeValueAsString(getGame().getWinner()));
                        dOut.flush();
                        continue;
                    }
                    else if (testCommand.contains("setPlayerTiles")){
                        //find out the number of the player
                        String[] arguments = testCommand.split("\\s+", 3);
                        int playerNum = Integer.parseInt(arguments[1]);
                        String[] tiles = (arguments[2].substring(1, arguments[2].length() - 1)).split(", ");
                        getPlayer(playerNum).setTiles(game.getStartTilesFromDeck(new ArrayList<>(List.of(tiles))));
                    }

                    dOut.writeUTF("ok");
                    dOut.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Runs the game server for one game of Rummikub
     *
     */
    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
        gs.start();
        System.out.println("End of the game");
        gs.close();
    }
}

