package fantasy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import fantasy.pieces.Color;

public class PgnFileTest {

	@Test
	public void parseSingleEvent() throws IOException {
		String rawPgn = FileReader.readFile("Garry Kasparov_vs_Veselin Topalov_1999.__.__.pgn");

		List<Game> games = Tournament.parse(rawPgn).games;

		assertEquals(1, games.size());

		Game game = games.get(0);
		assertEquals("It (cat.17)", game.event);
		assertEquals("Garry Kasparov", game.playerWhite.name);
		assertEquals("Veselin Topalov", game.playerBlack.name);
		assertEquals(Color.WHITE, game.winner);
		assertNotNull(game.moves);
		assertFalse(game.moves.isEmpty());

	}

	@Test
	public void parseMultipleEvents() throws IOException {
		String rawPgn = FileReader.readFile("Norway_Chess_2016_AllGames.pgn");
		List<Game> games = Tournament.parse(rawPgn).games;

		assertEquals(45, games.size());

		Game game1 = games.get(0);
		assertEquals("Kramnik, Vladimir", game1.playerWhite.name);
		assertNotNull(game1.moves);
		assertFalse(game1.moves.isEmpty());

		Game game45 = games.get(44);
		assertEquals("Li, Chao", game45.playerWhite.name);
		assertNotNull(game45.moves);
		assertFalse(game45.moves.isEmpty());

	}

}
