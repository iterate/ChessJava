package fantasy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class EndToEndTest {

	@Test
	public void norwayChess2016() throws IOException {
		Tournament t = Tournament.parse(FileReader.readFile("Norway_Chess_2016_AllGames.pgn"));
		assertEquals(45, t.games.size());

		FantasyChess fantasyChess = new FantasyChess();
		Standings report = fantasyChess.play(t, t.draftTeam());
		assertEquals(45, report.gamesPlayed);
	}

}
