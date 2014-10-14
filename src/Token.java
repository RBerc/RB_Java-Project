/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rob
 */

public class Token
{
	private int rowNumber;
	private int columnNumber;
	private String lexeme;
	private TokenType type;

	/**
	 * @param rowNumber - must be positive
	 * @param columnNumber - must be positive
	 * @param lexeme - must not be null & must have positive length
	 * @param type - cannot be null
	 * @throws IllegalArgumentException is a precondition is not met
	 */
	public Token(int rowNumber, int columnNumber, String lexeme, TokenType type)
	{
		if (rowNumber <= 0)
			throw new IllegalArgumentException ("invalid row number");
		if (columnNumber <= 0)
			throw new IllegalArgumentException ("invalid column number");
		if (lexeme == null || lexeme.length() == 0)
			throw new IllegalArgumentException ("invalid lexeme argument");
		if (type == null)
			throw new IllegalArgumentException ("null token type");
		this.rowNumber = rowNumber;
		this.columnNumber = columnNumber;
		this.lexeme = lexeme;
		this.type = type;
	}

	public TokenType getTokenType()
	{
		return type;
	}

	public int getColumnNumber()
	{
		return columnNumber;
	}

	public int getRowNumber()
	{
		return rowNumber;
	}

	public String getLexeme()
	{
		return lexeme;
	}

}

