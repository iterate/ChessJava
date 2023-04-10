package fantasy;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fantasy.pieces.Type;

public class HalfMove {

	static final Pattern ALGEBRAIC_NOTATION = Pattern
			.compile("([PNBRQK])?([a-h])?([1-8])?(x)?([a-h][1-8])(=?[PNBRQ])?(\\+|#)?");

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

	public HalfMove() {

	}

	public HalfMove(Castle castle) {
		this.castle = castle;
	}

	public HalfMove(Type type, String to) {
		this.type = type;
		this.to = to.toLowerCase();
	}

	public static HalfMove parse(String target) {

		if (Castle.KING_SIDE.notation.equals(target))
			return new HalfMove(Castle.KING_SIDE);
		else if (Castle.QUEEN_SIDE.notation.equals(target))
			return new HalfMove(Castle.QUEEN_SIDE);

		Matcher matcher = ALGEBRAIC_NOTATION.matcher(target);
		if (!matcher.matches())
			throw new ChessException("Cannot parse: \"" + target + "\"");

		HalfMove result = new HalfMove();
		if (matcher.group(1) == null)
			result.type = Type.PAWN;
		else
			result.type = Type.parse(matcher.group(1));

		if (matcher.group(2) != null)
			result.fromFile = matcher.group(2).charAt(0);

		if (matcher.group(3) != null)
			result.fromRank = Integer.parseInt(matcher.group(3));

		result.to = matcher.group(5);

		result.capture = matcher.group(4) != null;

		if (matcher.group(6) != null)
			result.promotedTo = Type.parse(matcher.group(6).substring(1));

		result.check = "+".equals(matcher.group(7));
		result.checkmate = "#".equals(matcher.group(7));

		return result;
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