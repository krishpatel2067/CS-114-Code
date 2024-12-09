package ps6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
//import java.util.ArrayList;

/*
 * Identify English words with the most homophones.
 */
public class FewestHomophones {

  public static void main(String[] args) {
    BST<String, Pronunciation> PDict = new BST<String, Pronunciation>();
    //UALDictionary<String, Pronunciation> PDict = new UALDictionary<String,
    //Pronunciation>();
    //OALDictionary<String, Pronunciation> PDict = new OALDictionary<String, Pronunciation>();
    File file = new File("cmudict.0.7a.txt");
    final int REQ_LEN = 5;
    // File file = new File("shuffledDictionary.txt");

    long startTime = System.currentTimeMillis();

    // Read in the cmu pronunciation dictionary.
    try {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.substring(0, 3).equals(";;;"))
          continue; // skip comment lines
        Pronunciation p = new Pronunciation(line);
        
        if (p.getWord().length() == REQ_LEN)
        	PDict.insert(p.getPhonemes(), p);
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    int count = 0;
    int minCount = Integer.MAX_VALUE;
    Pronunciation P = null;
    for (Pronunciation p : PDict.values()) {
      count = 0;
      for (Pronunciation _ : PDict.findAll(p.getPhonemes()))
        ++count;
      if (count < minCount) {
        minCount = count;
        P = p;
      }
    }
    System.out.println("Min homophones= " + minCount);
    System.out.println(minCount);
    for (Pronunciation q : PDict.findAll(P.getPhonemes()))
      System.out.println(q.getPhonemes() + ", " + q.getWord());
    long elapsed = System.currentTimeMillis() - startTime;
    System.out.println("Elapsed time: " + elapsed);
  }
}
