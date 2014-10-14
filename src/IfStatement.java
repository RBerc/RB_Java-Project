/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rob
 */

public class IfStatement implements Statement
{

	private BooleanExpression expr;
	
	private StatementSequence ss1, ss2;
	
	/**
	 * @param expr - cannot be null
	 * @param ss1 - cannot be null
	 * @param ss2 - cannot be null
	 * @throws IllegalArgumentException if precondition is not met
	 */
	public IfStatement(BooleanExpression expr, StatementSequence ss1, StatementSequence ss2)
	{
		if (expr == null)
			throw new IllegalArgumentException ("null boolean expression argument");
		if (ss1 == null || ss2 == null)
			throw new IllegalArgumentException ("null code block argument");
		this.expr = expr;
		this.ss1 = ss1;
		this.ss2 = ss2;
	}

	@Override
	public void execute()
	{
		if (expr.evaluate())
			ss1.execute();
		else
			ss2.execute();		
	}

}
