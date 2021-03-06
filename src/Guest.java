
public class Guest {
	final static public int REGULAR = 1;
	final static public int KID = 2;
	final static public int SENIOR = 3;
	final static public double REGULAR_RATE = 150.0;
	final static public double KID_RATE = 100.0;
	final static public double SENIOR_RATE = 120.0;	
	
	private String name;
	private int type;
	private double rate;
	
	public Guest() {
		this.name = new String();
		this.type = this.REGULAR;
		this.rate = this.REGULAR_RATE;
	}
	
	public Guest(String initName) {
		this.name = initName;
		this.type = this.REGULAR;
		this.rate = this.REGULAR_RATE;
	}	

	public Guest(String initName, double initRate) {
		this.name = initName;
		this.type = this.REGULAR;
		this.rate = initRate;
	}

	public Guest(String initName, int initType, double initRate) {
		this.name = initName;
		this.type = initType;
		this.rate = initRate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
