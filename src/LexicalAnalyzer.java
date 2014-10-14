/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rob
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class LexicalAnalyzer
{
	
	private List<Token> tokenList;
	
	/**
	 * @param fileName - cannot be null
	 * @throws FileNotFoundException 
	 * @throws LexicalException 
	 * @throws IllegalArgumentException if fileName is null
	 */
	public LexicalAnalyzer(String fileName) throws FileNotFoundException, LexicalException
	{
		if (fileName == null)
			throw new IllegalArgumentException ("null string argument");
		tokenList = new LinkedList<Token>();
		int lineNum = 1;
		Scanner input = new Scanner (new File (fileName));
		while (input.hasNext())
		{
			String line = input.nextLine();
			processLine (line, lineNum);
			lineNum++;
		}
		
		tokenList.add(new Token (lineNum, 1, ".", TokenType.EOF_TOK));  //This said "EOS"
                input.close();
        }

	private void processLine(String line, int lineNum) throws LexicalException
	{
		assert line != null;
		assert lineNum > 0;
		int columnNum = 0;
		columnNum = skipWhiteSpace (line, columnNum);
		while (columnNum < line.length())
		{
			String lexeme = getLexeme (line, columnNum);
			TokenType type = determineTokenType (lexeme, lineNum, columnNum);
			Token tok = new Token (lineNum, columnNum + 1, lexeme, type);
			tokenList.add(tok);
			columnNum += lexeme.length();
			columnNum = skipWhiteSpace (line, columnNum);
                        
		}
	}

	private TokenType determineTokenType(String lexeme, int lineNum, int columnNum) throws LexicalException 
	{
		assert lexeme != null;
		assert lineNum > 0;
		assert columnNum >= 0;
		TokenType type;
		if (Character.isLetter(lexeme.charAt(0)))
			if (lexeme.length() == 1)
				type = TokenType.ID_TOK;
			else if (lexeme.equals("MODULE"))
				type = TokenType.MODULE_TOK;
                        else if (lexeme.equals("BEGIN"))
				type = TokenType.BEGIN_TOK;
                        else if (lexeme.equals("END"))
				type = TokenType.END_TOK;
                        else if (lexeme.equals("IF"))
                                type = TokenType.IF_TOK;
			else if (lexeme.equals("THEN"))
				type = TokenType.THEN_TOK;
			else if (lexeme.equals("ELSE"))
				type = TokenType.ELSE_TOK;
                       else if (lexeme.equals("WHILE"))
				type = TokenType.WHILE_TOK;
			else if (lexeme.equals("DO"))
				type = TokenType.DO_TOK;
			else if (lexeme.equals("WriteInt"))  
				type = TokenType.PRINT_TOK;  
                        else if (lexeme.equals("REPEAT"))
				type = TokenType.REPEAT_TOK; 
                        else if (lexeme.equals("UNTIL"))
				type = TokenType.UNTIL_TOK;
                        else
				throw new LexicalException ("invalid lexeme  at row " + lineNum + " and column" + (columnNum + 1));
		else if (Character.isDigit(lexeme.charAt(0)))
			if (allDigits (lexeme))
				type = TokenType.LIT_INT;
			else
				throw new LexicalException ("invalid literal integer at row " + lineNum + " and column" + (columnNum + 1));
		else if (lexeme.equals(":="))
			type = TokenType.ASSIGN_OP;
		else if (lexeme.equals("+"))
			type = TokenType.ADD_TOK;
		else if (lexeme.equals("-"))
			type = TokenType.SUB_TOK;
		else if (lexeme.equals("*"))
			type = TokenType.MUL_TOK;
		else if (lexeme.equals("/"))
			type = TokenType.DIV_TOK;
		else if (lexeme.equals("="))
			type = TokenType.EQ_TOK;
		else if (lexeme.equals("#"))
			type = TokenType.NE_TOK;
		else if (lexeme.equals("<"))
			type = TokenType.LT_TOK;
		else if (lexeme.equals("<="))
			type = TokenType.LE_TOK;
		else if (lexeme.equals(">"))
			type = TokenType.GT_TOK;
		else if (lexeme.equals(">="))
			type = TokenType.GE_TOK;
		else if (lexeme.equals("("))
			type = TokenType.LP_TOK;
               else if (lexeme.equals(")"))
			type = TokenType.RP_TOK; 
               else if (lexeme.equals(";"))
			type = TokenType.EOS_TOK;
               else if (lexeme.equals("."))
                        type = TokenType.EOF_TOK;
                else
			throw new LexicalException ("invalid lexeme at row " + lineNum + " and column" + (columnNum + 1));			
		return type;
                
                      
	}

	private boolean allDigits(String s)
	{
		assert s != null;
		int i = 0;
		while (i < s.length() && Character.isDigit(s.charAt(i)))
			i++;
		return i == s.length();
	}

	private String getLexeme(String line, int columnNum)
	{
		assert line != null;
		assert columnNum >= 0;
		int i = columnNum;
		while (i < line.length() && !Character.isWhitespace(line.charAt(i)))
			i++;
		return line.substring(columnNum, i);
	}

	private int skipWhiteSpace(String line, int columnNum)
	{
		assert line != null;
		assert columnNum >= 0;
		while (columnNum < line.length() && Character.isWhitespace(line.charAt(columnNum)))
			columnNum++;
		return columnNum;
	}

	/**
	 * postcondition: token has been removed from front of list
	 * @return token from front of list
	 * @throws LexicalException is list is empty
	 */
	public Token getNextToken() throws LexicalException
	{
		if (!moreTokens())
			throw new LexicalException ("no more tokens");		
		return tokenList.remove(0);
	}

	public boolean moreTokens()
	{
		return !tokenList.isEmpty();
	}

	/**
	 * @return element at front of list
	 * @throws LexicalException if list is empty
	 */
	public Token getLookaheadToken() throws LexicalException
	{
		if (!moreTokens())
			throw new LexicalException ("no more tokens");		
		return tokenList.get(0);
	}

}

