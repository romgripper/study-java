
public class Expression extends CalcNode {
	private CalcNode left;
	private CalcNode right;
	private char operator = 0;	// Indicate no operator
	private boolean isInBracket = false;
	
	public Expression() {
	}
	
	public Expression(int left) {
		this.left = new Value(left);
	}

	public CalcNode getLeft() {
		return left;
	}

	public void setLeft(CalcNode left) {
		this.left = left;
		left.setParent(this);
	}

	public CalcNode getRight() {
		return right;
	}

	public void setRight(CalcNode right) {
		this.right = right;
		right.setParent(this);
	}

	public char getOperator() {
		return operator;
	}

	public void setOperator(char operator) {
		//	When it comes to a operator, only left exists
		if (left != null && right == null && this.operator == 0) {
			this.operator = operator;
		} else {
			throw new RuntimeException("Invalid Expression: there must be an operand before an operator");
		}
	}
	
	public boolean isInBracket() {
		return isInBracket;
	}

	public void setInBracket(boolean isInBracket) {
		this.isInBracket = isInBracket;
	}
	
	public Expression addLeftBracket() {
		Expression tmp = new Expression();
		tmp.setInBracket(true);
		this.addCalcNode(tmp);
		return tmp;
	}
	
	public void replace(CalcNode node1, CalcNode node2) {
		if (left == node1) {
			left = node2;
			node2.setParent(this);
		} else if (right == node1) {
			right = node2;
			node2.setParent(this);
		} else {
			throw new RuntimeException("Node not found, this should not occur");
		}
	}
	
	public void addCalcNode(CalcNode node) {
		if (left == null) {
			left = node;
		} else if (right == null && operator != 0) {
			right = node;
		} else {
			throw new RuntimeException("Invalid expression, more than 2 operands together");
		}
		node.setParent(this);
	}
	
	public void replaceChildWithValue(CalcNode node) {
		Value value = new Value(node.calc());
		if (left == node) {
			left = value;
			value.setParent(this);
		} else if (right == node) {
			right = value;
			value.setParent(this);
		}
	}
	
	public void addValue(int value) {
		addCalcNode(new Value(value));
	}

	public int calc() {
		//System.out.print(" " + left.calc() + operator + right.calc() + " ");
		int result = left.calc();
		if (right != null) {
			switch (operator) {
			case '+':
				result += right.calc();
				break;
			case '-':
				result -= right.calc();
				break;
			case '*':
				result *= right.calc();
				break;
			case '/':
				result /= right.calc();
				break;
			case '%':
				result %= right.calc();
				break;
			}
		}
		return result;
	}
	
}
