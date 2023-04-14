package fantasy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import fantasy.pieces.Color;
import fantasy.pieces.Piece;
import fantasy.pieces.Type;

class BetTest {

	@Test
	void singleBet() throws IOException {

		Tournament t = Tournament.parse(FileReader.readFile("Garry Kasparov_vs_Veselin Topalov_1999.__.__.pgn"));

		Bet whiteQueen = new Bet(t.players.get("Garry Kasparov"), new Piece(Type.QUEEN, Color.WHITE, "d1"));
		Set<Bet> bets = new LinkedHashSet<>();
		bets.add(whiteQueen);
		new FantasyChess().play(t, bets);

		// White Queen has 13 moves and 5 captures in this game
		assertEquals(115, whiteQueen.calculateScore());

	}

}
