import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotEquals;

/*
 * Note:
 * if you would like to see if a meld is inside a table, make sure to follow the order RBGO
 */

/**
 * AppTest tests out all required tests from the assignment 1 Excel sheet
 *
 * @author Sebastian Gadzinski
 */
public class AppTest extends ThreePlayersNetworkTest {

    public void testInitialPoints1() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R1", "R1", "R2", "R3", "R3", "R4", "R4", "R5", "R5", "R6", "R11", "R12", "R13", "O12")));

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{R1 R1 R2 R3 R3 R4 R4 R5 R5 R6 O12}", "{R11 R12 R13}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();
        localPlayer = commander.getPlayer(0);

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R11", "R12", "R13")));
        assertFalse(localPlayer.getTiles().contains("R11"));
        assertFalse(localPlayer.getTiles().contains("R12"));
        assertFalse(localPlayer.getTiles().contains("R13"));
        assertEquals(localPlayer.getTiles().size(), 11);
    }

    public void testInitialPoints2() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R1", "R1", "R2", "R3", "R3", "R4", "R4", "R5", "R5", "R6", "R12", "B12","G12", "O12")));

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{R1 R1 R2 R3 R3 R4 R4 R5 R5 R6 O12}", "{R12 B12 G12}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();
        localPlayer = commander.getPlayer(0);

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R12", "B12", "G12")));
        assertFalse(localPlayer.getTiles().contains("R12"));
        assertFalse(localPlayer.getTiles().contains("B12"));
        assertFalse(localPlayer.getTiles().contains("G12"));
        assertEquals(localPlayer.getTiles().size(), 11);
    }

    public void testInitialPoints3() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R1", "R1", "R2", "R3", "R3", "R4", "R4", "R9", "R10", "R11", "R12", "R13","G12", "O12")));

        //Then we add it to the list of tiles
        p1.writeToConsole(new String[] {"2", "{R1 R1 R2 R3 R3 R4 R4 G12 O12}", "{R9 R10 R11 R12 R13}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();
        localPlayer = commander.getPlayer(0);

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R9", "R10", "R11", "R12", "R13")));
        assertFalse(localPlayer.getTiles().contains("R9"));
        assertFalse(localPlayer.getTiles().contains("R10"));
        assertFalse(localPlayer.getTiles().contains("R11"));
        assertFalse(localPlayer.getTiles().contains("R12"));
        assertFalse(localPlayer.getTiles().contains("R13"));
        assertEquals(localPlayer.getTiles().size(), 9);
    }

    public void testInitialPoints4() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R1", "R1", "R2", "R3", "R3", "R4", "R4", "R9", "R10", "R11", "R13", "B13", "G13", "O13")));

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{R1 R1 R2 R3 R3 R4 R4 R9 R10 R11}", "{R13 B13 G13 O13}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();
        localPlayer = commander.getPlayer(0);

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R13", "B13", "G13", "O13")));
        assertFalse(localPlayer.getTiles().contains("R13"));
        assertFalse(localPlayer.getTiles().contains("B13"));
        assertFalse(localPlayer.getTiles().contains("G13"));
        assertFalse(localPlayer.getTiles().contains("O13"));
        assertEquals(localPlayer.getTiles().size(), 10);
    }

    public void testInitialPoints5() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R1", "R1", "R2", "R3", "R4", "R5", "R9", "R10", "R11", "R13", "B7", "B8", "B9", "B10")));
        assertEquals(commander.getGameTable().size(), 0);

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{R1 R1 R5 R9 R10 R11 R13 B10}", "{R2 R3 R4} {B7 B8 B9}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();
        localPlayer = commander.getPlayer(0);

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R2", "R3", "R4")));
        assertTrue(localGameTable.contains(Arrays.asList("B7", "B8", "B9")));
        assertFalse(localPlayer.getTiles().contains("R2"));
        assertFalse(localPlayer.getTiles().contains("R3"));
        assertFalse(localPlayer.getTiles().contains("R4"));
        assertFalse(localPlayer.getTiles().contains("B7"));
        assertFalse(localPlayer.getTiles().contains("B8"));
        assertFalse(localPlayer.getTiles().contains("B9"));
        assertEquals(localPlayer.getTiles().size(), 8);
    }

    public void testInitialPoints6() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R1", "R1", "R2", "R3", "R4", "R5", "B2", "B4", "B5", "G4", "O2", "O4", "O5", "O6")));
        assertEquals(commander.getGameTable().size(), 0);

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{R1 R1 R3 O6}", "{R2 B2 O2} {G4 O4 B4 R4} {O5 B5 R5}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();
        localPlayer = commander.getPlayer(0);

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R2", "B2", "O2")));
        assertTrue(localGameTable.contains(Arrays.asList("R4", "B4", "G4", "O4")));
        assertTrue(localGameTable.contains(Arrays.asList("R5", "B5", "O5")));
        assertFalse(localPlayer.getTiles().contains("R2"));
        assertFalse(localPlayer.getTiles().contains("R2"));
        assertFalse(localPlayer.getTiles().contains("B2"));
        assertFalse(localPlayer.getTiles().contains("O2"));
        assertFalse(localPlayer.getTiles().contains("G4"));
        assertFalse(localPlayer.getTiles().contains("O4"));
        assertFalse(localPlayer.getTiles().contains("B4"));
        assertFalse(localPlayer.getTiles().contains("R4"));
        assertFalse(localPlayer.getTiles().contains("O5"));
        assertFalse(localPlayer.getTiles().contains("B5"));
        assertFalse(localPlayer.getTiles().contains("R5"));
        assertEquals(localPlayer.getTiles().size(), 4);
    }


    public void testInitialPoints7() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R1", "R1", "R2", "R3", "R4", "R5", "R8", "G8", "O8", "O9", "O10", "O11", "O12", "O13")));
        assertEquals(commander.getGameTable().size(), 0);

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{R1 R1 R5 O9 O10 O11 O12 O13}", "{R8 G8 O8} {R2 R3 R4}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();
        localPlayer = commander.getPlayer(0);

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R8", "G8", "O8")));
        assertTrue(localGameTable.contains(Arrays.asList("R2", "R3", "R4")));
        assertFalse(localPlayer.getTiles().contains("R8"));
        assertFalse(localPlayer.getTiles().contains("G8"));
        assertFalse(localPlayer.getTiles().contains("O8"));
        assertFalse(localPlayer.getTiles().contains("R2"));
        assertFalse(localPlayer.getTiles().contains("R3"));
        assertFalse(localPlayer.getTiles().contains("R4"));
        assertEquals(localPlayer.getTiles().size(), 8);
    }

    public void testInitialPoints8() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R1", "R1", "R2", "R3", "B2", "B3", "B5", "B6", "B7", "G2", "G3", "G4", "O2", "O3")));
        assertEquals(commander.getGameTable().size(), 0);

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{R1 R1}", "{R2 B2 O2} {G2 G3 G4} {R3 B3 O3} {B5 B6 B7}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();
        localPlayer = commander.getPlayer(0);

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R2", "B2", "O2")));
        assertTrue(localGameTable.contains(Arrays.asList("G2", "G3", "G4")));
        assertTrue(localGameTable.contains(Arrays.asList("R3", "B3", "O3")));
        assertTrue(localGameTable.contains(Arrays.asList("B5", "B6", "B7")));
        assertFalse(localPlayer.getTiles().contains("R2"));
        assertFalse(localPlayer.getTiles().contains("B2"));
        assertFalse(localPlayer.getTiles().contains("O2"));
        assertFalse(localPlayer.getTiles().contains("G2"));
        assertFalse(localPlayer.getTiles().contains("G3"));
        assertFalse(localPlayer.getTiles().contains("G4"));
        assertFalse(localPlayer.getTiles().contains("R3"));
        assertFalse(localPlayer.getTiles().contains("B3"));
        assertFalse(localPlayer.getTiles().contains("B5"));
        assertFalse(localPlayer.getTiles().contains("B6"));
        assertFalse(localPlayer.getTiles().contains("B7"));
        assertEquals(localPlayer.getTiles().size(), 2);
    }

    public void testInitialPointsAndWin() throws IOException, InterruptedException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R2", "B2", "G2", "G3", "G4", "G5", "G6", "G7", "O2", "O4", "O5", "O6", "O7", "O8")));
        commander.sendTestCommand("setPlayerTiles 1 " + new ArrayList<>(Arrays.asList("B1", "B1", "B2", "B2", "B3", "B3", "B4", "B4", "B5", "B5", "B6", "B6", "B7", "B7")));
        commander.sendTestCommand("setPlayerTiles 2 " + new ArrayList<>(Arrays.asList("O1", "O1", "O2", "O2", "O3", "O3", "O4", "O4", "O5", "O5", "O6", "O6", "O7", "O7")));

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{}", "{R2 B2 G2 O2} {G3 G4 G5 G6 G7} {O4 O5 O6 O7 O8}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();

        assertTrue(localGameTable.contains(Arrays.asList("R2", "B2", "G2", "O2")));
        assertTrue(localGameTable.contains(Arrays.asList("G3", "G4", "G5", "G6", "G7")));
        assertTrue(localGameTable.contains(Arrays.asList("O4", "O5", "O6", "O7", "O8")));

        localPlayer = commander.getPlayer(0);
        Player winner = commander.getWinner();

        assertEquals(localPlayer.getTiles().size(), 0); // no tiles in hand
        //As they are two different object I they don't have the same address, therefore I check via name.
        assertEquals(winner.getName(), localPlayer.getName());

        //Reset the server to get rid of the sockets and player info, then start accepting connections again and connect players
        commander.sendTestCommand("hardReset");
        //Let the game server start accepting connections before we start letting players in
        Thread.sleep(1000);
        connectPlayers();
    }

    public void testAfterInitial30_63() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R2", "R3", "R8", "R11", "R12", "R13", "G2", "G3", "G4", "G8", "G9", "G10", "G11", "G12")));
        commander.sendTestCommand("setPlayerTiles 1 " + new ArrayList<>(Arrays.asList("B11", "B12", "B13", "G1", "G1", "G5", "G5", "G6", "G6", "G7", "G7", "G8", "G8", "G9")));
        commander.sendTestCommand("setPlayerTiles 2 " + new ArrayList<>(Arrays.asList("O1", "O2", "O2", "O3", "O4", "O5", "O6", "O7", "O8", "O9", "O10", "O11", "O12", "O13")));

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{R2 R3 R8 G2 G3 G4 G8 G9 G10 G11 G12}", "{R11 R12 R13}"});
        commander.sendTestCommand("round");
        p2.writeToConsole(new String[] {"2", "{G1 G1 G5 G5 G6 G6 G7 G7 G8 G8 G9}", "{B11 B12 B13} {R11 R12 R13}"});
        commander.sendTestCommand("round");
        p3.writeToConsole(new String[] {"2", "{O1 O2 O2 O3 O4 O5 O6 O7 O8 O9 O10}", "{O11 O12 O13} {B11 B12 B13} {R11 R12 R13}"});
        commander.sendTestCommand("round");
        p1.writeToConsole(new String[] {"2", "{R2 R3 R8 G8 G9 G10 G11 G12}", "{G2 G3 G4} {O11 O12 O13} {B11 B12 B13} {R11 R12 R13}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();
        localPlayer = commander.getPlayer(0);

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R11", "R12", "R13")));
        assertTrue(localGameTable.contains(Arrays.asList("B11", "B12", "B13")));
        assertTrue(localGameTable.contains(Arrays.asList("O11", "O12", "O13")));
        assertTrue(localGameTable.contains(Arrays.asList("G2", "G3", "G4")));
        assertEquals(localPlayer.getTiles().size(), 8);
    }

    public void testAfterInitial30_65() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R2", "R3", "R8", "R11", "R12", "R13", "G2", "G3", "G4", "G8", "G9", "G10", "O2", "G12")));
        commander.sendTestCommand("setPlayerTiles 1 " + new ArrayList<>(Arrays.asList("B11", "B12", "B13", "G1", "G1", "G5", "G5", "G6", "G6", "G7", "G7", "G8", "G8", "G9")));
        commander.sendTestCommand("setPlayerTiles 2 " + new ArrayList<>(Arrays.asList("O1", "O2", "O2", "O3", "O4", "O5", "O6", "O7", "O8", "O9", "O10", "O11", "O12", "O13")));

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{R2 R3 R8 G2 G3 G4 G8 G9 G10 O2 G12}", "{R11 R12 R13}"});
        commander.sendTestCommand("round");
        p2.writeToConsole(new String[] {"2", "{G1 G1 G5 G5 G6 G6 G7 G7 G8 G8 G9}", "{B11 B12 B13} {R11 R12 R13}"});
        commander.sendTestCommand("round");
        p3.writeToConsole(new String[] {"2", "{O1 O2 O2 O3 O4 O5 O6 O7 O8 O9 O10}", "{O11 O12 O13} {B11 B12 B13} {R11 R12 R13}"});
        commander.sendTestCommand("round");
        p1.writeToConsole(new String[] {"2", "{R3 R8 G3 G4 G8 G9 G10 G12}", "{G2 R2 O2} {O11 O12 O13} {B11 B12 B13} {R11 R12 R13}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();
        localPlayer = commander.getPlayer(0);

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R11", "R12", "R13")));
        assertTrue(localGameTable.contains(Arrays.asList("B11", "B12", "B13")));
        assertTrue(localGameTable.contains(Arrays.asList("O11", "O12", "O13")));
        assertTrue(localGameTable.contains(Arrays.asList("R2", "G2", "O2")));
        assertEquals(localPlayer.getTiles().size(), 8);

    }


    public void testAfterInitial30_64() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R2", "R3", "R8", "R11", "R12", "R13", "G2", "G3", "G4", "O8", "O9", "O10", "G11", "G12")));
        commander.sendTestCommand("setPlayerTiles 1 " + new ArrayList<>(Arrays.asList("B11", "B12", "B13", "G1", "G1", "G5", "G5", "G6", "G6", "G7", "G7", "G8", "G8", "G9")));
        commander.sendTestCommand("setPlayerTiles 2 " + new ArrayList<>(Arrays.asList("O1", "O2", "O2", "O3", "O4", "O5", "O6", "O7", "O8", "O9", "O10", "O11", "O12", "O13")));

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{R2 R3 R8 G2 G3 G4 O8 O9 O10 G11 G12}", "{R11 R12 R13}"});
        commander.sendTestCommand("round");
        p2.writeToConsole(new String[] {"2", "{G1 G1 G5 G5 G6 G6 G7 G7 G8 G8 G9}", "{B11 B12 B13} {R11 R12 R13}"});
        commander.sendTestCommand("round");
        p3.writeToConsole(new String[] {"2", "{O1 O2 O2 O3 O4 O5 O6 O7 O8 O9 O10}", "{O11 O12 O13} {B11 B12 B13} {R11 R12 R13}"});
        commander.sendTestCommand("round");
        p1.writeToConsole(new String[] {"2", "{R2 R3 R8 G11 G12}", "{G2 G3 G4} {O8 O9 O10} {O11 O12 O13} {B11 B12 B13} {R11 R12 R13}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();
        localPlayer = commander.getPlayer(0);

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R11", "R12", "R13")));
        assertTrue(localGameTable.contains(Arrays.asList("B11", "B12", "B13")));
        assertTrue(localGameTable.contains(Arrays.asList("O11", "O12", "O13")));
        assertTrue(localGameTable.contains(Arrays.asList("O8", "O9", "O10")));
        assertTrue(localGameTable.contains(Arrays.asList("G2", "G3", "G4")));
        assertEquals(localPlayer.getTiles().size(), 5);
    }


    public void testAfterInitial30_66() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R2", "R3", "R8", "R11", "R12", "R13", "G2", "G3", "G4", "G8", "O8", "O10", "O2", "B8")));
        commander.sendTestCommand("setPlayerTiles 1 " + new ArrayList<>(Arrays.asList("B11", "B12", "B13", "G1", "G1", "G5", "G5", "G6", "G6", "G7", "G7", "G8", "G8", "G9")));
        commander.sendTestCommand("setPlayerTiles 2 " + new ArrayList<>(Arrays.asList("O1", "O2", "O2", "O3", "O4", "O5", "O6", "O7", "O8", "O9", "O10", "O11", "O12", "O13")));

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{R2 R3 R8 G2 G3 G4 G8 O8 O10 O2 B8}", "{R11 R12 R13}"});
        commander.sendTestCommand("round");
        p2.writeToConsole(new String[] {"2", "{G1 G1 G5 G5 G6 G6 G7 G7 G8 G8 G9}", "{B11 B12 B13} {R11 R12 R13}"});
        commander.sendTestCommand("round");
        p3.writeToConsole(new String[] {"2", "{O1 O2 O2 O3 O4 O5 O6 O7 O8 O9 O10}", "{O11 O12 O13} {B11 B12 B13} {R11 R12 R13}"});
        commander.sendTestCommand("round");
        p1.writeToConsole(new String[] {"2", "{R3 G3 G4 O10}", "{R2 G2 O2} {R8 B8 G8 O8} {O11 O12 O13} {B11 B12 B13} {R11 R12 R13}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();
        localPlayer = commander.getPlayer(0);

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R11", "R12", "R13")));
        assertTrue(localGameTable.contains(Arrays.asList("B11", "B12", "B13")));
        assertTrue(localGameTable.contains(Arrays.asList("O11", "O12", "O13")));
        assertTrue(localGameTable.contains(Arrays.asList("R2", "G2", "O2")));
        assertTrue(localGameTable.contains(Arrays.asList("R8", "B8", "G8", "O8")));
        assertEquals(localPlayer.getTiles().size(), 4);
    }

    public void testAfterInitial30_67() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R2", "R3", "R8", "R11", "R12", "R13", "G2", "O2", "G4", "O8", "O9", "O10", "G11", "G12")));
        commander.sendTestCommand("setPlayerTiles 1 " + new ArrayList<>(Arrays.asList("B11", "B12", "B13", "G1", "G1", "G5", "G5", "G6", "G6", "G7", "G7", "G8", "G8", "G9")));
        commander.sendTestCommand("setPlayerTiles 2 " + new ArrayList<>(Arrays.asList("O1", "O2", "O2", "O3", "O4", "O5", "O6", "O7", "O8", "O9", "O10", "O11", "O12", "O13")));

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{R2 R3 R8 G2 O2 G4 O8 O9 O10 G11 G12}", "{R11 R12 R13}"});
        commander.sendTestCommand("round");
        p2.writeToConsole(new String[] {"2", "{G1 G1 G5 G5 G6 G6 G7 G7 G8 G8 G9}", "{B11 B12 B13} {R11 R12 R13}"});
        commander.sendTestCommand("round");
        p3.writeToConsole(new String[] {"2", "{O1 O2 O2 O3 O4 O5 O6 O7 O8 O9 O10}", "{O11 O12 O13} {B11 B12 B13} {R11 R12 R13}"});
        commander.sendTestCommand("round");
        p1.writeToConsole(new String[] {"2", "{R3 R8 G4 G11 G12}", "{R2 G2 O2} {O8 O9 O10} {O11 O12 O13} {B11 B12 B13} {R11 R12 R13}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();
        localPlayer = commander.getPlayer(0);

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R11", "R12", "R13")));
        assertTrue(localGameTable.contains(Arrays.asList("B11", "B12", "B13")));
        assertTrue(localGameTable.contains(Arrays.asList("O11", "O12", "O13")));
        assertTrue(localGameTable.contains(Arrays.asList("O8", "O9", "O10")));
        assertTrue(localGameTable.contains(Arrays.asList("R2", "G2", "O2")));
        assertEquals(localPlayer.getTiles().size(), 5);

    }

    //R = H | O = D | B = S | G = C  {2C 2H 2D} {8D 9D 10D}
    public void testAfterInitial30_68() throws IOException, InterruptedException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R2", "G2", "O2", "R3", "G3", "O3", "G8", "G9", "G10", "G11", "G12", "R11", "R12", "R13")));
        commander.sendTestCommand("setPlayerTiles 1 " + new ArrayList<>(Arrays.asList("B11", "B12", "B13", "G1", "G1", "G5", "G5", "G6", "G6", "G7", "G7", "G8", "G8", "G9")));
        commander.sendTestCommand("setPlayerTiles 2 " + new ArrayList<>(Arrays.asList("O1", "O2", "O2", "O3", "O4", "O5", "O6", "O7", "O8", "O9", "O10", "O11", "O12", "O13")));

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{R2 G2 O2 R3 G3 O3 G8 G9 G10 G11 G12}", "{R11 R12 R13}"});
        commander.sendTestCommand("round");
        p2.writeToConsole(new String[] {"2", "{G1 G1 G5 G5 G6 G6 G7 G7 G8 G8 G9}", "{B11 B12 B13} {R11 R12 R13}"});
        commander.sendTestCommand("round");
        p3.writeToConsole(new String[] {"2", "{O1 O2 O2 O3 O4 O5 O6 O7 O8 O9 O10}", "{O11 O12 O13} {B11 B12 B13} {R11 R12 R13}"});
        commander.sendTestCommand("round");
        p1.writeToConsole(new String[] {"2", "{}", "{R2 G2 O2} {R3 G3 O3} {G8 G9 G10 G11 G12} {O11 O12 O13} {B11 B12 B13} {R11 R12 R13}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();
        localPlayer = commander.getPlayer(0);

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R11", "R12", "R13")));
        assertTrue(localGameTable.contains(Arrays.asList("B11", "B12", "B13")));
        assertTrue(localGameTable.contains(Arrays.asList("O11", "O12", "O13")));
        assertTrue(localGameTable.contains(Arrays.asList("R3", "G3", "O3")));
        assertTrue(localGameTable.contains(Arrays.asList("G8", "G9", "G10", "G11", "G12")));
        assertTrue(localGameTable.contains(Arrays.asList("R2", "G2", "O2")));
        assertEquals(localPlayer.getTiles().size(), 0);

        //Because the player won the game we have to reset the server

        //Reset the server to get rid of the sockets and player info, then start accepting connections again and connect players
        commander.sendTestCommand("hardReset");
        //Let the game server start accepting connections before we start letting players in
        Thread.sleep(1000);
        connectPlayers();
    }

    public void testAPlayerChooseToDraw() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("G2", "R2", "O2", "R3", "G3", "O3", "O8", "O9", "O10", "R8", "R9", "R10", "G12", "R7")));
        //Check that there are only 14 cards before we draw
        assertEquals(commander.getPlayer(0).getTiles().size(), 14);
        //See if current player can play melds
        assertTrue(commander.sendTestCommand("getIfPlayerCanDraw"));
        //Write [draw]
        p1.writeToConsole(new String[] {"1"});
        commander.sendTestCommand("round");

        //Ensure table is empty and player drawn a card
        assertEquals(commander.getGameTable().size(), 0);
        assertEquals(commander.getPlayer(0).getTiles().size(), 15);
    }

    public void testHasToDrawOwnCase() throws IOException {
        System.out.println("ITS THIS TEST");
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("G2", "G2", "O2", "R3", "B3", "B3", "R5", "B6", "O7", "R9", "R10", "G11", "B12", "B13")));

        //Check that there are only 14 cards before we draw
        assertEquals(commander.getPlayer(0).getTiles().size(), 14);
        //See if current player can play melds
        assertFalse(commander.getIfPlayerCanPlayMelds());
        //Write [draw]
        p1.writeToConsole(new String[] {"1"});
        commander.sendTestCommand("round");

        localPlayer = commander.getPlayer(0);

        //Ensure table is empty and player drawn a card
        assertEquals(commander.getGameTable().size(), 0);
        assertEquals(localPlayer.getTiles().size(), 15);

        //Because we drew a tile at the start of the game, we have to figure which one we have drawn
        List<String> diffTiles = Utils.getDifferenceInLists(localPlayer.getTiles(),new ArrayList<>(Arrays.asList("G2", "G2", "O2", "R3", "B3", "B3", "R5", "B6", "O7", "R9", "R10", "G11", "B12", "B13")));

        //Make sure the tile drawn is not G2 or B3 (2C or 3S)
        assertNotEquals("G2", diffTiles.get(0));
        assertNotEquals("B3", diffTiles.get(0));
    }

    public void testWinnerWithReports() throws IOException, InterruptedException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("G1", "G2", "O2", "R3", "B3", "B3", "R5", "B6", "O7", "R9", "R10", "B11", "B12", "B13")));
        commander.sendTestCommand("setPlayerTiles 1 " + new ArrayList<>(Arrays.asList("R2", "B2", "G2", "O2", "G3", "G4", "G6", "G7", "O4", "O5", "O6", "O7", "O8", "O9")));
        commander.sendTestCommand("setPlayerTiles 2 " + new ArrayList<>(Arrays.asList("R4", "O6", "B6", "B7", "R7", "G8", "R10", "R11", "R12", "R13", "B10", "B11", "B12", "B13")));

        //p1 draws 2H
        commander.sendTestCommand("draw R2");
        commander.sendTestCommand("endTurn");
        //p2 draws 5C
        commander.sendTestCommand("draw G5");
        commander.sendTestCommand("endTurn");
        //Write [play melds, updatedHand, updatedTable]
        p3.writeToConsole(new String[] {"2", "{R4 O6 B6 B7 R7 G8}", "{R10 R11 R12 R13} {B10 B11 B12 B13}"});
        commander.sendTestCommand("round");
        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{G1 R3 B3 B3 R5 B6 O7 R9 R10}", "{R2 G2 O2} {B11 B12 B13} {R10 R11 R12 R13} {B10 B11 B12 B13}"});
        commander.sendTestCommand("round");
        //Reuse the table tiles
        p2.writeToConsole(new String[] {"2", "{}", "{R2 G2 B2 O2} {G3 G4 G5 G6 G7} {O4 O5 O6 O7 O8 O9} {R2 G2 O2} {B11 B12 B13} {R10 R11 R12 R13} {B10 B11 B12 B13}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();

        assertEquals(commander.getPlayer(0).score(), -47);
        assertEquals(commander.getPlayer(1).score(), 0);
        assertEquals(commander.getPlayer(2).score(), -38);
        //Because the player won the game we have to reset the server

        //Reset the server to get rid of the sockets and player info, then start accepting connections again and connect players
        commander.sendTestCommand("hardReset");
        //Let the game server start accepting connections before we start letting players in
        Thread.sleep(1000);
        connectPlayers();
    }

    public void test87() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R11", "G11", "B11", "O11", "G3", "O3", "B4", "G9", "G10", "B7", "G12", "R11", "R12", "R13")));
        commander.sendTestCommand("setPlayerTiles 1 " + new ArrayList<>(Arrays.asList("R12", "B12", "G12", "G1", "B11", "G11", "G5", "G6", "G6", "G7", "G7", "G8", "G8", "G9")));
        commander.sendTestCommand("setPlayerTiles 2 " + new ArrayList<>(Arrays.asList("O1", "O2", "O2", "O3", "O4", "O5", "O6", "O7", "O8", "O9", "O10", "O11", "O12", "O13")));

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{G3 O3 B4 G9 G10 B7 G12 R11 R12 R13}", "{R11 G11 B11 O11}"});
        commander.sendTestCommand("round");
        p2.writeToConsole(new String[] {"2", "{G1 B11 G11 G5 G6 G6 G7 G7 G8 G8 G9}", "{R12 B12 G12} {R11 G11 B11 O11}"});
        commander.sendTestCommand("round");
        p3.writeToConsole(new String[] {"2", "{O1 O2 O2 O3 O4 O5 O6}", "{O7 O8 O9 O10 O11 O12 O13} {R12 B12 G12} {R11 G11 B11 O11}"});
        commander.sendTestCommand("round");
        //Write [draw]
        p1.writeToConsole(new String[] {"1"});
        commander.sendTestCommand("round");
        //Reuse the table tiles
        p2.writeToConsole(new String[] {"2", "{G1 G5 G6 G6 G7 G7 G8 G8 G9}", "{O7 O8 O9 O10 O11 O12 O13} {R12 B12 G12} {R11 B11 G11} {B11 G11 O11}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("B11", "G11", "O11")));
        assertTrue(localGameTable.contains(Arrays.asList("R11", "B11", "G11")));
        assertTrue(localGameTable.contains(Arrays.asList("R12", "B12", "G12")));
        assertTrue(localGameTable.contains(Arrays.asList("O7", "O8", "O9", "O10", "O11", "O12", "O13")));
        assertEquals(localGameTable.size(), 4);
    }

    public void test_91() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R11", "G11", "B11", "O11", "G3", "O3", "B4", "G9", "G10", "B7", "G12", "B11", "G11", "R13")));
        commander.sendTestCommand("setPlayerTiles 1 " + new ArrayList<>(Arrays.asList("R12", "B12", "G12", "G1", "R13", "R12", "G5", "G6", "G6", "G7", "G7", "G8", "G8", "G9")));
        commander.sendTestCommand("setPlayerTiles 2 " + new ArrayList<>(Arrays.asList("O1", "O2", "O2", "O3", "O4", "O5", "O6", "O7", "O8", "O9", "O10", "O11", "O12", "O13")));

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{G3 O3 B4 G9 G10 B7 G12 B11 G11 R13}", "{R11 G11 B11 O11}"});
        commander.sendTestCommand("round");
        p2.writeToConsole(new String[] {"2", "{G1 R13 R12 G5 G6 G6 G7 G7 G8 G8 G9}", "{R12 B12 G12} {R11 G11 B11 O11}"});
        commander.sendTestCommand("round");
        p3.writeToConsole(new String[] {"2", "{O1 O2 O2 O3 O4 O5 O6}", "{O7 O8 O9 O10 O11 O12 O13} {R12 B12 G12} {R11 G11 B11 O11}"});
        commander.sendTestCommand("round");
        //Write [draw]
        p1.writeToConsole(new String[] {"1"});
        commander.sendTestCommand("round");
        //Reuse the table tiles
        p2.writeToConsole(new String[] {"2", "{G1 G5 G6 G6 G7 G7 G8 G8 G9}", "{O7 O8 O9 O10 O11 O12 O13} {R12 B12 G12} {R11 R12 R13} {B11 G11 O11}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("B11", "G11", "O11")));
        assertTrue(localGameTable.contains(Arrays.asList("R11", "R12", "R13")));
        assertTrue(localGameTable.contains(Arrays.asList("R12", "B12", "G12")));
        assertTrue(localGameTable.contains(Arrays.asList("O7", "O8", "O9", "O10", "O11", "O12", "O13")));
        assertEquals(localGameTable.size(), 4);
    }

    public void test_96() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R11", "G11", "B11", "O11", "G3", "O3", "B4", "R7", "B7", "B7", "G12", "B11", "G11", "R13")));
        commander.sendTestCommand("setPlayerTiles 1 " + new ArrayList<>(Arrays.asList("R12", "B12", "G12", "G1", "R13", "B13", "G5", "G6", "G6", "G7", "G7", "G8", "G8", "G9")));
        commander.sendTestCommand("setPlayerTiles 2 " + new ArrayList<>(Arrays.asList("O1", "O2", "O2", "O3", "O4", "O5", "O6", "O7", "O8", "O9", "O10", "O11", "O12", "O13")));

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{G3 O3 B4 R7 B7 B7 G12 B11 G11 R13}", "{R11 G11 B11 O11}"});
        commander.sendTestCommand("round");
        p2.writeToConsole(new String[] {"2", "{G1 R13 B13 G5 G6 G6 G7 G7 G8 G8 G9}", "{R12 B12 G12} {R11 G11 B11 O11}"});
        commander.sendTestCommand("round");
        p3.writeToConsole(new String[] {"2", "{O1 O2 O2 O3 O4 O5 O6}", "{O7 O8 O9 O10 O11 O12 O13} {R12 B12 G12} {R11 G11 B11 O11}"});
        commander.sendTestCommand("round");
        //Reuse the table tiles 96
        p1.writeToConsole(new String[] {"2", "{G3 O3 B4 B7 G12 B11 G11 R13}", "{O7 B7 R7} {O8 O9 O10 O11 O12 O13} {R12 B12 G12} {R11 G11 B11 O11}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R7", "B7", "O7")));
        assertTrue(localGameTable.contains(Arrays.asList("R12", "B12", "G12")));
        assertTrue(localGameTable.contains(Arrays.asList("R11", "B11", "G11", "O11")));
        assertTrue(localGameTable.contains(Arrays.asList("O8", "O9", "O10", "O11", "O12", "O13")));
        assertEquals(localGameTable.size(), 4);
    }

    public void test_98() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R11", "G11", "B11", "O11", "G3", "O3", "B4", "R7", "B7", "B7", "G12", "B13", "G11", "R13")));
        commander.sendTestCommand("setPlayerTiles 1 " + new ArrayList<>(Arrays.asList("R12", "B12", "G12", "G1", "R13", "B13", "G5", "G6", "G6", "G7", "G7", "G8", "G8", "G9")));
        commander.sendTestCommand("setPlayerTiles 2 " + new ArrayList<>(Arrays.asList("O1", "O2", "O2", "O3", "O4", "O5", "O6", "O7", "O8", "O9", "O10", "O11", "O12", "O13")));

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{G3 O3 B4 R7 B7 B7 G12 B13 G11 R13}", "{R11 G11 B11 O11}"});
        commander.sendTestCommand("round");
        p2.writeToConsole(new String[] {"2", "{G1 R13 B13 G5 G6 G6 G7 G7 G8 G8 G9}", "{R12 B12 G12} {R11 G11 B11 O11}"});
        commander.sendTestCommand("round");
        p3.writeToConsole(new String[] {"2", "{O1 O2 O2 O3 O4 O5 O6}", "{O7 O8 O9 O10 O11 O12 O13} {R12 B12 G12} {R11 G11 B11 O11}"});
        commander.sendTestCommand("round");
        //Reuse the table tiles
        p1.writeToConsole(new String[] {"2", "{G3 O3 B4 B7 B7 R7 G12 G11}", "{O7 O8 O9 O10 O11 O12} {R13 B13 O13} {R12 B12 G12} {R11 G11 B11 O11}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R13", "B13", "O13")));
        assertTrue(localGameTable.contains(Arrays.asList("R12", "B12", "G12")));
        assertTrue(localGameTable.contains(Arrays.asList("R11", "B11", "G11", "O11")));
        assertTrue(localGameTable.contains(Arrays.asList("O7", "O8", "O9", "O10", "O11", "O12")));
        assertEquals(localGameTable.size(), 4);
    }

    public void test_101() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R11", "G11", "B11", "O11", "G3", "O3", "B4", "R7", "B7", "B7", "G12", "O9", "G11", "O8")));
        commander.sendTestCommand("setPlayerTiles 1 " + new ArrayList<>(Arrays.asList("R12", "B12", "G12", "G1", "R13", "B13", "G5", "G6", "G6", "G7", "G7", "G8", "G8", "G9")));
        commander.sendTestCommand("setPlayerTiles 2 " + new ArrayList<>(Arrays.asList("O1", "O2", "O2", "O3", "O4", "O5", "O6", "O7", "O8", "O9", "O10", "O11", "O12", "O13")));

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{G3 O3 B4 R7 B7 B7 G12 O9 G11 O8}", "{R11 G11 B11 O11}"});
        commander.sendTestCommand("round");
        p2.writeToConsole(new String[] {"2", "{G1 R13 B13 G5 G6 G6 G7 G7 G8 G8 G9}", "{R12 B12 G12} {R11 G11 B11 O11}"});
        commander.sendTestCommand("round");
        p3.writeToConsole(new String[] {"2", "{O1 O2 O2 O3 O4 O5 O6}", "{O7 O8 O9 O10 O11 O12 O13} {R12 B12 G12} {R11 G11 B11 O11}"});
        commander.sendTestCommand("round");
        //Reuse the table tiles
        p1.writeToConsole(new String[] {"2", "{G3 O3 B4 B7 B7 R7 G12 G11}", "{O7 O8 O9} {O11 O12 O13} {O8 O9 O10} {R12 B12 G12} {R11 G11 B11 O11}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("O8", "O9", "O10")));
        assertTrue(localGameTable.contains(Arrays.asList("R12", "B12", "G12")));
        assertTrue(localGameTable.contains(Arrays.asList("R11", "B11", "G11", "O11")));
        assertTrue(localGameTable.contains(Arrays.asList("O7", "O8", "O9")));
        assertTrue(localGameTable.contains(Arrays.asList("O11", "O12", "O13")));
        assertEquals(localGameTable.size(), 5);
    }

    public void test_simple_3() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R11", "G11", "B11", "O11", "O4", "R4", "R8", "B1", "B2", "B3", "B4", "B5", "B6", "B7")));
        commander.sendTestCommand("setPlayerTiles 1 " + new ArrayList<>(Arrays.asList("R12", "B12", "G12", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "G9", "G10", "G11")));
        commander.sendTestCommand("setPlayerTiles 2 " + new ArrayList<>(Arrays.asList("R4", "G4", "B4", "R5", "R6", "R7", "O1", "O2", "O3", "O4", "O5", "O6", "O7", "O8")));

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{O4 R4 R8 B1 B2 B3 B4 B5 B6 B7}", "{R11 G11 B11 O11}"});
        commander.sendTestCommand("round");
        p2.writeToConsole(new String[] {"2", "{G1 G2 G3 G4 G5 G6 G7 G8 G9 G10 G11}", "{R12 B12 G12} {R11 G11 B11 O11}"});
        commander.sendTestCommand("round");
        p3.writeToConsole(new String[] {"2", "{O1 O2 O3 O4 O5 O6 O7 O8}", "{R4 B4 G4} {R5 R6 R7} {R12 B12 G12} {R11 G11 B11 O11}"});
        commander.sendTestCommand("round");
        //Reuse the table tiles
        p1.writeToConsole(new String[] {"2", "{B1 B2 B3 B4 B5 B6 B7}", "{R4 B4 G4 O4} {R4 R5 R6 R7 R8} {R12 B12 G12} {R11 G11 B11 O11}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R4", "B4", "G4", "O4")));
        assertTrue(localGameTable.contains(Arrays.asList("R4", "R5", "R6", "R7", "R8")));
        assertTrue(localGameTable.contains(Arrays.asList("R12", "B12", "G12")));
        assertTrue(localGameTable.contains(Arrays.asList("R11", "B11", "G11", "O11")));
        assertEquals(localGameTable.size(), 4);
    }

    public void test_simple_1() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R11", "G11", "B11", "O11", "G1", "B12", "B13", "B1", "B2", "B3", "B4", "B5", "B6", "B7")));
        commander.sendTestCommand("setPlayerTiles 1 " + new ArrayList<>(Arrays.asList("G11", "G12", "G13", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "G9", "G10", "G11")));
        commander.sendTestCommand("setPlayerTiles 2 " + new ArrayList<>(Arrays.asList("R4", "G4", "B4", "R5", "R6", "R7", "O1", "O2", "O3", "O4", "O5", "O6", "O7", "O8")));

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{G11 G1 B12 B13 B1 B2 B3 B4 B5 B6 B7}", "{R11 O11 B11}"});
        commander.sendTestCommand("round");
        p2.writeToConsole(new String[] {"2", "{G1 G2 G3 G4 G5 G6 G7 G8 G9 G10 G11}", "{G11 G12 G13} {R11 O11 B11}"});
        commander.sendTestCommand("round");
        p3.writeToConsole(new String[] {"2", "{O1 O2 O3 O4 O5 O6 O7 O8}", "{R4 B4 G4} {R5 R6 R7} {G11 G12 G13} {R11 O11 B11}"});
        commander.sendTestCommand("round");
        //Reuse the table tiles
        p1.writeToConsole(new String[] {"2", "{G11 B1 B2 B3 B4 B5 B6 B7}", "{R4 B4 G4} {R5 R6 R7} {G12 G13 G1} {R11 G11 O11} {B11 B12 B13}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R4", "B4", "G4")));
        assertTrue(localGameTable.contains(Arrays.asList("R5", "R6", "R7")));
        assertTrue(localGameTable.contains(Arrays.asList("G12", "G13", "G1")));
        assertTrue(localGameTable.contains(Arrays.asList("B11", "B12", "B13")));
        assertTrue(localGameTable.contains(Arrays.asList("R11", "G11", "O11")));
        assertEquals(localGameTable.size(), 5);
    }

    public void test_simple_2() throws IOException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R11", "G11", "B11", "O11", "G10", "B10", "R13", "B1", "B2", "B3", "B4", "B5", "B6", "B7")));
        commander.sendTestCommand("setPlayerTiles 1 " + new ArrayList<>(Arrays.asList("O12", "B12", "G12", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "G9", "G10", "G11")));
        commander.sendTestCommand("setPlayerTiles 2 " + new ArrayList<>(Arrays.asList("R7", "R8", "R9", "R10", "R11", "R12", "O1", "O2", "O3", "O4", "O5", "O6", "O7", "O8")));

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{R11 G10 B10 R13 B1 B2 B3 B4 B5 B6 B7}", "{O11 B11 G11}"});
        commander.sendTestCommand("round");
        p2.writeToConsole(new String[] {"2", "{G1 G2 G3 G4 G5 G6 G7 G8 G9 G10 G11}", "{O12 B12 G12} {O11 B11 G11}"});
        commander.sendTestCommand("round");
        p3.writeToConsole(new String[] {"2", "{O1 O2 O3 O4 O5 O6 O7 O8}", "{R7 R8 R9 R10 R11 R12} {O12 B12 G12} {O11 B11 G11}"});
        commander.sendTestCommand("round");
        //Reuse the table tiles
        p1.writeToConsole(new String[] {"2", "{R11 B1 B2 B3 B4 B5 B6 B7}", "{R7 R8 R9} {R10 G10 B10} {R11 R12 R13} {O12 B12 G12} {O11 B11 G11}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("R7", "R8", "R9")));
        assertTrue(localGameTable.contains(Arrays.asList("R10", "B10", "G10")));
        assertTrue(localGameTable.contains(Arrays.asList("R11", "R12", "R13")));
        assertTrue(localGameTable.contains(Arrays.asList("B12", "G12", "O12")));
        assertTrue(localGameTable.contains(Arrays.asList("B11", "G11", "O11")));
        assertEquals(localGameTable.size(), 5);
    }

    public void test_complex_case() throws IOException, InterruptedException {
        commander.sendTestCommand("reset");

        commander.sendTestCommand("setPlayerTiles 0 " + new ArrayList<>(Arrays.asList("R11", "G11", "B11", "R3", "R4", "R5", "B1", "B2", "B3", "B4", "G4", "B4", "O5", "O13")));
        commander.sendTestCommand("setPlayerTiles 1 " + new ArrayList<>(Arrays.asList("O1", "O2", "O3", "O4", "R3", "O3", "B3", "G3", "G3", "G4", "G5", "B5", "B6", "B7")));
        commander.sendTestCommand("setPlayerTiles 2 " + new ArrayList<>(Arrays.asList("R1", "R2", "R4", "R5", "R6", "R7", "O1", "O2", "O3", "O4", "O5", "O6", "O7", "O8")));

        //Write [play melds, updatedHand, updatedTable]
        p1.writeToConsole(new String[] {"2", "{G4 B4 O5 O13}", "{R11 G11 B11} {R3 R4 R5} {B1 B2 B3 B4}"});
        commander.sendTestCommand("round");
        p2.writeToConsole(new String[] {"2", "{B5 B6 B7}", "{O1 O2 O3 O4} {R3 O3 B3 G3} {G3 G4 G5} {R11 G11 B11} {R3 R4 R5} {B1 B2 B3 B4}"});
        commander.sendTestCommand("round");
        //[draw]
        p3.writeToConsole(new String[] {"1"});
        commander.sendTestCommand("round");
        //Reuse the table tiles
        p1.writeToConsole(new String[] {"2", "{}", "{O4 G4 B4} {O13 O1 O2 O3} {O5 R5 G5} {B1 B2 B3} {R4 G4 B4} {R3 B3 G3} {O3 R3 G3} {R11 G11 B11}"});
        commander.sendTestCommand("round");

        localGameTable = commander.getGameTable();

        //Ensure that tiles were played
        assertTrue(localGameTable.contains(Arrays.asList("B4", "G4", "O4")));
        assertTrue(localGameTable.contains(Arrays.asList("O13", "O1", "O2", "O3")));
        assertTrue(localGameTable.contains(Arrays.asList("R5", "G5", "O5")));
        assertTrue(localGameTable.contains(Arrays.asList("B1", "B2", "B3")));
        assertTrue(localGameTable.contains(Arrays.asList("R4", "B4", "G4")));
        assertTrue(localGameTable.contains(Arrays.asList("R3", "B3", "G3")));
        assertTrue(localGameTable.contains(Arrays.asList("R3", "G3", "O3")));
        assertTrue(localGameTable.contains(Arrays.asList("R11", "B11", "G11")));
        assertEquals(localGameTable.size(), 8);

        //Reset the server to get rid of the sockets and player info, then start accepting connections again and connect players
        commander.sendTestCommand("hardReset");
        //Let the game server start accepting connections before we start letting players in
        Thread.sleep(1000);
        connectPlayers();
    }
}
