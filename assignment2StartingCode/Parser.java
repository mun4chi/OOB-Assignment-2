package appDomain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import exceptions.EmptyQueueException;
import implementations.MyQueue;
import implementations.MyStack;

/**
 * This is a parser that checks a text file for brackets that are matched
 * correctly.
 * <p>
 * This file only reads one line at a time. The brackets are only checked and
 * every other characters are ignored. Each of the brackets are stored as string
 * so that the line numbers can be found. All error will be printed in the order
 * they occur.
 * </p>
 */

public class Parser {
	/** Opening brackets to be matched with their closing brackets **/
	private MyStack stack;

	/** Brackets in the wrong place */
	private MyQueue errorQ;

	/** Closing brackets with no opening brackets */
	private MyQueue extrasQ;

	/** Total number of errors */
	private int errorCount;

	/**
	 * A parser with an empty stack and empty queue
	 */
	public Parser() {
		stack = new MyStack();
		errorQ = new MyQueue();
		extrasQ = new MyQueue();
		errorCount = 0;

	}
	/**
	 * Starting point of the application. Path of the file checks the first command line argument
	 * @param  args the command line argument which is the file to parse.
	 * 
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: java -jar Parser.jar <filename>");
			return;
		}
		Parser parser = new Parser();
		parser.parseFile(args[0]);
	}
	/**
	 * Reads the file line by line, checks every brackets and print the results.
	 * @param fileName the path of the file to check
	 *
	 */
	public void parseFile(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			int lineNumber = 0;
			String line = reader.readLine();
			while (line != null) {
				lineNumber++;
				processLine(line, lineNumber);
				line = reader.readLine();
			}
			reader.close();

			System.out.println("Parsing file: " + fileName);
			reportErrors();
			if (errorCount == 0) {
				System.out.println("No errors found. The file is properly built.");
			} else {
				System.out.println("Total errors found: " + errorCount);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error: the file \"" + fileName + "\" could not be found.");
		} catch (IOException e) {
			System.out.println("Error: the file could not be read. " + e.getMessage());
		} catch (EmptyQueueException e) {
			System.out.println("Error: unexpected empty queue. " + e.getMessage());
		}
	}
	/**
	 * Reports all errors after the file is fully read.
	 * @throws EmptyQueueException is the queue is suddenly empty/.
	 */
	private void reportErrors() throws EmptyQueueException {
		// Openers that are never closed
		while (!stack.isEmpty()) {
			errorQ.enqueue(stack.pop());
		}

		// Compare both queues when both have elements
		while (!errorQ.isEmpty() && !extrasQ.isEmpty()) {
			String error = errorQ.peek();
			String extra = extrasQ.peek();
			if (matches(getBracket(error), getBracket(extra))) {
				errorQ.dequeue();
				extrasQ.dequeue();
			} else {
				report(errorQ.dequeue());
			}
		}

		// Whatever is left in either queue will be an error
		while (!errorQ.isEmpty()) {
			report(errorQ.dequeue());
		}
		while (!extrasQ.isEmpty()) {
			report(extrasQ.dequeue());
		}
	}
	/**
	 * Prints one error and adds it the error count.
	 * @param entry the entry in the form "line:bracket".
	 */
	private void report(String entry) {
		errorCount++;
		System.out.println("Error at line " + getLine(entry) + ": unmatched " + getBracket(entry));
	}



	/**
	 * Checks whether a character is an opening bracket.
	 * 
	 * @param c the character to check
	 * @return true if c is ( { [ or &lt;
	 */
	private boolean isOpening(char c) {
		return c == '(' || c == '{' || c == '[' || c == '<';
	}

	/**
	 * Checks whether a character is a closing bracket
	 * 
	 * @param c the character to check
	 * @return true if c is ) } ] or &gt;
	 * 
	 */
	private boolean isClosing(char c) {
		return c == ')' || c == '}' || c == ']' || c == '>';
	}

	/**
	 * checks whether an opening or closing bracket forms a pair.
	 * 
	 * @param open  the opening bracket
	 * @param close the closing bracket
	 * @return true if they are matches
	 * 
	 */
	private boolean matches(char open, char close) {
		return (open == '(' && close == ')') || (open == '{' && close == '}') || (open == '[' && close == ']')
				|| (open == '<' && close == '>');
	}

	/**
	 * Build a stack or queue entry that stores line numbers with the bracket.
	 * 
	 * @param lineNumber the line in which the bracket was found.
	 * @param bracket    the bracket character
	 * @return a String in the form "line:bracket"
	 */
	private String makeEntry(int lineNumber, char bracket) {
		return lineNumber + ":" + bracket;
	}

	/**
	 * Gets the brackets from an entry.
	 * 
	 * @param entry a string in the form "line:bracket"
	 * @return the bracket character
	 */
	private char getBracket(String entry) {
		return entry.charAt(entry.length() - 1);
	}

	/**
	 * Gets the line number from an entry
	 * 
	 * @param entry a string in the form " line: bracket"
	 * @return the line number
	 * 
	 */
	private int getLine(String entry) {
		return Integer.parseInt(entry.substring(0, entry.indexOf(':')));
	}
	/**
	 * checks every character in one line and bring out the ones that are brackets
	 * @param line the text of the line.
	 * @param lineNumber the line number starting at 1 
	 * @throws EmptyQueueException if a queue is suddenly  empty  
	 */
	private void processLine(String line, int lineNumber) throws EmptyQueueException {
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (isOpening(c)) {
				stack.push(makeEntry(lineNumber, c));
			} else if (isClosing(c)) {
				processClosing(c, lineNumber);
			}
		}
	}
	/**
	 * Bracket matching rules applied for closing brackets.
	 * @param close the closing bracket
	 * @param lineNumber the line it was found on
	 * @throws EmptyQueueException if queue is suddenly empty.
	 */
	private void processClosing(char close, int lineNumber) throws EmptyQueueException {
		String entry = makeEntry(lineNumber, close);

		if (!stack.isEmpty() && matches(getBracket(stack.peek()), close)) {
			// Rule 1
			stack.pop();
		} else if (!errorQ.isEmpty() && matches(getBracket(errorQ.peek()), close)) {
			// Rule 2
			errorQ.dequeue();
		} else if (stack.isEmpty()) {
			// Rule 3
			errorQ.enqueue(entry);
		} else if (stackHasOpenerFor(close)) {
			// Rule 4
			String top = stack.pop();
			while (!matches(getBracket(top), close)) {
				errorQ.enqueue(top);
				top = stack.pop();
			}
		} else {
			// Rule 5
			extrasQ.enqueue(entry);
		}
	}
	/**
	 * Checks whether if the stack contains an opening bracket that matches the given closing bracket
	 * @param close the closing bracket
	 * @return true if a matching opening bracket found in the stack.
	 */
	private boolean stackHasOpenerFor(char close) {
		String[] entries = stack.toArray(new String[0]);
		for (int i = 0; i < entries.length; i++) {
			if (matches(getBracket(entries[i]), close)) {
				return true;
			}
		}
		return false;
}
}
