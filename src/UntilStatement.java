/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**d
 *
 * @author Rob
 */

public class UntilStatement implements Statement
{

	private  BooleanExpression expr;
	
	private StatementSequence ss;
	
	/**
	 * @param expr - cannot be null
	 * @param ss - cannot be null
	 * @throws IllegalArgumentException if precondition is not met
	 */
	public UntilStatement(BooleanExpression expr, StatementSequence ss)
	{
		if (expr == null)
			throw new IllegalArgumentException ("null expresssion argument");
		if (ss == null)
			throw new IllegalArgumentException ("null code block argument");
		this.expr = expr;
		this.ss = ss;
	}

	@Override
	public void execute()
	{
		while (!expr.evaluate())
			ss.execute();
	}

}
