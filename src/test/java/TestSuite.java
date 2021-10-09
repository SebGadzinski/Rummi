import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * TestSuite is a test suite fore all tests. All testcase files should be inside the Suite
 *
 * @author Sebastian Gadzinski
 */

@RunWith(Suite.class)
@Suite.SuiteClasses( {AppTest.class, NetworkTest.class} )
public class TestSuite {

}