package fantasy.pieces;

import fantasy.HalfMove;
import fantasy.Position;

public class Pawn extends Piece {

	int advances = 0;

	public Pawn(Color color, String position) {
		super(Type.PAWN, color, position);
	}

	@Override
	public boolean isLegalMove(HalfMove halfMove) {

		var destination = Position.parse(halfMove.to);

		if (halfMove.capture) {

			if (halfMove.fromFile != null && position.x != Position.parseFile(halfMove.fromFile))
				return false;
			else {
				if (Color.WHITE == color)
					return destination.y - position.y == 1 && Math.abs(position.x - destination.x) == 1;
				else if (Color.BLACK == color)
					return position.y - destination.y == 1 && Math.abs(position.x - destination.x) == 1;
			}

		}
		if (position.x == destination.x) {
			if ((position.y == 2 && color.equals(Color.WHITE)) || (position.y == 7 && color.equals(Color.BLACK))) {
				if (Math.abs(destination.y - position.y) == 2 || Math.abs(destination.y - position.y) == 1)
					return true;
			} else {
				if (Color.WHITE == color)
					return destination.y - position.y == 1;
				else if (Color.BLACK == color)
					return position.y - destination.y == 1;
			}
		}
		return false;
	}

	public boolean isEnPassantReady() {
		return (this.advances == 3);
	}

	public int advances() {
		return this.advances;
	}

	@Override
	public void reset() {
		this.advances = 0;
		super.reset();
	}

	@Override
	public void moveTo(String to) {
		Position toPos = Position.parse(to);
		int diff = Math.abs(toPos.y - position.y);
		this.advances += diff;
		super.moveTo(to);
	}

}
