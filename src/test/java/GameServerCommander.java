import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * GameServerCommander attaches itself to the GameServer via socket, and feeds it commands
 *
 * @author Sebastian Gadzinski
 */
public class GameServerCommander {
    private Socket socket;
    private ObjectInputStream dIn;
    private ObjectOutputStream dOut;

    public GameServerCommander() throws IOException {
        socket = new Socket("localhost", Config.GAME_SERVER_TEST_PORT_NUMBER);
        dOut = new ObjectOutputStream(socket.getOutputStream());
        dIn = new ObjectInputStream(socket.getInputStream());
        System.out.println("TestCaseGameServerCommander is on");
    }

    /**
     * Sends a test command to the game server
     *
     * @param testCommand - Command to be given to GameServer
     */
    public boolean sendTestCommand(String testCommand){
        try {
            dOut.writeUTF(testCommand);
            dOut.flush();
            String isOk = dIn.readUTF();
            if(!isOk.equals("ok")) return false;
        } catch (Exception e) {
            System.out.println("String was not sent");
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Sends a test command to the game server and receives an object
     *
     * @param testCommand - Command to be given to GameServer
     */
    public String sendTestCommandAndReceiveObject(String testCommand){
        try {
            dOut.writeUTF(testCommand);
            dOut.flush();
            String jsonObject = dIn.readUTF();
            return jsonObject;
        } catch (Exception e) {
            System.out.println("String was not sent");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ================================================COMMONLY_USED================================================
     */

    public List<List<String>> getGameTable() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = sendTestCommandAndReceiveObject("getGameTable");
        List<List<String>> gameTable = mapper.readValue(jsonString, List.class);
        return gameTable;
    }

    public Player getPlayer(int player) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = sendTestCommandAndReceiveObject("getPlayer " + player).split(",\"tilesString\"" )[0] + "}";
        Player returningPlayer = mapper.readValue(jsonString, Player.class);
        return returningPlayer;
    }


    public Player getWinner() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = sendTestCommandAndReceiveObject("getWinner").split(",\"tilesString\"" )[0] + "}";
        Player returningPlayer = mapper.readValue(jsonString, Player.class);
        return returningPlayer;
    }


    public boolean getIfPlayerCanPlayMelds() throws IOException {
        dOut.writeUTF("getIfPlayerCanPlayMelds");
        dOut.flush();
        String isOk = dIn.readUTF();
        return isOk.equals("ok");
    }
}
