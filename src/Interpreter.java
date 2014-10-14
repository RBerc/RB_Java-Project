/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rob
 */
import java.io.FileNotFoundException;


public class Interpreter
{

	public static void main(String[] args)
	{
		try
		{
			Parser p = new Parser ("test4.mod");
			Program prog = p.parse();
			prog.execute();
                        
		}
		catch (ParserException e)
		{
			System.out.println(e.getMessage());
		}
		catch (LexicalException e)
		{
			System.out.println(e.getMessage());
		}
		catch (FileNotFoundException e)
		{
			System.out.println("source file could not be found");
		}
		catch (Exception e)
		{
			System.out.println("unknown error - terminating");
		}
	}

}
