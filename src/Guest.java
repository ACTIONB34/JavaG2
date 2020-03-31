
public class Guest {
	private String name;
	private double rate;
	
	public Guest() {
		this.name = new String();
		this.rate = 150;
	}	

	public Guest(String initName, double initRate) {
		this.name = initName;
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
}
