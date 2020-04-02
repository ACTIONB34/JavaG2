
public class Kid extends Guest {
	
	public Kid() {
		super();
		super.setRate(100.00);
		super.setType(super.KID);
	}
	
	public Kid(String initName) {
		super(initName, 100.00);
		super.setType(super.KID);
	}

	public Kid(String initName, double initRate) {
		super(initName, initRate);
		super.setType(super.KID);
	}
}