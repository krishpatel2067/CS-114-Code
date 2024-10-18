package lab6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
//import java.util.ArrayList;

/*
 * Identify English words with the most homophones.
 */
public class MostHomophones {

  public static void main(String[] args) {
    BST<String, Pronunciation> PDict = new BST<String, Pronunciation>();
    //UALDictionary<String, Pronunciation> PDict = new UALDictionary<String,
    //Pronunciation>();
    //OALDictionary<String, Pronunciation> PDict = new OALDictionary<String, Pronunciation>();
    File file = new File("cmudict.0.7a.txt");
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
        PDict.insert(p.getPhonemes(), p);
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
    System.out.println("The height is: "+PDict.height());

    int count = 0;
    int maxCount = 0;
    Pronunciation P = null;
    for (Pronunciation p : PDict.values()) {
      count = 0;
      for (Pronunciation _ : PDict.findAll(p.getPhonemes()))
        ++count;
      if (count > maxCount) {
        maxCount = count;
        P = p;
      }
    }
    //System.out.println("Max. homophones= " + maxCount);
    System.out.println(maxCount);
    for (Pronunciation q : PDict.findAll(P.getPhonemes()))
      System.out.println(q.getPhonemes() + ", " + q.getWord());
    long elapsed = System.currentTimeMillis() - startTime;
    System.out.println("Elapsed time: " + elapsed);
  }
}
