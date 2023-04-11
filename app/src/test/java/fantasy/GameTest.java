package fantasy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

public class GameTest {

	@Test
	void players() throws IOException {
		String pgn = FileReader.readFile("Garry Kasparov_vs_Veselin Topalov_1999.__.__.pgn");
		Tournament tournament = Tournament.parse(pgn);

		assertEquals(1, tournament.games.size());
		assertEquals("Garry Kasparov", tournament.games.get(0).playerWhite.name);
		assertEquals("Veselin Topalov", tournament.games.get(0).playerBlack.name);
	}

}
