
public class Value extends CalcNode {

	private int value;
	
	public Value(int value) {
		this.value = value;
	}
	
	@Override
	public int calc() {
		return value;
	}

}
