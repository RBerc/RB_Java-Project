/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rob
 */

public class Id implements Expression
{

	private char ch;
	
	/**
	 * @param ch - must be a letter
	 * @throws IllegalArgumentException if ch is not a letter
	 */
	public Id(char ch)
	{
		if (!Character.isLetter(ch))
			throw new IllegalArgumentException (ch + " is not a valid identifier");
		this.ch = ch;
	}

	public char getChar()
	{
		return ch;
	}
	
	@Override
	public int evaluate() 
	{
		return Memory.fetch (this);
	}

}

   
