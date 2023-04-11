package fantasy;

import fantasy.pieces.Color;

public class Game {

	public String event;
	public String site;
	public String date;

	public String round;
	public Player playerWhite;
	public Player playerBlack;
	public Color winner;
	public boolean draw = false;

	public String moves;

	@Override
	public String toString() {
		return "Game [event=" + event + ", site=" + site + ", date=" + date + ", round=" + round + ", playerWhite="
				+ playerWhite + ", playerBlack=" + playerBlack + ", winner=" + winner + "]";
	}

}
