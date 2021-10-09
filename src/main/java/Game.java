import java.io.Serializable;
import java.util.*;

/**
 * Game logic designed for Rummikub
 *
 * @author Sebastian Gadzinski
 */
public class Game implements Serializable {
    static final int START_TILES_NUM = 14;
    private Player[] players;
    private boolean[] initial = {true, true, true};
    private List<List<String>> table;
    private Player winner;
    private LinkedList<String> gameTiles;
    private int curr;
    private boolean isOutOfCards = false;

    public Game(Player[] players) {
        this.players = players;
        this.table = new ArrayList<>();
        this.gameTiles = new LinkedList<>();
        gameTiles.addAll(Config.tiles);
        Collections.shuffle(gameTiles);
    }

    /**
     * ================================================_GS_================================================
     */

    public int getCurr() {
        return curr;
    }

    public Player getWinner() {
        return winner;
    }

    public boolean isOutOfCards() {
        return isOutOfCards;
    }

    public boolean getInitial() {
        return initial[curr % Config.GAME_NUMBER_OF_PLAYERS];
    }

    public List<List<String>> getTable() {
        return table;
    }

    public void setTable(List<List<String>> table) {
        this.table = table;
    }

    /*
     * ================================================_CLASS_RELATED_================================================
     */

    /**
     * Draws a tile from the deck
     *
     */
    public void draw() {
        String tile = getRandomTileFromDeck();
        if (tile == null) isOutOfCards = true;
        else players[curr % Config.GAME_NUMBER_OF_PLAYERS].addToTiles(tile);
    }

    /**
     * Draws a specific tile from the deck
     *
     * @param tile - tile to be drawn
     */
    public void draw(String tile) {
        if(!grabTileFromDeck(tile)){
            System.out.println("Could Not Grab tile: " + tile);
        }
        if (tile == null) isOutOfCards = true;
        else players[curr % Config.GAME_NUMBER_OF_PLAYERS].addToTiles(tile);
    }

    /**
     * Creates a string from the table
     *
     * @param table - table in string form
     * @return a table in string form
     */
    public String getTableString(List<List<String>> table) {
        StringBuilder sb = new StringBuilder();
        if (table == null || table.size() == 0) return "{}";
        for (int i = 0; i < table.size(); i++) {
            sb.append("{");
            for (int j = 0; j < table.get(i).size(); j++) {
                sb.append(table.get(i).get(j));
                if (j != table.get(i).size() - 1) {
                    sb.append(" ");
                }
            }
            sb.append("} ");
        }
        return sb.toString();
    }

    /**
     * Draws a specific tile from the deck
     *
     * @param tile - tile to be drawn
     * @return if grabbed tile requested
     */
    private boolean grabTileFromDeck(String tile) {
        //Tiles shuffled at start of game so you can just pop them like a stack
        if (gameTiles.size() == 0) return false;
        return gameTiles.remove(tile);
    }

    /**
     * Gets random start tiles from deck and removes them from deck
     *
     * @return list of start tiles
     */
    public List<String> getStartTilesFromDeck() {
        List<String> startTiles = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < START_TILES_NUM; i++) {
            int index = r.nextInt(gameTiles.size());
            startTiles.add(gameTiles.get(index));
            gameTiles.remove(index);
        }
        Game.sortTiles(startTiles);
        return startTiles;
    }

    /**
     * Gets requested start tiles from deck and removes them from deck
     *
     * @return list of start tiles
     */
    public List<String> getStartTilesFromDeck(List<String> listOfTilesToTake) {
        for (String tile : listOfTilesToTake) {
            gameTiles.remove(tile);
        }
        return listOfTilesToTake;
    }

    /**
     * Gets a random tile from a deck
     *
     * @return a random tile
     */
    public String getRandomTileFromDeck(){
        //Tiles shuffled at start so you can just pop them like a stack
        if (gameTiles.size() == 0) return null;
        return gameTiles.removeFirst();
    }

    /**
     * Sorts tiles by RBGO 1-13 (USED FOR HAND (PLAYERS TILES)
     *
     * @param tiles - tiles to be sorted
     */
    public static void sortTiles(List<String> tiles) {
        HashMap<Character, Integer> map = new HashMap();
        map.put('R', 1);
        map.put('B', 2);
        map.put('G', 3);
        map.put('O', 4);
        tiles.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.charAt(0) == o2.charAt(0)) {
                    return Integer.parseInt(o1.substring(1)) - Integer.parseInt(o2.substring(1));
                } else {
                    return map.get(o1.charAt(0)) - map.get(o2.charAt(0));
                }
            }
        });
    }

    /**
     * Sets a boolean for a user stating it has put over 30 points onto the board allowing him to always play with the table
     *
     * @param index - players index
     */
    public void setInitial(int index){
        if(index < 0 || index > Config.GAME_NUMBER_OF_PLAYERS) return;
        initial[index] = false;
    }

    /**
     * Ends the current players turn
     *
     */
    public void endTurn() {
        if (win()) {
            winner = players[curr % Config.GAME_NUMBER_OF_PLAYERS];
        }
        curr++;
    }

    /**
     * Ends the current players turn
     *
     * @return if current player won
     */
    public boolean win() {
        return players[curr % Config.GAME_NUMBER_OF_PLAYERS].getTiles().size() == 0;
    }

    /**
     * Updates the current table if all verification tests pass for the new table and the  new hand
     *
     * @param newTableString - Rummikub  table
     * @param newHandString - players tiles
     * @return if the table with hand are ok
     */
    public boolean updateTable(String newTableString, String newHandString) {
        System.out.println("Updating Table...");
        List<String> oldHand = players[getCurr() % Config.GAME_NUMBER_OF_PLAYERS].getTiles();

        //Parse the strings to objects
        List<List<String>> newTable = parseTable(newTableString);
        List<String> newHand = parseHand(newHandString);

        System.out.println("New Table : " + getTableString(newTable));
        System.out.println("Old Table : " + getTableString(table));
        System.out.println("New Hand : " + newHand);
        System.out.println("Old Hand : " + oldHand);

        //Verify that the new hand can come from the old hand
        if (!Utils.listIsSubsetOfOtherList(oldHand, newHand)) return false;
        System.out.println("Passed new hand is subset of old hand");

        //Get the tiles used in the new table
        List<String> usedTiles = Utils.getDifferenceInLists(oldHand, newHand);
        System.out.println("Used tiles: " + usedTiles);

        List<String> allTilesInNewTable = new ArrayList<>(), allTilesInOldTable = new ArrayList<>();

        //Collect all tiles from old table
        for (List<String> list : table){
            allTilesInOldTable.addAll(list);
        }

        //Add the used tiles from the hand
        allTilesInOldTable.addAll(usedTiles);

        //Collect all tiles from new table
        for (List<String> list : newTable){
            allTilesInNewTable.addAll(list);
        }

        //See if they match
        Collections.sort(allTilesInNewTable);
        Collections.sort(allTilesInOldTable);
        System.out.println("All tiles in new table: " + allTilesInNewTable);
        System.out.println("All tiles in old table + used tiles: " + allTilesInOldTable);
        if(!allTilesInNewTable.equals(allTilesInOldTable)) return false;
        System.out.println("Passed all tiles of new table are old tiles + tiles used");

        //Check if all melds in the new table are acceptable
        for (List<String> meld : newTable){
            if(!verifyMeld(meld)) return false;
        }
        System.out.println("Passed all melds verified");

        //New table and hand are verified
        setTable(newTable);
        players[curr % Config.GAME_NUMBER_OF_PLAYERS].setTiles(newHand);

        return true;
    }

    /**
     * Verifies a tile
     *
     * @param tile - tile to verify
     * @return if the tile is ok
     */
    public boolean verifyTile(String tile){
        if (tile.length() < 2 || "RGBO".indexOf(tile.charAt(0)) == -1 || !Character.isDigit(tile.charAt(1))
        || getNumberFromTile(tile) > 13 || getNumberFromTile(tile) < 1) return false;
        return true;
    }

    /**
     * Verifies a meld
     *
     * @param meld - tile to verify
     * @return if meld is ok
     */
    public boolean verifyMeld(List<String> meld){
        System.out.println("Verifying meld: " + meld);
        if (meld.isEmpty() || meld.size() < 3) return false;
        System.out.println("Meld passed qualification");

        //Ensure all tiles start with [RGBO] and have a number of [1-13]
        for (String tile : meld){
            System.out.println("Verifying tile: " + tile);
            if(!verifyTile(tile)) return false;
        }
        System.out.println("All tiles in meld passed");

        //Same Type
        if(meld.get(0).charAt(0) == meld.get(1).charAt(0)){
            System.out.println("All types in meld the same");
            for(int i = 0; i < meld.size()-1; i++) {
                //Melds are sorted when parsed so we can assume it is in increasing order
                if (getNumberFromTile(meld.get(i + 1)) == 1) {
                    if (getNumberFromTile(meld.get(i)) != 13) return false;
                } else {
                    if (!(getNumberFromTile(meld.get(i)) == getNumberFromTile(meld.get(i + 1)) - 1)) return false;
                }
            }
        }
        //Different Types
        else{
            System.out.println("All types in meld different");
            for(int i = 0; i < meld.size()-1; i++) {
                //Ensure there can only be 4 of the same number of different types in a meld
                if(i > 4) return false;
                //Melds are sorted when parsed so we can assume it is in increasing order
                if (!(getNumberFromTile(meld.get(i)) == getNumberFromTile(meld.get(i+1))))return false;
            }
        }

        System.out.println("Meld Verified");
        //Meld is verified
        return true;
    }

    /**
     * Extracts number from tile
     *
     * @param tile - tile extract number from
     * @return number in tile
     */
    private int getNumberFromTile(String tile){
        if (tile.length() <= 1) return -1;
        return Integer.parseInt(tile.replaceAll("[\\D]", ""));
    }

    /**
     * Parse table string into a table
     *
     * @param newTable - new table created by current players
     * @return table
     */
    public List<List<String>> parseTable(String newTable) {
        List<List<String>> returningTable = new ArrayList<>();

        for (int i = 0; i < newTable.length(); i++){
            List<String> currentMeld = new ArrayList<>();

            //Go to start of a meld
            while(true){
                if (i >= newTable.length() || newTable.charAt(i) == '{') break;
                i++;
            }

            //Go to next character
            i++;

            //Collect tiles in meld until meld is closed
            StringBuilder tile = new StringBuilder();
            while(true) {
                if (newTable.length() <= i || newTable.charAt(i) == '}') {
                    break;
                }
                if ("RGBO".indexOf(newTable.charAt(i)) != -1) {
                    //Add the Type
                    tile.append(newTable.charAt(i));
                    //Add the first digit
                    tile.append(newTable.charAt(i + 1));
                    //If the tile is double-digit, add the next digit
                    if ("0123".indexOf(newTable.charAt(i + 2)) != -1) tile.append(newTable.charAt(i + 2));
                    //Add the tile to the meld
                    currentMeld.add(tile.toString());
                    sortMeld(currentMeld);
                    tile.delete(0, tile.length());
                }
                i++;
            }
            sortMeld(currentMeld);
            returningTable.add(currentMeld);
        }
        return returningTable;
    }

    /**
     * Sorts a meld and keeps its structure (ie {R12 R13 R1 R2}
     *
     * @param meld - meld to sort
     * @return table
     */
    public void sortMeld(List<String> meld){
        String [] kings = new String [] {"R13", "B13", "G13", "O13"};
        String [] aces = new String [] {"R1", "B1", "G1", "O1"};
        for (int i = 0; i < aces.length; i++){
            if (meld.contains(aces[i])){
                //If the meld contains the king before the ace, keep it as so
                if (Collections.indexOfSubList(meld, new ArrayList<>(Arrays.asList(kings[i], aces[i]))) != -1){
                    return;
                }
            }
        }
        Game.sortTiles(meld);
    }

    /**
     * Parse hand string into a players hand (tiles)
     *
     * @param newHand - new hand string given by current player
     * @return hand (tiles)
     */
    public List<String> parseHand(String newHand) {
        List<String> returningHand = new ArrayList<>();
        if (newHand == null || newHand.length() < 2 || newHand == "{}") return returningHand;
        int i = 0;

        //Go to start of hand
        while(true){
            if (i >= newHand.length() || newHand.charAt(i) == '{') break;
            i++;
        }

        //Go to next character
        i++;

        //Collect tiles in hand until meld is closed
        StringBuilder tile = new StringBuilder();
        while(true) {
            if (newHand.length() <= i || newHand.charAt(i) == '}') {
                break;
            }
            if ("RGBO".indexOf(newHand.charAt(i)) != -1) {
                //Add the Type
                tile.append(newHand.charAt(i));
                //Add the first digit
                tile.append(newHand.charAt(i + 1));
                //If the tile is double-digit, add the next digit
                if ("0123".indexOf(newHand.charAt(i + 2)) != -1) tile.append(newHand.charAt(i + 2));
                //Add the tile to the meld
                returningHand.add(tile.toString());
                sortMeld(returningHand);
                tile.delete(0, tile.length());
            }
            i++;
        }

        Game.sortTiles(returningHand);
        return returningHand;
    }

}
