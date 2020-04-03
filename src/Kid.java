
public class Kid extends Guest {
	
	public Kid() {
		super();
		super.setRate(super.KID_RATE);
		super.setType(super.KID);
	}
	
	public Kid(String initName) {
		super(initName);
		super.setRate(super.KID_RATE);;
		super.setType(super.KID);
	}

	public Kid(String initName, double initRate) {
		super(initName, initRate);
		super.setType(super.KID);
	}
}