package fantasy;

import java.io.IOException;
import java.util.Set;

public class FantasyChess {

	public static void main(String[] args) throws IOException {

		String fileName;
		if (args.length > 0)
			fileName = args[0];
		else {
			System.out.println("Missing argument: [filename]");
			return;
		}
		Tournament t = Tournament.parse(FileReader.readFile(fileName));
		Set<Bet> myBets = t.draftTeam();
		FantasyChess fantasyChess = new FantasyChess();
		Standings standings = fantasyChess.play(t, myBets);

		if (fileName != null)
			System.out.println("File: " + fileName + "\n");
		System.out.println(standings);
	}

	public Standings play(Tournament tournament, Set<Bet> bets) {

		int gamesPlayed = 0;
		for (Game game : tournament.games) {
			Board board = Board.newGame();
			board.game = game;
			board.populate(bets);
			boolean played = board.play(Move.parse(game.moves));
			if (played)
				gamesPlayed++;
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("\nFANTASY CHESS - Every piece matters\n");
		buffer.append("-".repeat(100) + "\n");

		buffer.append("Team: ");
		for (Bet bet : bets)
			buffer.append(
					bet.piece.type + " " + bet.piece.originalPosition.toNotation() + " (" + bet.player.name + "), ");
		buffer.deleteCharAt(buffer.lastIndexOf(","));
		buffer.append("\n\n");

		buffer.append(gamesPlayed + " matches played:\n");
		for (Bet bet : bets) {
			buffer.append("\t" + bet.report() + "\n");
		}
		return new Standings(gamesPlayed, buffer.toString());
	}

}
