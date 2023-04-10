package fantasy;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fantasy.pieces.Type;

public class HalfMove {

	public int number;
	public Type type;
	public String to;
	public Castle castle;
	public boolean check;
	public boolean checkmate;
	public boolean capture;
	public Character fromFile;
	public Integer fromRank;

	public Type promotedTo = null;

	public HalfMove(Castle castle) {
		this.castle = castle;
	}

	public HalfMove(Type type, String to) {
		this.type = type;
		this.to = to.toLowerCase();
	}

	static HalfMove parse(String target) {

		if (Castle.KING_SIDE.notation.equals(target))
			return new HalfMove(Castle.KING_SIDE);
		else if (Castle.QUEEN_SIDE.notation.equals(target))
			return new HalfMove(Castle.QUEEN_SIDE);

		Type type = null;
		boolean check = false;
		Type promotedTo = null;
		Character fromFile = null;
		Integer fromRank = null;
		String square = "";
		boolean capture = false;

		Pattern pattern = Pattern.compile("([PNBRQK])?([a-h])?([1-8])?(x)?([a-h][1-8])(=?[PNBRQ])?(\\+|#)?");
		Matcher matcher = pattern.matcher(target);
		if (matcher.matches()) {
			if (matcher.group(1) == null)
				type = Type.PAWN;
			else
				type = Type.parse(matcher.group(1));
			check = matcher.group(7) != null;
			if (matcher.group(6) != null) {
				promotedTo = Type.parse(matcher.group(6).substring(1));
			}
			if (matcher.group(2) != null)
				fromFile = matcher.group(2).charAt(0);
			if (matcher.group(3) != null)
				fromRank = Integer.parseInt(matcher.group(3));
			square = matcher.group(5);

			if (matcher.group(4) != null)
				capture = true;

		}

		HalfMove halfMove = new HalfMove(type, square);
		halfMove.capture = capture;
		halfMove.checkmate = target.contains("#");
		halfMove.fromFile = fromFile;
		halfMove.fromRank = fromRank;
		halfMove.check = check;
		halfMove.promotedTo = promotedTo;
		return halfMove;
	}

	@Override
	public int hashCode() {
		return Objects.hash(castle, check, type, to);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HalfMove other = (HalfMove) obj;
		return castle == other.castle && check == other.check && type == other.type && Objects.equals(to, other.to);
	}

	@Override
	public String toString() {
		return "HalfMove [number=" + number + ", piece=" + type + ", to=" + to + ", castle=" + castle + ", check="
				+ check + ", capture=" + capture + ", fromFile=" + fromFile + ", fromRank=" + fromRank
				+ ((promotedTo != null) ? ", promotedTo=" + promotedTo.name() : "") + "]";
	}

}