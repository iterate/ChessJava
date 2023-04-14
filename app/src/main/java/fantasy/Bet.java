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

	public int calculateScore() {
		return (piece.moves * 5) + (piece.captures * 10);
	}

	public String report() {
		return piece.type.name() + " (" + player.shortName() + ") " + piece.originalPosition.toNotation() + ": Score ["
				+ calculateScore() + "] (" + piece.moves + " moves, " + piece.captures + " captures, " + piece.captured
				+ " captured)";
	}

	@Override
	public String toString() {
		return "Bet [player=" + player.name + ", piece=" + piece.type.name() + ", score=" + score + "]";
	}

}
