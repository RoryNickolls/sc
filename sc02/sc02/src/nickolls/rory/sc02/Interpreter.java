package nickolls.rory.sc02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Interpreter {
	
	private HashMap<String, Variable> variables;
	private String[] programLines;
	private int progCtr = 0;
	
	public Interpreter()
	{
		variables = new HashMap<String, Variable>();
	}
	
	private void loadToMemory(String file)
	{
		BufferedReader br;
		try
		{
			br = new BufferedReader(new FileReader(new File(file)));
			String program = "";
			String curLine;
			while((curLine = br.readLine()) != null)
			{
				program += curLine;
			}
			// remove tabs
			program = program.replaceAll("[\t]", "");
			// split program into array of lines
			programLines = program.split(";");

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void beginInterpretation()
	{
		variables.clear();
		// loop until the program counter reaches the end
		while(progCtr < programLines.length)
		{
			interpretLine(progCtr);
			progCtr++;
		}
	}
	
	private void enterLoop(int lineNumber)
	{
		String[] currentLine = programLines[lineNumber].split(" ");
		
		// find properties of the while loop
		Variable variable = variables.get(currentLine[1]);
		int compareVal = Integer.parseInt(currentLine[3]);
		Condition condition = new Condition(variable, new Variable(compareVal));
		while(condition.evaluate(currentLine[2]))
		{
			// keep looping through the loop's statements until 'end' is found
			progCtr = lineNumber + 1;
			while(!programLines[progCtr].split(" ")[0].equals("end"))
			{
				// this will either execute a statement or recursively enter another while loop
				interpretLine(progCtr);
				progCtr++;
			}
		}
	}
	
	private void executeStatement(String[] line)
	{
		// try to find referenced variable in memory or create a new one
		Variable refVar;
		if(!variables.containsKey(line[1]))
		{
			refVar = new Variable(0);
			System.out.println("new  var! " + progCtr);
			variables.put(line[1], refVar);
		}
		else
		{
			refVar = variables.get(line[1]);
		}
		
		// create a new statement out of this line and execute it
		Statement stmt = new Statement(line[0], refVar);
		stmt.execute();
	}
	
	private void interpretLine(int lineNumber)
	{
		String[] line = programLines[lineNumber].split(" ");
		
		// do nothing for end or empty lines
		if(line[0].equals("end") || line[0].equals(""))
			return;
		
		if(!line[0].equals("while"))
		{
			// if this line is not a while loop then it must be a statement so execute it
			executeStatement(line);
			printState();
		}
		else
		{
			enterLoop(lineNumber);
		}
	}
	
	private void printState()
	{
		System.out.print("Line:" + progCtr + " ");
		String variableStr = "";
		for(String varName : variables.keySet())
		{
			variableStr += varName + ":" + variables.get(varName).getValue() + " ";
		}
		System.out.print(variableStr + "\n");
	}
	
	public static void main(String[] args)
	{
		Interpreter interpreter = new Interpreter();
		interpreter.loadToMemory(interpreter.getClass().getResource("/nickolls/rory/sc02/program.bb").getPath().toString());
		interpreter.beginInterpretation();
	}
}
