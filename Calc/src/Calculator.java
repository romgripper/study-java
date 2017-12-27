import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;


public class Calculator {

	private static MathUtils math = new MathUtils();
	
	private static Expression createCalcTree(String[] exps) {
		Expression root = new Expression();
		root.setLeft(new Value(1));
		root.setOperator('*');
		Expression node = root;
		
		Stack<Expression> bracketExpressions = new Stack<Expression>();
		for (String exp : exps) {
			if (math.isOperand(exp)) { // For operand
				int operand = Integer.parseInt(exp);
				node.addValue(operand);
			} else if (math.isOperator(exp)){ // For operator
				char op = exp.charAt(0);
				if (math.isLeftBracket(op)) {
					node = node.addLeftBracket();
					bracketExpressions.push(node);
				} else if (math.isRightBracket(op)) {
					if (bracketExpressions.size() == 0) {
						throw new RuntimeException("Brackets don't match");
					}
					node = bracketExpressions.pop();
					Expression parent = node.getParent();
					if (parent == null) {
						break;	// end
					}
					parent.replaceChildWithValue(node);
					node = parent;
				} else {	// +-*/%
					if (node.getRight() == null) {
						node.setOperator(op);
					} else {
						do {
							if (math.isPrior(op, node.getOperator())) {
								Expression tmp = new Expression();
								tmp.setLeft(node.getRight());
								tmp.setOperator(op);
								node.setRight(tmp);
								node = tmp;
								break;
							} else {
							Expression parent = node.getParent();
							if (node.isInBracket()) {
								Expression tmp = new Expression();
								tmp.setLeft(node);
								node.setInBracket(false);
								tmp.setInBracket(true);
								tmp.setOperator(op);
								bracketExpressions.pop();
								bracketExpressions.push(tmp);
								parent.replace(node, tmp);
								tmp.replaceChildWithValue(node);
								node = tmp;
								break;
							}
							parent.replaceChildWithValue(node);
							node = parent;
							if (node.getOperator() == 0) {
								node.setOperator(op);
								break;
							}
							}
						} while (true);
						
					}
				}
			} else {	// not operator or operand
				throw new RuntimeException("Invalid expression: " + exp);
			}
		}
		return root;
	}
	
	private static int calc(String expression) {
		expression = expression.trim();
		expression = expression.replaceAll("\\s+", " ");
		String[] args = expression.split(" ");
		String[] exps = new String[args.length + 2];
		exps[0] = "(";
		int i = 1;
		System.out.print("Expression:");
		for(String arg : args) {
			System.out.print(" " + arg);
			if (! math.validateCharacters(arg)) {
				System.out.println("Invalid expression: " + arg);
				System.exit(1);
			}
			exps[ i ++ ] = arg;
		}
		System.out.println();
		exps[i] = ")";
		Expression root = createCalcTree(exps);
		return root.calc();
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Please input a file name which contains the expressions");
			System.exit(1);
		}
		File file = new File(args[0]);
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String expression;
			while ((expression = reader.readLine()) != null) {
				try {
					System.out.println("Orginal expression: " + expression);
					System.out.println("Result: " + calc(expression));
					System.out.println();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Cannot open file: " + args[0]);
			System.exit(1);
		}
	}
}
