package fantasy;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class EndToEndTest {

	@Test
	public void norwayChess2016() throws IOException {
		List<Game> games = Game.parse(FileReader.readFile("Norway_Chess_2016_AllGames.pgn"));

		assertEquals(45, games.size());

		FantasyChess fantasyChess = new FantasyChess();
		Standings report = fantasyChess.play(games, fantasyChess.draftTeam());
		assertEquals(45, report.gamesPlayed);

//		int gamesPlayed = 0;
//		for (Game game : games) {
//			Board board = Board.newGame();
//			board.game = game;
//			boolean played = board.play(Move.parse(game.moves));
//			if (played)
//				gamesPlayed++;
//		}
//
//		assertEquals(45, gamesPlayed);
	}

}
