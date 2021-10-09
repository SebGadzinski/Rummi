import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Note:
 * if you would like to see if a meld is inside a table, make sure to follow the order RBGO
 */

/**
 * NetworkTest tests out the sequence of the Rummikub  game
 *
 * @author Sebastian Gadzinski
 */
public class NetworkTest extends ThreePlayersNetworkTest {

    public void testSequence() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R1", "R1", "R2", "R3", "R3", "R4", "R4", "R5", "R5", "R6", "R12", "B12", "G12", "O12")));
        commander.sendTestCommand("setPlayerTiles 1 " + new ArrayList<>(Arrays.asList("R11", "R12", "R13", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "G9", "G10", "G11")));
        commander.sendTestCommand("setPlayerTiles 2 " + new ArrayList<>(Arrays.asList("R2", "R13", "B2", "B3", "B3", "B4", "B4", "B5", "B5", "B6", "B13", "G2", "G4", "G13")));

        commander.sendTestCommand("draw");
        commander.sendTestCommand("endTurn");

        //Ensure table is empty and player drawn a card
        assertEquals(commander.getGameTable().size(), 0);
        assertEquals(commander.getPlayer(0).getTiles().size(), 15);

        //Write [play melds, updatedHand, updatedTable, end turn]
        p2.writeToConsole(new String[]{"2", "{G1 G2 G3 G4 G5 G6 G7 G8 G9 G10 G11}", "{R11 R12 R13}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();
        localPlayer = commander.getPlayer(1);

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R11", "R12", "R13")));
        assertFalse(localPlayer.getTiles().contains("R11"));
        assertFalse(localPlayer.getTiles().contains("R12"));
        assertFalse(localPlayer.getTiles().contains("R13"));

        //Write [play melds, updatedHand, updatedTable, end turn]
        p3.writeToConsole(new String[]{"2", "{B3 B3 B4 B4 B5 B5 B6 G4}", "{R11 R12 R13} {R13 B13 G13} {R2 B2 G2}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();
        localPlayer = commander.getPlayer(2);

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R13", "B13", "G13")));
        assertTrue(localGameTable.contains(Arrays.asList("R2", "B2", "G2")));
        assertFalse(localPlayer.getTiles().contains("R13"));
        assertFalse(localPlayer.getTiles().contains("B13"));
        assertFalse(localPlayer.getTiles().contains("G13"));
        assertFalse(localPlayer.getTiles().contains("R2"));
        assertFalse(localPlayer.getTiles().contains("B2"));
        assertFalse(localPlayer.getTiles().contains("G2"));

        //Because we drew a tile at the start of the game, we have to figure which one we have drawn
        localPlayer = commander.getPlayer(0);

        List<String> diffTiles = Utils.getDifferenceInLists(localPlayer.getTiles(), new ArrayList<>(Arrays.asList("R1", "R1", "R2", "R3", "R3", "R4", "R4", "R5", "R5", "R6", "R12", "B12", "G12", "O12")));

        //Then we add it to the list of tiles
        p1.writeToConsole(new String[]{"2", "{R1 R1 R2 R3 R3 R4 R4 R5 R5 R6 " + diffTiles.get(0) + "}", "{R11 R12 R13} {R13 B13 G13} {R2 B2 G2} {R12 B12 G12 O12}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();
        localPlayer = commander.getPlayer(0);

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R12", "B12", "G12", "O12")));
        assertFalse(localPlayer.getTiles().contains("R12"));
        assertFalse(localPlayer.getTiles().contains("B12"));
        assertFalse(localPlayer.getTiles().contains("O12"));
        assertFalse(localPlayer.getTiles().contains("G12"));
    }
}