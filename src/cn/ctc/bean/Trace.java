package cn.ctc.bean;

public class Trace {

	private String line;
	private String Section;
	private String blocknumber;
	private double blocklength;// (m)
	private double blockgrade;// (%)
	private double speedlimit;// (Km/Hr)

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getSection() {
		return Section;
	}

	public void setSection(String section) {
		Section = section;
	}

	public String getBlocknumber() {
		return blocknumber;
	}

	public void setBlocknumber(String blocknumber) {
		this.blocknumber = blocknumber;
	}

	public double getBlocklength() {
		return blocklength;
	}

	public void setBlocklength(double blocklength) {
		this.blocklength = blocklength;
	}

	public double getBlockgrade() {
		return blockgrade;
	}

	public void setBlockgrade(double blockgrade) {
		this.blockgrade = blockgrade;
	}

	public double getSpeedlimit() {
		return speedlimit;
	}

	public void setSpeedlimit(double speedlimit) {
		this.speedlimit = speedlimit;
	}

	@Override
	public String toString() {
		return "Trace [line=" + line + ", Section=" + Section
				+ ", blocknumber=" + blocknumber + ", blocklength="
				+ blocklength + ", blockgrade=" + blockgrade + ", speedlimit="
				+ speedlimit + "]";
	}

}
