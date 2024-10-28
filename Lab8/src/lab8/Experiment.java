package lab8;
import java.util.ArrayList;
import java.text.NumberFormat;

/* Program sorts many arrays using one of the "Mystery" sorts
 * sort0-sort4.
 */

public class Experiment {

    public static void main(String[] args) {
        MysterySorts srts = new MysterySorts();
        int n;
        int REP = 100; // how many times to repeat experiment
        long start, now, elapsed;
        Integer[] A;

        int n0 = 10000;

        int s=0; // try sort(s,.)
        System.out.println("Testing sort "+s);
        n =  n0;
        A = new Integer[n];

        // first try already sorted
        start = System.currentTimeMillis();
        for (int rep = 0; rep < REP; ++rep) {
            for (int j = 0; j < n; ++j) A[j] = j;
            srts.sort(s,A);
        }
        now = System.currentTimeMillis();
        elapsed = now - start;
        System.out.println("Sorted short array time: "+elapsed);
        System.out.println("Sorted short array avg time: "+ (double)elapsed/REP);

        // now do longer arrays
        n =  2*n0;
        A = new Integer[n];

        start = System.currentTimeMillis();
        for (int rep = 0; rep < REP; ++rep) {
            //srts.shuffleArray(A);
            for (int j = 0; j < n; ++j) A[j] = j;
            srts.sort(s,A);
        }
        now = System.currentTimeMillis();
        elapsed = now - start;
        System.out.println("Sorted long array time: "+elapsed);
        System.out.println("Sorted long array avg time: "+ (double)elapsed/REP);

        // now shuffled
        n =  n0;
        A = new Integer[n];

        start = System.currentTimeMillis();
        for (int rep = 0; rep < REP; ++rep) {
            for (int j = 0; j < n; ++j) A[j] = j;
            srts.shuffleArray(A);
            srts.sort(s,A);
        }
        now = System.currentTimeMillis();
        elapsed = now - start;
        System.out.println("Shuffled short array time: "+elapsed);
        System.out.println("Shuffled short array avg time: "+ (double)elapsed/REP);

        // now do longer arrays
        n =  2*n0;
        A = new Integer[n];

        start = System.currentTimeMillis();
        for (int rep = 0; rep < REP; ++rep) {
            for (int j = 0; j < n; ++j) A[j] = j;
            srts.shuffleArray(A);
            srts.sort(s,A);
        }
        now = System.currentTimeMillis();
        elapsed = now - start;
        System.out.println("Shuffled long array time: "+elapsed);
        System.out.println("Shuffled long array avg time: "+ (double)elapsed/REP);

    }
}
