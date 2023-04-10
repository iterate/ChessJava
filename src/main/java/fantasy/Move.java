package fantasy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Move {

	public HalfMove white; // white's move
	public HalfMove black; // black's move

	public static Move[] parse(String game) {
		game = game.replace("\n", " ").trim();
		if (game.endsWith("1/2-1/2"))
			game = game.substring(0, game.length() - 7);
		else if (game.endsWith("1-0") || game.endsWith("0-1"))
			game = game.substring(0, game.length() - 3);

		String[] moves = game.split("\\d+\\.\\s");
		if (moves.length <= 1)
			return new Move[0];

		List<Move> result = new ArrayList<Move>();

		for (int i = 1; i < moves.length; i++) {
			var fragment = moves[i].trim();

			Move move = new Move();

			if (fragment.contains(" ")) {
				var player1 = fragment.substring(0, fragment.indexOf(" ")).trim();
				var player2 = fragment.substring(fragment.indexOf(" ")).trim();

				move.white = HalfMove.parse(player1);
				move.white.number = i;
				move.black = HalfMove.parse(player2);
				move.black.number = i;
			} else {
				move.white = HalfMove.parse(fragment);
			}
			result.add(move);
		}
		return result.toArray(new Move[result.size()]);
	}

	@Override
	public int hashCode() {
		return Objects.hash(black, white);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Move other = (Move) obj;
		return Objects.equals(black, other.black) && Objects.equals(white, other.white);
	}

	public String toString() {
		String w = "[?]";
		if (white != null)
			if (white.type != null)
				w = white.type.abbreviation + white.to;
			else if (white.castle != null)
				w = white.castle.notation;

		String b = "[?]";
		if (black != null)
			if (black.type != null)
				b = black.type.abbreviation + black.to;
			else if (black.castle != null)
				b = black.castle.notation;

		return w + " " + b;
	}

}
