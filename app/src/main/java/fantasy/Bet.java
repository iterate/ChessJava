package fantasy;

import fantasy.pieces.Piece;

public class Bet {

	final public Player player;
	final public Piece piece;

	public int score;

	public Bet(Player player, Piece piece) {
		if (player == null)
			throw new IllegalArgumentException("Player cannot be null");
		if (piece == null)
			throw new IllegalArgumentException("Piece cannot be null");

		this.player = player;
		this.piece = piece;
	}

	@Override
	public String toString() {
		return "Bet [player=" + player.name + ", piece=" + piece.type.name() + ", score=" + score + "]";
	}

}
