package cn.ctc.bean;

public class Schedule {

	private String line;
	private int authority;
	private String departuretime;
	private String authsequence;
	private String speedsequence;
	private boolean isrun=false;

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public int getAuthority() {
		return authority;
	}

	public void setAuthority(int authority) {
		this.authority = authority;
	}

	public String getDeparturetime() {
		return departuretime;
	}

	public void setDeparturetime(String departuretime) {
		this.departuretime = departuretime;
	}

	public String getAuthsequence() {
		return authsequence;
	}

	public void setAuthsequence(String authsequence) {
		this.authsequence = authsequence;
	}

	public String getSpeedsequence() {
		return speedsequence;
	}

	public void setSpeedsequence(String speedsequence) {
		this.speedsequence = speedsequence;
	}

	public boolean isIsrun() {
		return isrun;
	}

	public void setIsrun(boolean isrun) {
		this.isrun = isrun;
	}

	@Override
	public String toString() {
		return "Schedule [line=" + line + ", authority=" + authority
				+ ", departuretime=" + departuretime + ", authsequence="
				+ authsequence + ", speedsequence=" + speedsequence + "]";
	}

}
