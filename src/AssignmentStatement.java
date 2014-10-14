/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rob
 */

public class AssignmentStatement implements Statement
{

	private Id var;
	
	private Expression expr;
	
	/**
	 * @param var - cannot be null
	 * @param expr - cannot be null
	 * @throws IllegalArgumentException if a precondition is not met
	 */
	public AssignmentStatement(Id var, Expression expr)
	{
		if (var == null)
			throw new IllegalArgumentException ("null Id argument");
		if (expr == null)
			throw new IllegalArgumentException ("null Expression argument");
		this.var = var;
		this.expr = expr;
	}

	@Override
	public void execute()
	{
		Memory.store(var, expr.evaluate());
	}

}

