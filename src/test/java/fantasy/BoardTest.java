package fantasy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import fantasy.pieces.Color;
import fantasy.pieces.Pawn;
import fantasy.pieces.Piece;
import fantasy.pieces.Type;

class BoardTest {

	@Test
	void simpleOpening() {
		Move[] moves = Move.parse("1. Nf3 Nf6 2. c4 g6 3. Nc3 Bg7 ");
		Board board = Board.newGame();
		board.play(moves);

		assertEquals(Type.KNIGHT, board.pieceAt("f3").type);
		assertEquals(Type.KNIGHT, board.pieceAt("f6").type);
		assertEquals(Type.PAWN, board.pieceAt("c4").type);
		assertEquals(Type.PAWN, board.pieceAt("g6").type);
		assertEquals(Type.KNIGHT, board.pieceAt("c3").type);
		assertEquals(Type.BISHOP, board.pieceAt("g7").type);

		assertTrue(board.isEmpty("g1"));
		assertTrue(board.isEmpty("g8"));
		assertTrue(board.isEmpty("c2"));
		assertTrue(board.isEmpty("b1"));
		assertTrue(board.isEmpty("f8"));
		assertTrue(board.isEmpty("g8"));
	}

	@Test
	void capture() {
		Move[] moves = Move.parse("1. d4 g5 2. f4 g4 3. h3 h5 4. hxg4 Nc6");
		Board board = Board.newGame();

		Piece capturedPawn = board.pieceAt("g7");

		board.play(moves);

		Piece winningPawn = board.pieceAt("g4");
		assertEquals(Type.PAWN, winningPawn.type);
		assertEquals(Color.WHITE, winningPawn.color);
		assertEquals(1, winningPawn.captures);

		assertTrue(capturedPawn.isCaptured());
	}

	@Test
	void castle() {
		Move[] moves = Move.parse("1. Nf3 Nf6 2. e3 d5 3. Be2 c5 4. O-O Bf5");
		Board board = Board.newGame();
		board.play(moves);

		assertEquals(Type.KING, board.pieceAt("g1").type);
		assertEquals(Type.ROOK, board.pieceAt("f1").type);

		moves = Move.parse("1. Nc3 Nf6 2. d4 d6 3. Qd3 e5 4. d5 Nbd7 5. Bd2 Be7 6. O-O-O O-O");
		board = Board.newGame();
		board.play(moves);

		assertEquals(Type.KING, board.pieceAt("c1").type);
		assertEquals(Type.ROOK, board.pieceAt("d1").type);
		assertEquals(Type.KING, board.pieceAt("g8").type);
		assertEquals(Type.ROOK, board.pieceAt("f8").type);
	}

	@Test
	void enPassant() {
		var chess = "1. e4 e5 2. Nf3 Nc6 3. Bb5 Nf6 4. d3 Bc5 5. Nbd2 O-O 6. Bxc6 bxc6 7. Nxe5 Re8\n"
				+ "8. Nef3 d5 9. O-O Bg4 10. h3 Bh5 11. Qe2 h6 12. Re1 a5 13. Qf1 a4 14. e5 Nd7\n"
				+ "15. d4 Bb6 16. c4 Nf8 17. c5 Ba5 18. Re3 Ne6 19. g4 Bg6 20. a3 f5 21. b4 axb3\n"
				+ "22. Nxb3 fxg4 23. hxg4 Rf8 24. Nh2 Be4 25. Rg3 Qe7 26. Ra2 g5 27. Rb2 Qh7 28.\n"
				+ "Qd1 Qf7 29. Qe2 Qg6 30. Qd1 Qf7 31. Qe2 Qg6 32. Qd1 1/2-1/2";

		Move[] moves = Move.parse(chess);
		Board board = Board.newGame();

		for (int i = 0; i < 20; i++) {
			Move move = moves[i];
			board.play(move.white, Color.WHITE);
			board.play(move.black, Color.BLACK);
		}
		// En Passant happens at move 21:
		board.play(moves[20].white, Color.WHITE);

		Pawn attacker = (Pawn) board.pieceAt("a4");
		assertTrue(attacker.isEnPassantReady());

		board.play(moves[20].black, Color.BLACK);
		assertTrue(board.isEmpty("b4"));
		assertEquals("b3", attacker.position.toNotation());
	}

	@Test
	void norwayChess2016Round2_1() {
		var chess = "1. e4 e5 2. Nf3 Nc6 3. Bb5 Nf6 4. d3 Bc5 5. Nbd2 O-O 6. Bxc6 bxc6 7. Nxe5 Re8\n"
				+ "8. Nef3 d5 9. O-O Bg4 10. h3 Bh5 11. Qe2 h6 12. Re1 a5 13. Qf1 a4 14. e5 Nd7\n"
				+ "15. d4 Bb6 16. c4 Nf8 17. c5 Ba5 18. Re3 Ne6 19. g4 Bg6 20. a3 f5 21. b4 axb3\n"
				+ "22. Nxb3 fxg4 23. hxg4 Rf8 24. Nh2 Be4 25. Rg3 Qe7 26. Ra2 g5 27. Rb2 Qh7 28.\n"
				+ "Qd1 Qf7 29. Qe2 Qg6 30. Qd1 Qf7 31. Qe2 Qg6 32. Qd1 1/2-1/2";

		Move[] moves = Move.parse(chess);
		Board board = Board.newGame();
		board.play(moves);
	}

	@Test
	void mamedyarovVsTari() {
		var chess = "1. d4 Nf6 2. c4 e6 3. Nc3 Bb4 4. Nf3 O-O 5. Qc2 c5 6. dxc5 Na6 7. g3 Nxc5\n"
				+ "8. Bg2 Nce4 9. O-O Bxc3 10. bxc3 Qc7 11. Nd4 d5 12. cxd5 exd5 13. Bf4 Qc5\n"
				+ "14. Rfb1 b6 15. Rb5 Qxc3 16. Qxc3 Nxc3 17. Rb3 Nce4 18. a4 Bd7 19. a5 Rfc8\n"
				+ "20. axb6 axb6 21. Rxa8 Rxa8 22. Rxb6 Ra1+ 23. Bf1 h6 24. Nc2 Ra2 25. Nb4\n"
				+ "Ra1 26. Nc2 Ra2 27. Nb4 Ra1 28. Nc2 1/2-1/2";

		Move[] moves = Move.parse(chess);
		Board board = Board.newGame();

		for (int i = 0; i < 9; i++) {
			Move move = moves[i];
			board.play(move.white, Color.WHITE);
			board.play(move.black, Color.BLACK);
		}
		board.play(moves[9].white, Color.WHITE);
		assertFalse(board.isEmpty("c3"));
		board.play(moves[9].black, Color.BLACK);
		for (int i = 10; i < 14; i++) {
			Move move = moves[i];
			board.play(move.white, Color.WHITE);
			board.play(move.black, Color.BLACK);
		}
		assertFalse(board.isEmpty("c3"));
		board.play(moves[14].white, Color.WHITE);
		board.play(moves[14].black, Color.BLACK);
		assertEquals(Type.QUEEN, board.findPieceAtSquare("c3").type);
	}

	@Test
	void topalovVsKasparov1999() {

		// Source: https://www.chess.com/games/view/969971

		var chess = "1. e4 d6 2. d4 Nf6 3. Nc3 g6 4. Be3 Bg7 5. Qd2 c6 6. f3 b5 7. Nge2 Nbd7 8. Bh6\n"
				+ "Bxh6 9. Qxh6 Bb7 10. a3 e5 11. O-O-O Qe7 12. Kb1 a6 13. Nc1 O-O-O 14. Nb3 exd4\n"
				+ "15. Rxd4 c5 16. Rd1 Nb6 17. g3 Kb8 18. Na5 Ba8 19. Bh3 d5 20. Qf4+ Ka7 21. Rhe1\n"
				+ "d4 22. Nd5 Nbxd5 23. exd5 Qd6 24. Rxd4 cxd4 25. Re7+ Kb6 26. Qxd4+ Kxa5 27. b4+\n"
				+ "Ka4 28. Qc3 Qxd5 29. Ra7 Bb7 30. Rxb7 Qc4 31. Qxf6 Kxa3 32. Qxa6+ Kxb4 33. c3+\n"
				+ "Kxc3 34. Qa1+ Kd2 35. Qb2+ Kd1 36. Bf1 Rd2 37. Rd7 Rxd7 38. Bxc4 bxc4 39. Qxh8\n"
				+ "Rd3 40. Qa8 c3 41. Qa4+ Ke1 42. f4 f5 43. Kc1 Rd2 44. Qa7 1-0";

		Move[] moves = Move.parse(chess);
		Board board = Board.newGame();
		board.play(moves);

		assertEquals(Type.QUEEN, board.pieceAt("a7").type);
		assertEquals(Type.PAWN, board.pieceAt("c3").type);
		assertEquals(Type.KING, board.pieceAt("c1").type);
		assertEquals(Type.ROOK, board.pieceAt("d2").type);
		assertEquals(Type.KING, board.pieceAt("e1").type);
		assertEquals(Type.PAWN, board.pieceAt("f5").type);
		assertEquals(Type.PAWN, board.pieceAt("f4").type);
		assertEquals(Type.PAWN, board.pieceAt("g6").type);
		assertEquals(Type.PAWN, board.pieceAt("g3").type);
		assertEquals(Type.PAWN, board.pieceAt("h7").type);
		assertEquals(Type.PAWN, board.pieceAt("h2").type);
	}

	@Test
	void morphyVsIsogouard1858() throws IOException {
		var chess = FileReader.readFile("Paul Morphy_vs_Duke of Brunswick and Count Isouard_1858.__.__.pgn");

		Move[] moves = Move.parse(chess);
		Board board = Board.newGame();
		board.play(moves);

		assertEquals(Type.ROOK, board.pieceAt("d8").type);
		assertEquals(Color.WHITE, board.pieceAt("d8").color);
		assertEquals(Type.KING, board.pieceAt("e8").type);
		assertEquals(Color.BLACK, board.pieceAt("e8").color);
		assertEquals(Type.BISHOP, board.pieceAt("g5").type);
		assertEquals(Color.WHITE, board.pieceAt("g5").color);

	}

	@Test
	void vachierVsMamedyarov() {
		var game = "1. d4 Nf6 2. c4 e6 3. Nf3 d5 4. Nc3 c5 5. cxd5 cxd4 6. Qxd4 exd5 7. Bg5 Be7\n"
				+ "8. e3 O-O 9. Qa4 h6 10. Bh4 Nc6 11. Bb5 Ne4 12. Bxe7 Nxe7 13. Rc1 Qb6 14.\n"
				+ "O-O a6 15. Nxe4 dxe4 16. Qxe4 Qxb5 17. Qxe7 Qxb2 18. a3 Bf5 19. Rc7 b5 20.\n"
				+ "Qb4 Qxb4 21. axb4 Rfc8 22. Rfc1 Rxc7 23. Rxc7 a5 24. bxa5 Rxa5 25. h3 b4\n"
				+ "26. Nd4 Be4 27. f3 Bd5 28. e4 Ra1+ 29. Kh2 Rd1 30. exd5 Rxd4 31. Rb7 Rxd5 32. Rxb4 1/2-1/2";

		Move[] moves = Move.parse(game);
		Board board = Board.newGame();
		board.play(moves);

		assertEquals(Type.ROOK, board.pieceAt("b4").type);

	}

	@Test
	void liVsKramnik() {
		var game = "1. e4 e5 2. Nc3 Nf6 3. g3 Bc5 4. Bg2 c6 5. Nge2 d5 6. exd5 cxd5 7. d4 exd4 8.\n"
				+ "Nxd4 O-O 9. O-O Bg4 10. Qd3 Bxd4 11. Qxd4 Nc6 12. Qc5 d4 13. h3 Bxh3 14. Bxh3\n"
				+ "dxc3 15. bxc3 Re8 16. Qb5 Qa5 17. a4 Qxc3 18. Bb2 Qxc2 19. Rac1 Qe2 20. Bg2 Qe7\n"
				+ "21. Bxf6 Qxf6 22. Qxb7 Nd4 23. Rfe1 h5 24. Qxa8 Rxa8 25. Bxa8 h4 26. Rc8+ Kh7\n"
				+ "27. Be4+ g6 28. gxh4 Qe6 29. Re8 Qg4+ 30. Kf1 Qxh4 31. Re7 Qh3+ 32. Kg1 Qg4+\n"
				+ "33. Kf1 Qh3+ 34. Kg1 Kg7 35. Bd5 Qg4+ 36. Kf1 Qh3+ 37. Kg1 Nf3+ 38. Bxf3 Qxf3\n"
				+ "39. Rxa7 Qg4+ 40. Kf1 Qh3+ 41. Kg1 Qg4+ 42. Kf1 Qh3+ 1/2-1/2\n";

		Move[] moves = Move.parse(game);
		Board board = Board.newGame();

		for (int i = 0; i < 15; i++) {
			Move move = moves[i];
			board.play(move.white, Color.WHITE);
			board.play(move.black, Color.BLACK);
		}

		assertEquals(Type.ROOK, board.pieceAt("e8").type);
		assertEquals(Color.BLACK, board.pieceAt("e8").color);

		assertEquals(Type.QUEEN, board.pieceAt("d8").type);
		assertEquals(Color.BLACK, board.pieceAt("d8").color);

		assertEquals(Type.ROOK, board.pieceAt("a8").type);
		assertEquals(Color.BLACK, board.pieceAt("a8").color);

	}

	@Test
	void vachierVsEljanov() {
		var chess = "1. e4 e5 2. Nf3 Nc6 3. Bb5 Nf6 4. O-O Nxe4 5. d4 Nd6 6. Bxc6 dxc6 7. dxe5 Nf5\n"
				+ "8. Qxd8+ Kxd8 9. h3 Be7 10. Nc3 Nh4 11. Nxh4 Bxh4 12. Be3 h5 13. Rad1+ Ke8 14.\n"
				+ "Ne2 Be7 15. Rfe1 a6 16. Bf4 g5 17. Be3 Bf5 18. Nd4 Bg6 19. e6 Rd8 20. exf7+\n"
				+ "Kxf7 21. Nf3 Bxc2 22. Rxd8 Rxd8 23. Nxg5+ Kf6 24. Ne6 Rd1 25. Bg5+ Kf7 26. Rxd1\n"
				+ "Bxd1 27. Bxe7 Kxe6 28. Bd8 Kd6 29. f3 c5 30. Kf2 b5 31. Ke3 b4 32. g4 hxg4 33.\n"
				+ "fxg4 a5 34. Kf4 c4 35. Bf6 c5 36. h4 Ke6 37. Bh8 Kf7 38. h5 a4 39. a3 Be2 40.\n"
				+ "Be5 c3 41. bxc3 bxa3 42. c4 Bxc4 43. g5 Bd3 44. g6+ Bxg6 45. hxg6+ Kxg6 46. Ke4\n"
				+ "c4 47. Ba1 c3 48. Kd3 a2 49. Kxc3 a3 50. Kb3 Kh7 51. Kxa2 Kg8 52. Kxa3 1/2-1/2";

		Move[] moves = Move.parse(chess);
		Board board = Board.newGame();
		board.play(moves);

		assertEquals(Type.KING, board.pieceAt("a3").type);
	}

	@Test
	void kramnikVsEljanov() {
		var chess = "1. e4 e5 2. Nf3 Nc6 3. Bc4 Bc5 4. c3 Nf6 5. d3 d6 6. O-O a6 7. Re1 h6 8. Nbd2\n"
				+ "O-O 9. Nf1 Ba7 10. Bb3 Re8 11. Ng3 Be6 12. Bxe6 Rxe6 13. d4 Re8 14. h3 d5 15.\n"
				+ "Nxe5 Nxe5 16. dxe5 Nxe4 17. Nxe4 dxe4 18. Qg4 Rxe5 19. Bxh6 g5 20. Kh1 Qf6 21.\n"
				+ "f4 Qxf4 22. Qxf4 gxf4 23. Bxf4 Re7 24. g4 Kg7 25. Rad1 f5 26. Rd5 fxg4 27. hxg4\n"
				+ "Rf8 28. Re5 Rxe5 29. Bxe5+ Kg6 30. Rxe4 Rf1+ 31. Kg2 Rf2+ 32. Kg3 Rxb2 33. Rf4\n"
				+ "Re2 34. Rf6+ Kg5 35. Rf5+ Kg6 36. a4 c6 37. a5 b5 38. axb6 Bxb6 39. Rf6+ Kg7\n"
				+ "40. Rxc6+ Rxe5 41. Rxb6 Re3+ 42. Kf4 Rxc3 43. Rxa6 1/2-1/2";

		Move[] moves = Move.parse(chess);
		Board board = Board.newGame();
		board.play(moves);

		assertEquals(Type.ROOK, board.pieceAt("a6").type);
	}

	@Test
	void grandeliusVsHarikrishna() {
		var chess = "1. e4 e6 2. d4 d5 3. Nc3 Nf6 4. Bg5 dxe4 5. Nxe4 Be7 6. Bxf6 gxf6 7. Qd2 b6 8.\n"
				+ "O-O-O Bb7 9. Nc3 c6 10. Nf3 Nd7 11. Qh6 Qc7 12. Qg7 Rf8 13. Qxh7 f5 14. Bc4 Nf6\n"
				+ "15. Qh6 Ng4 16. Qd2 O-O-O 17. h3 Nf6 18. Ne5 Kb8 19. Rhe1 b5 20. Bb3 b4 21. Ne2\n"
				+ "c5 22. Qe3 Ne4 23. f4 cxd4 24. Nxd4 Bc5 25. Nef3 Rg8 26. g4 fxg4 27. hxg4 Rxg4\n"
				+ "28. Ne5 Rxd4 29. Rxd4 Rg3 30. Qxe4 Bxe4 31. Rdxe4 Rg1 32. Rxg1 Bxg1 33. Rxb4+\n"
				+ "Bb6 34. a4 Ka8 35. Kb1 Qe7 36. Rc4 Bc7 37. Nd3 Bd6 38. a5 Kb8 39. Ba4 Qb7 40.\n"
				+ "Rc6 Bxf4 41. Nc5 Qe7 42. Na6+ Kb7 43. Nc5+ Kb8 44. Na6+ Kb7 45. Nc5+ 1/2-1/2";

		Move[] moves = Move.parse(chess);
		Board board = Board.newGame();
		board.play(moves);

		assertEquals(Type.ROOK, moves[31].white.type);
		assertEquals(Type.BISHOP, moves[31].black.type);

		assertEquals(Type.KNIGHT, board.pieceAt("c5").type);
	}

	@Test
	void populate() {
		Board board = Board.newGame();
		Piece d2Pawn = board.pieceAt("d2");

		Piece teamPawn = new Pawn(Color.WHITE, "d2");
		Set<Piece> team = new LinkedHashSet<>();
		team.add(teamPawn);
		board.populate(team);

		assertEquals(teamPawn, board.pieceAt("d2"));
		assertTrue(board.pieces.contains(teamPawn));
		assertFalse(board.pieces.contains(d2Pawn));
	}

}
