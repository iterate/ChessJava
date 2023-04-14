package fantasy;

import java.io.IOException;
import java.util.Set;

import util.CommandLineParser;

public class FantasyChess {

	public static void main(String[] args) throws IOException {

		CommandLineParser arguments = new CommandLineParser(args);

		String fileName = arguments.getArgument("-file");
		if (fileName == null) {
			System.out.println(
					"Usage:\n\t-file [Path to PGN file]\tPlay this tournament\n\t-info\t\t\t\tPrint tournament info");
			return;
		}
		Tournament t = Tournament.parse(FileReader.readFile(fileName));
		if (arguments.getArgument("-info") != null) {
			System.out.println("Tournament: " + t.name + " on " + t.date + ". Site: " + t.site);
			System.out.println("-".repeat(100));
			System.out.println("Players:");
			for (Player p : t.players.values())
				System.out.println("\t" + p.name);
			System.exit(0);
		}

		Set<Bet> myBets = t.draftTeam();
		FantasyChess fantasyChess = new FantasyChess();
		Standings standings = fantasyChess.play(t, myBets);
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
