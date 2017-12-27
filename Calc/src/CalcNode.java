
public abstract class CalcNode {
	private Expression parent;
	
	public CalcNode() {
		
	}
	
	public CalcNode(Expression parent) {
		this.parent = parent;
	}
	
	public Expression getParent() {
		return parent;
	}
	
	public void setParent(Expression parent) {
		this.parent = parent;
	}
	
	public abstract int calc();
}
