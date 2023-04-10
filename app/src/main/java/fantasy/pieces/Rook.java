package fantasy.pieces;

import fantasy.HalfMove;
import fantasy.Position;

public class Rook extends Piece {

	public Rook(Color color, String position) {
		super(Type.ROOK, color, position);
	}

	@Override
	public boolean isLegalMove(HalfMove halfMove) {
		Position toPos = Position.parse(halfMove.to);
		return (this.position.x == toPos.x || this.position.y == toPos.y);
	}

}
