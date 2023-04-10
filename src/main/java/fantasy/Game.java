package fantasy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fantasy.pieces.Color;

public class Game {

	public String event;
	public String site;
	public String date;

	public String round;
	public String playerWhite;
	public String playerBlack;
	public Color winner;
	public boolean draw = false;

	public String moves;

	public static List<Game> parse(String... pgns) throws IOException {
		List<Game> games = new ArrayList<>();

		for (String rawPgn : pgns) {
			Game game = null;
			rawPgn = rawPgn.trim();

			int index = 0;
			while (index < rawPgn.length()) {

				if ("\\n".equals(rawPgn.substring(index, index + 1))) {
					index++;
				} else if (rawPgn.substring(index).trim().startsWith("1.")) {
					var endOfMoves = rawPgn.indexOf("[", index);
					if (endOfMoves == -1)
						endOfMoves = rawPgn.length();

					game.moves = rawPgn.substring(index, endOfMoves);
					index = endOfMoves;

				} else {
					int nextEOL = rawPgn.indexOf("\n", index);
					if (nextEOL == -1)
						nextEOL = rawPgn.length() - 1;
					if (index > nextEOL)
						break;

					String line = rawPgn.substring(index, nextEOL);

					index = nextEOL + 1;

					String key = line.substring(1, line.indexOf("\"") - 1).trim().toUpperCase();
					String value = line.substring(line.indexOf("\"") + 1, line.indexOf("]") - 1);

					switch (key) {
					case "EVENT":
						game = new Game();
						games.add(game);
						game.event = value;
						break;
					case "SITE":
						game.site = value;
						break;
					case "DATE":
						game.date = value;
						break;
					case "ROUND":
						game.round = value;
						break;
					case "WHITE":
						game.playerWhite = value;
						break;
					case "BLACK":
						game.playerBlack = value;
						break;
					case "RESULT":
						if ("1-0".equals(value))
							game.winner = Color.WHITE;
						else if ("0-1".equals(value))
							game.winner = Color.BLACK;
						else
							game.draw = true;

						break;
					}
				}
			}
		}
		return games;
	}

	@Override
	public String toString() {
		return "Game [event=" + event + ", site=" + site + ", date=" + date + ", round=" + round + ", playerWhite="
				+ playerWhite + ", playerBlack=" + playerBlack + ", winner=" + winner + "]";
	}

}
