/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rob
 */

import java.util.ArrayList;
import java.util.List;


public class StatementSequence {
    private List<Statement> stmts;
    
    public StatementSequence()
    {
        stmts = new ArrayList<Statement> ();
    }
public void add(Statement stmt)
{
    if(stmt == null)
        throw new IllegalArgumentException("null statement argument");
    stmts.add(stmt);
}

public void execute()
{
    for (Statement s: stmts)
        s.execute();
}



}
