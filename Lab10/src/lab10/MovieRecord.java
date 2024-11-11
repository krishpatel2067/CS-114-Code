/* Starter code given by the professor */
package lab10;
import java.util.LinkedList;
import java.util.Scanner;

public class MovieRecord implements Comparable<MovieRecord> {
    private String title;
    private int rating;

    MovieRecord(String t, int r) {
        title = t;
        rating = r;
    }

    public String getTitle() {
        return title;
    }

    public int getRating() {
        return rating;
    }

    public String toString() {
        String s = title+"\t"+rating;
        return s;
    }

    public int compareTo(MovieRecord m) {
        return this.rating - m.rating;
    }

}
