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


public class Parser
{

	private LexicalAnalyzer lex;
	
	public Parser (String fileName) throws FileNotFoundException, LexicalException
	{
		lex = new LexicalAnalyzer (fileName);
	}
	
	public Program parse () throws ParserException
	{
		Token tok = getNextToken ();
		match (tok, TokenType.MODULE_TOK);
                tok = getNextToken();
                match(tok, TokenType.ID_TOK);
                tok = getNextToken();
                match(tok, TokenType.EOS_TOK);
                tok = getNextToken();
                match(tok, TokenType.BEGIN_TOK);
		StatementSequence ss = getStatementSequence ();
                tok = getNextToken();
		match (tok, TokenType.END_TOK);
		tok = getNextToken();
                match(tok, TokenType.ID_TOK);
                tok = getNextToken();
		if (tok.getTokenType() != TokenType.EOF_TOK)
			throw new ParserException ("there is no valid ending");
		return new Program (ss);
	}

	private StatementSequence getStatementSequence() throws ParserException
	{
		StatementSequence cd = new StatementSequence();
		Statement stmt = getStatement();
		cd.add (stmt);
		Token tok = getLookaheadToken ();
		while (isValidStartOfStatement (tok))
		{
			stmt = getStatement ();
			cd.add(stmt);
			tok = getLookaheadToken();
                      
		}
		return cd;
	}

	private boolean isValidStartOfStatement(Token tok)
	{
		assert tok != null;
		return tok.getTokenType() == TokenType.IF_TOK || tok.getTokenType() == TokenType.ID_TOK ||
			tok.getTokenType() == TokenType.WHILE_TOK || tok.getTokenType() == TokenType.REPEAT_TOK ||
			tok.getTokenType() == TokenType.PRINT_TOK;
	}

	private Statement getStatement() throws ParserException
	{
		Statement stmt;
		Token tok = getLookaheadToken();
		if (tok.getTokenType() == TokenType.ID_TOK)
			stmt = getAssignmentStatement();
		else if (tok.getTokenType() == TokenType.IF_TOK)
			stmt = getIfStatement ();
		else if (tok.getTokenType() == TokenType.PRINT_TOK)
			stmt = getPrintStatement ();
		else if (tok.getTokenType() == TokenType.WHILE_TOK)
			stmt = getWhileStatement();
		else if (tok.getTokenType() == TokenType.REPEAT_TOK)
			stmt = getUntilStatement();
               
                else 
			throw new ParserException ("statement expected at row " 
				+ tok.getRowNumber() + " and column " + tok.getColumnNumber());		
		
               return stmt;
	}

	private Statement getWhileStatement() throws ParserException
	{
		Token tok = getNextToken();
		match (tok, TokenType.WHILE_TOK);
		BooleanExpression expr = getBooleanExpression();
		tok = getNextToken();
		match (tok, TokenType.DO_TOK);
		StatementSequence ss = getStatementSequence();
		tok = getNextToken();
		match (tok, TokenType.END_TOK);
                // Added EOS_TOK to make sure that it checks for a Semicolin
                tok = getNextToken();
                match(tok, TokenType.EOS_TOK);
		return new WhileStatement (expr, ss);
	}

	private Statement getUntilStatement() throws ParserException
	{
		Token tok = getNextToken();
		match (tok, TokenType.REPEAT_TOK);
		StatementSequence ss = getStatementSequence();
		tok = getNextToken();
		match (tok, TokenType.UNTIL_TOK);
		BooleanExpression expr = getBooleanExpression();
                // Added EOS_TOK to make sure that it checks for a Semicolin
                tok = getNextToken();
                match(tok, TokenType.EOS_TOK);
		return new UntilStatement (expr, ss);
	}

	private Statement getIfStatement() throws ParserException
	{
		Token tok = getNextToken();
		match (tok, TokenType.IF_TOK);
		BooleanExpression expr = getBooleanExpression();
		tok = getNextToken();
		match (tok, TokenType.THEN_TOK);
		StatementSequence ss1 = getStatementSequence();
		tok = getNextToken();
		match (tok, TokenType.ELSE_TOK);
		StatementSequence ss2 = getStatementSequence ();
		tok = getNextToken();
		match (tok, TokenType.END_TOK);
                // Added EOS_TOK to make sure that it checks for a Semicolin
                tok = getNextToken();
                match(tok, TokenType.EOS_TOK);
		return new IfStatement (expr, ss1, ss2);
	}

	private Statement getPrintStatement() throws ParserException
	{
		Token tok = getNextToken();
		match (tok, TokenType.PRINT_TOK);
                tok = getNextToken();
                match(tok, TokenType.LP_TOK);
		Expression expr = getExpression();
                tok = getNextToken();
                match(tok, TokenType.RP_TOK);
                // Added EOS_TOK to make sure that it checks for a Semicolin
                tok = getNextToken();
                match(tok, TokenType.EOS_TOK);
		return new PrintStatement (expr);
	}

	private Statement getAssignmentStatement() throws ParserException
	{
		Token tok = getNextToken();
		match (tok, TokenType.ID_TOK);
		Id var = new Id (tok.getLexeme().charAt(0));
		tok = getNextToken();
		match (tok, TokenType.ASSIGN_OP);
		Expression expr = getExpression();
                // Added EOS_TOK to make sure that it checks for a Semicolin
                tok = getNextToken();
                match(tok, TokenType.EOS_TOK);
		return new AssignmentStatement (var, expr);
	}

	private Expression getExpression() throws ParserException
	{
		Expression expr;
		Token tok = getLookaheadToken();
		if (tok.getTokenType() == TokenType.ID_TOK)
		{
			tok = getNextToken();
			expr = new Id (tok.getLexeme().charAt(0));
		}
		else if (tok.getTokenType() == TokenType.LIT_INT)
		{
			tok = getNextToken();
			try
			{
				expr = new LiteralInteger (Integer.parseInt (tok.getLexeme()));
			}
			catch (NumberFormatException e)
			{
				throw new ParserException ("integer constant expected at row " 
					+ tok.getRowNumber() + " and column " + tok.getColumnNumber());
			}
		}
		else
		{
			ArithmeticOperator op = getArithmeticOperator ();
			Expression expr1 = getExpression();
			Expression expr2 = getExpression();
			expr = new BinaryExpression (op, expr1, expr2);
		}			
		return expr;
	}

	private ArithmeticOperator getArithmeticOperator() throws ParserException
	{
		ArithmeticOperator op;
		Token tok = getNextToken();
		switch (tok.getTokenType())
		{
			case ADD_TOK:
				op = ArithmeticOperator.ADD_OP;
				break;
			case SUB_TOK:
				op = ArithmeticOperator.SUB_OP;
				break;
			case MUL_TOK:
				op = ArithmeticOperator.MUL_OP;
				break;
			case DIV_TOK:
				op = ArithmeticOperator.DIV_OP;
				break;	
			default:
				throw new ParserException ("arithmetic operator expected at row " 
				+ tok.getRowNumber() + " and column " + tok.getColumnNumber());
		}
		return op;
	}

	private BooleanExpression getBooleanExpression() throws ParserException
	{
		RelationalOperator op = getRelationalOperator();
		Expression expr1 = getExpression();
		Expression expr2 = getExpression();
		return new BooleanExpression (op, expr1, expr2);
	}

	private RelationalOperator getRelationalOperator() throws ParserException
	{
		RelationalOperator op;
		Token tok = getNextToken();
		switch (tok.getTokenType())
		{
			case NE_TOK:
				op = RelationalOperator.NE_OP;
				break;
			case EQ_TOK:
				op = RelationalOperator.EQ_OP;
				break;
			case LT_TOK:
				op = RelationalOperator.LT_OP;
				break;
			case LE_TOK:
				op = RelationalOperator.LE_OP;
				break;
			case GT_TOK:
				op = RelationalOperator.GT_OP;
				break;
			case GE_TOK:
				op = RelationalOperator.GE_OP;
				break;
			default:
				throw new ParserException ("relational operator expected at row " 
						+ tok.getRowNumber() + " and column " + tok.getColumnNumber());	
		}
		return op;
	}

	private void match(Token tok, TokenType type) throws ParserException
	{
		assert tok != null;
		assert type != null;
		if (tok.getTokenType() != type)
			throw new ParserException (type.name() + " expected at row " 
					+ tok.getRowNumber() + " and column " + tok.getColumnNumber());
	}
	
	private Token getNextToken () throws ParserException
	{
		Token tok;
		try
		{
			tok = lex.getNextToken();
		}
		catch (LexicalException e)
		{
			throw new ParserException (e.getMessage());
		}
		return tok;
	}
	
	private Token getLookaheadToken () throws ParserException
	{
		Token tok;
		try
		{
			tok = lex.getLookaheadToken();
		}
		catch (LexicalException e)
		{
			throw new ParserException (e.getMessage());
		}
		return tok;
	}
}

