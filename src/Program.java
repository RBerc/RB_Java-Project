/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rob
 */
public class Program
{

	private StatementSequence ss;
       // private Id i1;
	
	/**
	 * @param cb - cannot be null
	 * @throws IllegalArgumentException if StatementSequence  is null
	 */
	public Program(StatementSequence ss)
	{
		if (ss == null)
			throw new IllegalArgumentException ("null Statement Sequence exception");
                //if(i1== null)
                  //      throw new IllegalArgumentException("null ID exception");
		this.ss = ss;
               // this.i1 = i1;
	}
	
	public void execute ()
	{
            ss.execute();
	}

}
