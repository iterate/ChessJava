package fantasy;

public enum Castle {

	KING_SIDE("O-O"), QUEEN_SIDE("O-O-O");

	String notation;

	Castle(String notation) {
		this.notation = notation;
	}
}
