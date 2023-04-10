package fantasy;

import java.util.Objects;

public class Position {

	public int x;
	public int y;

	public Position() {

	}

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static Position parse(String square) {
		if (square == null || square.length() != 2)
			throw new IllegalArgumentException("Not a Chess square: " + square);

		Position result = new Position();
		result.x = parseFile(square.charAt(0));
		result.y = parseRank(square);

		return result;
	}

	public static Integer parseRank(String square) {
		return Integer.valueOf(square.substring(1));
	}

	public static int parseFile(char column) {
		return Character.getNumericValue(column) - 9;
	}

	public static char fileLabel(int columnNumber) {
		return (char) (columnNumber + 96);
	}

	public boolean adjacentFiles(Position other) {
		return Math.abs(this.x - other.x) == 1;
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append(fileLabel(x));
		s.append(y);
		return s.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		return x == other.x && y == other.y;
	}

	public String toNotation() {
		return Character.toString(fileLabel(x)) + y;
	}
}
