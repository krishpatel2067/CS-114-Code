package ps10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Quantiles {

	public static void main(String[] args) {
		File incomesTxt = new File("Incomes.txt");
		ArrayList<Integer> incomesAl = new ArrayList<>();
		try {
			Scanner incomesReader = new Scanner(incomesTxt);
			
			while (incomesReader.hasNextInt())
			{
				incomesAl.add(incomesReader.nextInt());
			}
			System.out.println("Incomes successfully read");
			incomesReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int n = incomesAl.size();
		System.out.println("# income values: " + n);
		
		int[] incomes = new int[n];
		for (int i = 0; i < incomes.length; i++)
			incomes[i] = incomesAl.get(i);
		
		Scanner userInput = new Scanner(System.in);
		System.out.println("To find the k quantiles of the incomes. Enter value for k:  ");
		int k = userInput.nextInt();
		
		System.out.println(k + " quantiles of incomes: ");
		for (int j = 1; j < k; j++)
		{
			System.out.println("\t" + Select.select(incomes, 0, incomes.length - 1, j * Math.ceilDiv(n, k)));
		}
		
		userInput.close();
	}

}
