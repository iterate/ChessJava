package fantasy;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import fantasy.pieces.Color;

public class Tournament {

	public String name;
	public String date;
	public String site;

	final List<Game> games = new ArrayList<>();
	final Map<String, Player> players = new LinkedHashMap<>();

	@Override
	public String toString() {
		return "Tournament [name=" + name + ", date=" + date + ", site=" + site + ", games=" + games.size()
				+ ", players=" + players.size() + "]";
	}

	public static Tournament parse(String... pgns) {
		Tournament tournament = new Tournament();
		List<Game> games = tournament.games;

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
						game.playerWhite = new Player(value);
						break;
					case "BLACK":
						game.playerBlack = new Player(value);
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
		return tournament;
	}

}
