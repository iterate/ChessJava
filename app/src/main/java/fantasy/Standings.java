package fantasy;

public class Standings {

	public int gamesPlayed;

	public String report;

	public Standings(int gamesPlayed, String report) {
		this.gamesPlayed = gamesPlayed;
		this.report = report;
	}

	public String toString() {
		return report;
	}

}
