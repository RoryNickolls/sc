package nickolls.rory.sc03;

/**
 * Compares variable1 to variable2 using the comparator passed in evaluation
 * @author Rory
 *
 */
public class Condition {
	
	private Variable variable1, variable2;
	
	public Condition(Variable variable1, Variable variable2)
	{
		this.variable1 = variable1;
		this.variable2 = variable2;
	}
	
	public boolean evaluate(String comparator)
	{
		switch(comparator)
		{
			case "not":
				return variable1.getValue() != variable2.getValue();
			default:
				return false;
		}
	}
	
}
