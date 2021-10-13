import junit.framework.TestCase;

import java.io.IOException;
import java.util.List;

/**
 * Initializes 3 players with a GameServerCommander for a test case file
 *
 * @author Sebastian Gadzinski
 */
public class ThreePlayersNetworkTest extends TestCase{
    protected static Player p1, p2, p3, localPlayer;
    protected List<List<String>> localGameTable;
    protected static GameServerCommander commander;

    static {
        try {
            commander = new GameServerCommander();
            p1 = new Player("Player 1");
            p2 = new Player("Player 2");
            p3 = new Player("Player 3");
            connectPlayers();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connects all players to the game
     *
     */
    protected static void connectPlayers() throws InterruptedException {
        Thread p1Thread, p2Thread, p3Thread;
        p1Thread = new Thread(p1);
        p2Thread = new Thread(p2);
        p3Thread = new Thread(p3);
        p1Thread.start();
        Thread.sleep(500);
        p2Thread.start();
        Thread.sleep(500);
        p3Thread.start();
        Thread.sleep(500);
    }

}
