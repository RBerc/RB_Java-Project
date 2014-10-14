/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rob
 */

public class Memory
{
	private static int[] mem = new int[52];

	/**
	 * @param var - cannot be null
	 * @return value of var
	 * @throws IllegalArgumentException if var is null
	 */
	public static int fetch(Id var)
	{
		if (var == null)
			throw new IllegalArgumentException ("null Id argument");
		return mem[getIndex (var.getChar())];
	}

	/**
	 * @param var - cannot be null
	 * @param value
	 * @throws IllegalArgumentException if var is null
	 */
	public static void store (Id var, int value)
	{
		if (var == null)
			throw new IllegalArgumentException ("null Id argument");
		mem[getIndex(var.getChar())] = value;
	}
	
	private static int getIndex (char ch)
	{
		int index;
		if (Character.isUpperCase(ch))
			index = ch - 'A';
		else
			index = ch - 'a' + 26;
		return index;
	}
}

