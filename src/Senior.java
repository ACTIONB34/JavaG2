
public class Senior extends Guest {

	public Senior() {
		super();
		super.setRate(120.00);
	}	

	public Senior(String initName, double initRate) {
		super(initName, initRate);
		super.setType(super.KID);
	}
}
