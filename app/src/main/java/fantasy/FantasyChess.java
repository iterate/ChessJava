package fantasy;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import fantasy.pieces.Bishop;
import fantasy.pieces.Color;
import fantasy.pieces.Knight;
import fantasy.pieces.Pawn;
import fantasy.pieces.Piece;
import fantasy.pieces.Rook;

public class FantasyChess {

	public static void main(String[] args) throws IOException {
		List<Game> games = Game.parse(FileReader.readFile("Norway_Chess_2016_AllGames.pgn"));

		FantasyChess fantasyChess = new FantasyChess();
		Set<Piece> myTeam = fantasyChess.draftTeam();
		Standings standings = fantasyChess.play(games, myTeam);

		System.out.println(standings);
	}

	public Standings play(List<Game> games, Set<Piece> team) {
		int gamesPlayed = 0;
		for (Game game : games) {
			Board board = Board.newGame();
			board.game = game;
			board.populate(team);
			boolean played = board.play(Move.parse(game.moves));
			if (played)
				gamesPlayed++;
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("\nFANTASY CHESS - Every piece matters\n");
		buffer.append("-".repeat(100) + "\n");
		buffer.append("Team: ");
		for (Piece p : team)
			buffer.append(p.type + " " + p.originalPosition.toNotation() + ", ");
		buffer.deleteCharAt(buffer.lastIndexOf(","));
		buffer.append("\n\n");

		buffer.append(gamesPlayed + " matches played:\n");
		for (Piece piece : team) {
			buffer.append("\t" + piece.report() + "\n");
		}
		return new Standings(gamesPlayed, buffer.toString());
	}

	public Set<Piece> draftTeam() {
		Set<Piece> team = new LinkedHashSet<>();
		team.add(new Rook(Color.WHITE, "a1"));
		team.add(new Knight(Color.WHITE, "b1"));
		team.add(new Bishop(Color.WHITE, "f1"));
		team.add(new Knight(Color.WHITE, "g1"));
		team.add(new Pawn(Color.WHITE, "a2"));
		team.add(new Pawn(Color.WHITE, "d2"));
		team.add(new Pawn(Color.WHITE, "e2"));
		team.add(new Pawn(Color.WHITE, "f2"));
		team.add(new Pawn(Color.WHITE, "h2"));
		return team;
	}

}