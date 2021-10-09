import java.util.ArrayList;
import java.util.List;

/**
 * Utility functions for Rummikub
 *
 * @author Sebastian Gadzinski
 */
public class Utils {

    /**
     * Checks to see if a target list is a subset of a source list
     *
     * @param source - source list
     * @param target - subset list
     */
    static <T> boolean listIsSubsetOfOtherList(List<T> source, List<T> target){
        List<T> copySource = new ArrayList<T>(source);
        List<T> targetSource = new ArrayList<T>(target);
        for (T t : targetSource){
            if(copySource.contains(t)) copySource.remove(t);
            else return false;
        }
        return true;
    }

    /**
     * Gives the difference between objects in two lists
     *
     * @param source - source list
     * @param target - subset list
     */
    static <T> List<T> getDifferenceInLists(List<T> source, List<T> target){
        List<T> copySource = new ArrayList<T>(source);
        List<T> targetSource = new ArrayList<T>(target);
        for (T t : targetSource){
            if(copySource.contains(t)) copySource.remove(t);
        }
        return copySource;
    }
}
