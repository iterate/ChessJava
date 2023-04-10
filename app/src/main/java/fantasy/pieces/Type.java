package fantasy.pieces;

public enum Type {

	KING(Integer.MAX_VALUE, "K"), QUEEN(9, "Q"), ROOK(5, "R"), BISHOP(3, "B"), KNIGHT(3, "N"), PAWN(1, "");

	public final Integer value;
	public final String abbreviation;

	Type(Integer value, String abbreviation) {
		this.value = value;
		this.abbreviation = abbreviation;
	}

	public static Type parse(String abbreviation) {
		if (abbreviation == null)
			return null;

		for (Type p : Type.values())
			if (p.abbreviation.equalsIgnoreCase(abbreviation))
				return p;

		return null;
	}

}