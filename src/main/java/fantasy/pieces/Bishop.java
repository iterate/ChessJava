package fantasy.pieces;

import fantasy.HalfMove;
import fantasy.Position;

public class Bishop extends Piece {

	public Bishop(Color color, String position) {
		super(Type.BISHOP, color, position);
	}

	@Override
	public boolean isLegalMove(HalfMove halfMove) {
		var positionTo = Position.parse(halfMove.to);

		int dx = Math.abs(positionTo.x - position.x);
		int dy = Math.abs(positionTo.y - position.y);
		return dx == dy;
	}

}
