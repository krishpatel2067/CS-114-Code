package lab6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
//import java.util.ArrayList;

/*
 * Identify all words of length len that have the same pronunciation if you
 * remove one of its letters. Ignore words containing double adjacent letters
 * or punctuation.
 */


public class SuperfluousLetter {

    public static void main(String[] args) {
        OALDictionary<String, Pronunciation> PDict = new OALDictionary<String, Pronunciation>();
        //UALDictionary<String, Pronunciation> PDict = new UALDictionary<String, Pronunciation>();
        File file = new File("cmudict.0.7a.txt");

        System.out.println("Enter the length of a word: ");
        Scanner inp = new Scanner(System.in);
        int len = inp.nextInt();  // consider only words of length len

        long start = System.currentTimeMillis();

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.substring(0, 3).equals(";;;"))
                    continue; // skip comment lines
                Pronunciation p = new Pronunciation(line);
                if(!p.getWord().matches("[A-Z]+")) {
                    continue; // avoid strings containing punctuation
                }
                // We need only consider words of length len or len-1.
                if ((p.getWord().length() < len - 1)
                        || (p.getWord().length() > len))
                    continue;
                PDict.insert(p.getWord(), p);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        long middle = System.currentTimeMillis();

        System.out.println("Loaded dictionary.");
        for (Pronunciation p : PDict.values()) {
            String w = p.getWord();
            if(w.length() != len) continue;
            boolean doubleLetter = false;
            for(int i=1; i<len; i++)
                if(w.charAt(i-1) == w.charAt(i)) doubleLetter = true;
            if(doubleLetter) continue;
            if (w.length() == len) {
//                for(int k=1; k<=len; ++k) {
//                    String w1 = w.substring(0,k-1)+ w.substring(k); // word obtained by removing k-th letter
//                    Pronunciation p1 = PDict.find(w1);
//                    if ((p1 != null)
//                            && (p.getPhonemes().equals(p1.getPhonemes()))) {
//                        System.out.println("An answer is: " + w+" "+w1);
//                    }
//                }
            	
            	String w0 = w.substring(1);							// remove 1st letter
            	String w1 = w.substring(0, 1) + w.substring(2);		// remove 2nd letter
            	Pronunciation p0 = PDict.find(w0);
            	Pronunciation p1 = PDict.find(w1);
            	
            	// for p0
            	if (p0 != null && p1 != null &&
            			p.getPhonemes().equals(p0.getPhonemes()) &&
            			p.getPhonemes().equals(p1.getPhonemes()))
            		System.out.println("An answer is: "+w+" "+w0+" "+w1);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Run times: load dictionary= " + (middle - start)
                           + " process= " + (end - middle) + " total= " + (end - start));
    }
}
