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

//		String fragment = target;
//		if (fragment.endsWith("#")) {
//			checkmate = true;
//			fragment = fragment.substring(0, fragment.length() - 1);
//		}
//		if (fragment.endsWith("+")) {
//			check = true;
//			fragment = fragment.substring(0, fragment.length() - 1);
//		}
//		if (fragment.contains("=")) {
//			int equalsIndex = fragment.indexOf('=');
//			String pieceTypeAbbreviation = Character.toString(fragment.charAt(equalsIndex + 1));
//			promotedTo = Type.parse(pieceTypeAbbreviation);
//			String foo = fragment.substring(0, equalsIndex);
//			fragment = foo;
//			if ((equalsIndex + 1) < fragment.length())
//				fragment = fragment.substring(equalsIndex + 1);
//		}
//
//		if (Character.isLowerCase(fragment.charAt(0))) {
//			type = Type.PAWN;
//
//			if (fragment.length() == 2) {
//				square = fragment;
//			} else {
//				square = fragment.substring(fragment.length() - 2);
//			}
//
//			if (fragment.contains("x") && fragment.charAt(0) != 'x') {
//				fromFile = fragment.charAt(0);
//			}
//
//		} else {
//			type = Type.parse(fragment.substring(0, 1));
//
//			if (fragment.contains("x")) {
//				square = fragment.substring(fragment.indexOf("x") + 1);
//			} else if (fragment.length() == 4) {
//
//				char c = fragment.charAt(1);
//				if (Character.isDigit(c))
//					fromRank = Integer.parseInt(Character.toString(c));
//				else
//					fromFile = c;
//			}
//
//			if (fragment.length() == 5) {
//				char c = fragment.charAt(1);
//				if (Character.isDigit(c))
//					fromRank = Integer.parseInt(Character.toString(c));
//				else
//					fromFile = c;
//			}
//
//			if (fragment.endsWith("+")) {
//				check = true;
//				square = fragment.substring(fragment.length() - 3, fragment.length() - 1);
//			} else
//				square = fragment.substring(fragment.length() - 2);
//		}

		HalfMove halfMove = new HalfMove(type, square);
//		halfMove.capture = fragment.contains("x");
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