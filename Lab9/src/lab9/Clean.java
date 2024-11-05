/* Copy of Blur.java with modifications */

package lab9;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;

public class Clean {

	public static void main(String[] args) throws IOException {
		int pics = 17;
		File[] file = new File[pics];
		// String prefix = "../resource/asnlib/publicdata/";
		String prefix = "Birds/BIRDS/";
		for (int p = 1; p <= pics; ++p) {
			String suff = String.valueOf(p);
			String filename = prefix + "birds" + suff + ".ppm";
			System.out.println("Opening " + filename);
			file[p - 1] = new File(filename);
		}

		long start = System.currentTimeMillis();

		// create a scanner for each of the pictures
		//
		Scanner[] scan = new Scanner[pics];

		int rows = 0, cols = 0, mx = 0;			// all images the same size
		for (int i = 0; i < pics; ++i) {
			try {
				scan[i] = new Scanner(file[i]);
				String line = scan[i].nextLine();
				cols = Integer.parseInt(scan[i].next());
				rows = Integer.parseInt(scan[i].next());
				mx = Integer.parseInt(scan[i].next());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		// open output file and print out header

		BufferedWriter output = new BufferedWriter(new FileWriter("blurred2.ppm"));
		output.write(String.format("%s\n", "P3"));
		output.write(String.format("%d  %d\n", cols, rows));
		output.write(String.format("%d\n", mx));
		System.out.println(rows + ", " + cols + ", " + mx);

		for (int i = 0; i < cols; ++i) {
			for (int j = 0; j < rows; ++j) {
				// read in red, green, blue
				for (int c = 0; c < 3; ++c) {

					// Instead of average, think of what else you might compute.
					/* Modified by Krish A. Patel */
					// try median, q3, max (median worst, q3 works best, max removes desired pixels)
					int[] pixelData = new int[scan.length];
					
//					int avg = 0;
					for (int k = 0; k < pics; ++k) {
						int next = Integer.parseInt(scan[k].next());
//						avg += next;
//						System.out.println(next);
						pixelData[k] = next;
					}
					int q3 = Select.select(pixelData, 0, pixelData.length - 1, 3 * (pixelData.length / 4));
//					int median = Select.select(pixelData, 0, pixelData.length - 1, 2 * (pixelData.length / 4));
//					int max = Select.select(pixelData, 0, pixelData.length - 1, pixelData.length);
//					prt(scanData, 0, -1);
//					avg = avg / pics;
					output.write(String.format("%d ", q3));

				}
			}
			output.write(String.format("\n"));
		}

		for (int i = 0; i < pics; ++i) {
			scan[i].close();
		}
		output.close();
		System.out.println("Finished");
	}
	
	public static void prt(int[] arr, int i, int j)
	{
		if (j == -1)
			j = arr.length - 1;
		
		for (int k = i; k <= j; k++)
			System.out.print(k + " ");
	}
}