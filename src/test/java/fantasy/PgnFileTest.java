package fantasy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import fantasy.pieces.Color;

public class PgnFileTest {

	@Test
	public void parseSingleEvent() throws IOException {
		String rawPgn = FileReader.readFile("Garry Kasparov_vs_Veselin Topalov_1999.__.__.pgn");

		List<Game> events = Game.parse(rawPgn);

		assertEquals(1, events.size());

		Game event = events.get(0);
		assertEquals("It (cat.17)", event.event);
		assertEquals("Garry Kasparov", event.playerWhite);
		assertEquals("Veselin Topalov", event.playerBlack);
		assertEquals(Color.WHITE, event.winner);
		assertNotNull(event.moves);
		assertFalse(event.moves.isEmpty());

	}

	@Test
	public void parseMultipleEvents() throws IOException {
		String rawPgn = FileReader.readFile("Norway_Chess_2016_AllGames.pgn");
		List<Game> events = Game.parse(rawPgn);

		assertEquals(45, events.size());

		Game event1 = events.get(0);
		assertEquals("Kramnik, Vladimir", event1.playerWhite);
		assertNotNull(event1.moves);
		assertFalse(event1.moves.isEmpty());

		Game event45 = events.get(44);
		assertEquals("Li, Chao", event45.playerWhite);
		assertNotNull(event45.moves);
		assertFalse(event45.moves.isEmpty());

	}

}
