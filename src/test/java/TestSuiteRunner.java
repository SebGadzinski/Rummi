import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Starts the GameServer and runs all test case files in the TestSuite class
 *
 * @author Sebastian Gadzinski
 */
public class TestSuiteRunner {

    public static void main(String[] args) {
        String resultsOfFailedTests = "";

        //Create the GameServer that all tests will use and manipulate
        GameServer gameServer = new GameServer();
        Thread gameServerThread = new Thread(gameServer);
        gameServerThread.start();

        //Runs all tests in TestSuite
        Result result = JUnitCore.runClasses(TestSuite.class);

        //Collect all failures to print out for feedback
        for (Failure failure : result.getFailures()) {
            resultsOfFailedTests += "\nTest: " + failure.getTestHeader() + "\n" + failure.toString();
        }

        System.out.println("\nAll Test Passed: " + result.wasSuccessful()
                + "\nTests Passed: " + (result.getRunCount() - result.getFailureCount())
                + "\nTests Failed: " + result.getFailureCount()
                + "\nFailed Tests Feedback: " + resultsOfFailedTests);

        System.exit(0);
    }

}
