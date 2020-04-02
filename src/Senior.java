
public class Senior extends Guest {

	public Senior() {
		super();
		super.setRate(120.00);
		super.setType(super.SENIOR);
	}
	
	public Senior(String initName) {
		super(initName, 120.00);
		super.setType(super.SENIOR);
	}

	public Senior(String initName, double initRate) {
		super(initName, initRate);
		super.setType(super.SENIOR);
	}
}
