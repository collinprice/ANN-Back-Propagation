package ann;

import java.util.Random;

/**********************************************************************
 * 
 * Collin Price
 * cp06vz @ brocku.ca
 * 
 * Problem
 * 4 bit parity problem helper.
 * 
 * Dec. 1, 2010
 * 
 **********************************************************************/

public class Problem {

	/**
	 * Binary input from 0-15
	 */
	private static int[][] inputs = {
		{0,0,0,0},
		{1,1,1,0},
		{0,0,1,0},
		{0,0,1,1},
		{0,1,0,0},
		{1,0,1,1},
		{0,1,0,1},
		{0,1,1,0},
		{0,1,1,1},
		{1,0,0,0},
		{1,0,0,1},
		{1,0,1,0},
		{1,1,0,1},
		{0,0,0,1},
		{1,1,1,1},
		{1,1,0,0}
	};
	
	/**
	 * Expected parity value from 0-15
	 */
	private static int[] parity = {
		0,
		1,
		1,
		0,		
		1,
		1,
		0,
		0,
		1,
		1,
		0,
		0,
		1,
		1,
		0,
		0
	};
	
	/**
	 * Returns the expected parity bit for the given integer.
	 * @param i
	 * @return
	 */
	public static int getOutput(int i) {
		return parity[i];
	} // getOutput
	
	/**
	 * Returns 4 bits that represent a binary input
	 * @param i the integer number the bits represent
	 * @return
	 */
	public static int[] getInput(int i) {
		return inputs[i];
	} // getInput
	
} // Problem
