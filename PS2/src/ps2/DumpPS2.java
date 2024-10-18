package ps2;

public class DumpPS2 {

	public static void main(String[] args) {

	}

//	public static void insertBlanks(String str, int i)
//	{
//		// akingandqueen i = -1
//		// (a)kingandqueen i = 0	// real starting point
//		// a kingandqueen
//		// a (k)ingandqueen i = 0
//		// a (ki)ngandqueen i = 1  wordSoFar = "ki"
//		// a (kin)gandqueen i = 2
//		// a (king)andqueen i = 3
//		// a king andqueen
//		// a king (a)ndqueen i = 0
//		// a king (an)dqueen i = 1
//		// a king (and)queen i = 2
//		// a king and queen
//		// a king and (q)ueen i = 0
//		// a king and (qu)een i = 1
//		// a king and (que)en i = 2
//		// a king and (quee)n i = 3
//		// a king and (queen) i = 4
//		// a king and queen
//		// ------ keep interating until the current selection ISN'T a word
//		
//		if (i >= str.length())
//			return;
//		
//		String wordSoFar = str.substring(0, i + 1);
//		
//		if (words.contains(wordSoFar))
//		{
//			System.out.println(wordSoFar + " is a word, but checking next character...");
//			
//			if (i + 2 > str.length() || !words.contains(str.substring(0, i + 2)))			// then count as word found
//			{
//				System.out.println("WORD: " + wordSoFar + " ");
//				wordsFound.add(wordSoFar);
//				insertBlanks(str.substring(i + 1), 0);
//			}
//			else
//			{
//				System.out.println("Next letter still makes it word, moving on...");
//				insertBlanks(str, i + 1);
//			}
//		}
//		else
//		{
//			System.out.println(wordSoFar + " is NOT a word, moving on...");
//			insertBlanks(str, i + 1);
//		}
//		
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public static boolean insertBlanks(int i, String msg)
//	{
//		if (i >= msg.length())
//		{
//			return words.contains(msg);
//			
////			wordsFound.add(msg);
////			return wordsFound;
//		}
//		
//		/*
//			formanytimes
//			f ormanytimes (i = 0)
//			fo rmanytimes (i = 1)
//			for manytimes *
//			
//		*/
//		
//		String wordSoFar = msg.substring(0, i + 1);
//		
//		if (words.contains(wordSoFar))
//		{
//			wordsFound.add(wordSoFar);
//			System.out.println("Word found: " + wordSoFar);
//			return insertBlanks(0, msg.substring(i + 1));
//		}
//		else
//		{
//			System.out.println("Word not found: " + wordSoFar);
//			return insertBlanks(i + 1, msg);
//		}
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
////		String wordSoFar = "";
////		String newMsg = "";
////		
////		for (int i = 0; i < msg.length(); i++)
////		{
////			String s = msg.substring(i, i + 1);
////			wordSoFar += s;
////			
////			System.out.println("NewMsg: " + newMsg);
////			
////			if (words.contains(wordSoFar))
////			{
////				System.out.println("Word so far found: " + wordSoFar);
////				newMsg += wordSoFar + " ";
////				wordSoFar = "";
////			}
////			else
////			{
////				insertBlanks(msg.substring(i));
////			}
////		}
//		
////		return "";
//	}
}
