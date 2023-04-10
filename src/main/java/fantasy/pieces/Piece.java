package fantasy.pieces;

import fantasy.HalfMove;
import fantasy.Position;

public class Piece implements Comparable<Piece> {

	public Color color;

	public Type type;
	public int captures;
	public int captured;
	public int moves;

	public Position position;
	public Position originalPosition;

	public boolean isCaptured = false;

	public Piece(Type type, Color color, String position) {
		this.type = type;
		this.color = color;
		this.position = Position.parse(position);
		this.originalPosition = Position.parse(position);
	}

	public String abbreviation() {
		if (type.equals(Type.PAWN))
			return "P";
		else
			return type.abbreviation;
	}

	public boolean isLegalMove(HalfMove halfMove) {
		return true;
	}

	public void moveTo(String to) {
		this.position = Position.parse(to);
		this.moves++;
	}

	public void captured() {
		this.isCaptured = true;
		this.captured++;
	}

	public void reset() {
		this.position = this.originalPosition;
		this.isCaptured = false;
	}

	public String report() {
		return type.name() + " " + originalPosition.toNotation() + " - Moves: " + moves + ", Captures: " + captures
				+ " Captured: " + this.captured;
	}

	@Override
	public String toString() {
		return "Piece [color=" + color + ", type=" + type + ", captures=" + captures + ", moves=" + moves
				+ ", position=" + position + "]";
	}

	@Override
	public int compareTo(Piece o) {
		return o.type.value - this.type.value;
	}

	public boolean isCaptured() {
		return this.isCaptured;
	}

}
