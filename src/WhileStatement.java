/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rob
 */
public class WhileStatement  implements Statement
{
    private BooleanExpression expr;
    private StatementSequence ss;

public WhileStatement(BooleanExpression expr, StatementSequence ss)
{
    if (expr == null)
			throw new IllegalArgumentException ("null expresssion argument");
		if (ss == null)
			throw new IllegalArgumentException ("null code block argument");
		this.expr = expr;
		this.ss = ss;
}

public void execute()
{
    while (expr.evaluate())
        ss.execute();
}


}
