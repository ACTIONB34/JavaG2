
public class Senior extends Guest {

	public Senior() {
		super();
		super.setRate(super.SENIOR_RATE);
		super.setType(super.SENIOR);
	}
	
	public Senior(String initName) {
		super(initName);
		super.setRate(super.SENIOR_RATE);
		super.setType(super.SENIOR);
	}

	public Senior(String initName, double initRate) {
		super(initName, initRate);
		super.setType(super.SENIOR);
	}
}
