package fantasy;

import java.io.IOException;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class FantasyChessTest {

	@Test
	void playerPieceBets() throws IOException {

		Tournament t = Tournament.parse(FileReader.readFile("Garry Kasparov_vs_Veselin Topalov_1999.__.__.pgn"));

		FantasyChess chess = new FantasyChess();
		Set<Bet> bets = t.draftTeam();
		chess.play(t, bets);

		// TODO: Assertions on bet.score

	}

}
