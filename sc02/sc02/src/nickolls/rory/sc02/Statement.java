package nickolls.rory.sc02;

/*
 * All statements should take the structure [COMMAND] [VARIABLE]
 * Execution should return the new state of [VARIABLE]
 */
public class Statement {
	
	private String command;
	private Variable variable;
	
	public Statement(String command, Variable variable)
	{
		this.command = command;
		this.variable = variable;
	}
	
	public void execute()
	{
		switch(command)
		{
			case "incr":
				variable.setValue(variable.getValue() + 1);
				break;
			case "decr":
				variable.setValue(variable.getValue() - 1);
				break;
			case "clear":
				variable.setValue(0);
				break;
		}
	}
	
}
