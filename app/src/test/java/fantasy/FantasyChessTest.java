package fantasy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import fantasy.pieces.Color;
import fantasy.pieces.Pawn;

public class FantasyChessTest {

	@Test
	void playerPieceBets() throws IOException {

		Tournament t = Tournament.parse(FileReader.readFile("Garry Kasparov_vs_Veselin Topalov_1999.__.__.pgn"));

		FantasyChess chess = new FantasyChess();
		Set<Bet> bets = new LinkedHashSet<>();

		Bet bet1 = new Bet(t.players.get("Garry Kasparov"), new Pawn(Color.WHITE, "h2"));
		bets.add(bet1);
		Bet bet2 = new Bet(t.players.get("Garry Kasparov"), new Pawn(Color.WHITE, "g2"));
		bets.add(bet2);

		chess.play(t, bets);

		assertEquals(0, bet1.score);
		assertEquals(1, bet2.score);

	}

}
