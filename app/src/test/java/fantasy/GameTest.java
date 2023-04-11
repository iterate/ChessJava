package fantasy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class GameTest {

	@Test
	void twoPlayers() throws IOException {
		String pgn = FileReader.readFile("Garry Kasparov_vs_Veselin Topalov_1999.__.__.pgn");
		Tournament tournament = Tournament.parse(pgn);

		assertEquals(1, tournament.games.size());

		assertEquals(2, tournament.players.size());

		assertSame(tournament.players.get("Garry Kasparov"), tournament.games.get(0).playerWhite);
		assertEquals("Garry Kasparov", tournament.players.get("Garry Kasparov").name);
		assertSame(tournament.players.get("Veselin Topalov"), tournament.games.get(0).playerBlack);
		assertEquals("Veselin Topalov", tournament.players.get("Veselin Topalov").name);
	}

	@Test
	void manyPlayers() throws IOException {
		String pgn = FileReader.readFile("norway22.pgn");
		Tournament tournament = Tournament.parse(pgn);

		assertEquals(45, tournament.games.size());
		assertEquals(10, tournament.players.size());
	}

}
