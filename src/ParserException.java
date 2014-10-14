/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rob
 */
public class ParserException extends Exception {
    private static final long serialVersionUID = 2284169084900414715L;

	/**
	 * @param message - cannot be null
	 * @throws IllegalArgumentException is message is null
	 */
	public ParserException(String message)
	{
		super (message);
		if (message == null)
			throw new IllegalArgumentException ("null string argument");
	}
}
