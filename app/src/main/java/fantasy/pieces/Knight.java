package fantasy.pieces;

import fantasy.HalfMove;
import fantasy.Position;

public class Knight extends Piece {

	public Knight(Color color, String position) {
		super(Type.KNIGHT, color, position);
	}

	@Override
	public boolean isLegalMove(HalfMove halfMove) {
		Position toPos = Position.parse(halfMove.to);

		if ((position.x == (toPos.x + 1)) || (position.x == (toPos.x - 1)))
			return (position.y == (toPos.y + 2)) || (position.y == (toPos.y - 2));

		if ((position.y == (toPos.y + 1)) || (position.y == (toPos.y - 1)))
			return (position.x == (toPos.x + 2)) || (position.x == (toPos.x - 2));

		return false;
	}

}
