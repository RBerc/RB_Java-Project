/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rob
 */
public class LiteralInteger implements Expression
{

	private int value;
	
	public LiteralInteger(int value)
	{
		this.value = value;
	}

	@Override
	public int evaluate()
	{
		return value;
	}

}
