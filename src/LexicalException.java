/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rob
 */
public class LexicalException extends Exception
{

	private static final long serialVersionUID = 8968627285835792944L;

	/**
	 * @param message - cannot be null
	 * @throws IllegalArgumentException if message is null
	 */
	public LexicalException(String message)
	{
		super (message);
		if (message == null)
			throw new IllegalArgumentException ("null string argument");
	}

}
