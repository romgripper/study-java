
public class MathUtils {
	public boolean validateCharacters(String expression) {
		return expression.matches("^[-+*/%0-9()]+$");
	}

	public boolean isOperand(String e)
	{
		return e.matches("^-?[0123456789]+$");
	}
	
	public boolean isOperator(String e) {
		return e.matches("^[-+*/%()]$");
	}

	public boolean isRightBracket(char e)
	{
		return e == ')';
	}

	public boolean isLeftBracket(char e)
	{
		return e == '(';
	}
	
	public boolean isPrior(char operator1, char operator2) {
		return ((operator1 == '*' || operator1 == '/' || operator1 == '%') &&
				(operator2 == '+' || operator2 == '-'));
	}
}
