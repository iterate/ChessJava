package fantasy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fantasy.pieces.Type;

class NotationTest {

	@Test
	void pawnToE4() {
		var whiteMovesPawnToE4 = "1. e4";

		Move[] result = Move.parse(whiteMovesPawnToE4);
		assertEquals(1, result.length);
		assertEquals(new HalfMove(Type.PAWN, "E4"), result[0].white);
	}

	@Test
	void threeMoveSequence() {
		var threeMoveSequence = "1. Nf3 Nf6 2. c4 g6 3. Nc3 Bg7 ";

		Move[] moves = Move.parse(threeMoveSequence);

		assertEquals(3, moves.length);
		assertEquals(new HalfMove(Type.KNIGHT, "f3"), moves[0].white);
		assertEquals(new HalfMove(Type.KNIGHT, "f6"), moves[0].black);
		assertEquals(new HalfMove(Type.PAWN, "c4"), moves[1].white);
		assertEquals(new HalfMove(Type.PAWN, "g6"), moves[1].black);
		assertEquals(new HalfMove(Type.KNIGHT, "c3"), moves[2].white);
		assertEquals(new HalfMove(Type.BISHOP, "g7"), moves[2].black);
	}

	@Test
	void variousHalfMoves() {
		HalfMove move1 = HalfMove.parse("Nge2");
		assertEquals(Type.KNIGHT, move1.type);
		assertSame('g', move1.fromFile);
		assertEquals("e2", move1.to);

		HalfMove move2 = HalfMove.parse("Nbd7");
		assertEquals(Type.KNIGHT, move2.type);
		assertSame('b', move2.fromFile);
		assertEquals("d7", move2.to);

		HalfMove move3 = HalfMove.parse("Qxh6");
		assertEquals(Type.QUEEN, move3.type);
		assertTrue(move3.capture);
		assertEquals("h6", move3.to);

		HalfMove move4 = HalfMove.parse("Bxh6");
		assertEquals(Type.BISHOP, move4.type);
		assertTrue(move4.capture);
		assertNull(move4.fromFile);
		assertEquals("h6", move4.to);

		HalfMove move5 = HalfMove.parse("Qf4+");
		assertEquals(Type.QUEEN, move5.type);
		assertTrue(move5.check);
		assertNull(move5.fromFile);
		assertEquals("f4", move5.to);

		HalfMove move6a = HalfMove.parse("b4");
		assertEquals(Type.PAWN, move6a.type);
		assertFalse(move6a.check);
		assertEquals("b4", move6a.to);

		HalfMove move6b = HalfMove.parse("b4+");
		assertEquals(Type.PAWN, move6b.type);
		assertTrue(move6b.check);
		assertEquals("b4", move6b.to);

		HalfMove move7 = HalfMove.parse("exd4");
		assertEquals(Type.PAWN, move7.type);
		assertTrue(move7.capture);
		assertSame('e', move7.fromFile);
		assertEquals("d4", move7.to);

		HalfMove move8 = HalfMove.parse("Rd8#");
		assertEquals(Type.ROOK, move8.type);
		assertFalse(move8.capture);
		assertNull(move8.fromFile);
		assertEquals("d8", move8.to);
		assertTrue(move8.checkmate);

		HalfMove move9 = HalfMove.parse("dxc3+");
		assertEquals(Type.PAWN, move9.type);
		assertTrue(move9.capture);
		assertEquals('d', move9.fromFile);
		assertEquals("c3", move9.to);
		assertTrue(move9.check);

		HalfMove move10 = HalfMove.parse("b8=Q+");
		assertEquals(Type.PAWN, move10.type);
		assertFalse(move10.capture);
		assertTrue(move10.check);
		assertNull(move10.fromFile);
		assertEquals("b8", move10.to);
		assertEquals(Type.QUEEN, move10.promotedTo);
	}

	@Test
	void promotion() {
		HalfMove move = HalfMove.parse("axb8=Q+");
		assertEquals(Type.PAWN, move.type);
		assertTrue(move.capture);
		assertTrue(move.check);
		assertEquals('a', move.fromFile);
		assertEquals("b8", move.to);
		assertEquals(Type.QUEEN, move.promotedTo);
	}

	@Test
	void fromRank() {
		HalfMove move = HalfMove.parse("N2f3");
		assertEquals(Type.KNIGHT, move.type);
		assertFalse(move.capture);
		assertFalse(move.check);
		assertEquals(2, move.fromRank);
		assertEquals("f3", move.to);
	}

	@Test
	void fromFileWithRook() {

		HalfMove move = HalfMove.parse("Rdxe4");
		assertEquals(Type.ROOK, move.type);
		assertTrue(move.capture);
		assertFalse(move.check);
		assertEquals('d', move.fromFile);
		assertEquals("e4", move.to);

	}

	@Test
	void castle() {
		var game = "1. O-O-O Qe7";
		Move[] moves = Move.parse(game);

		assertEquals(1, moves.length);
		assertEquals(Castle.QUEEN_SIDE, moves[0].white.castle);
		assertEquals(Type.QUEEN, moves[0].black.type);
		assertEquals("e7", moves[0].black.to);
	}

	@Test
	void newLine() {
		var fragment = "8. Bh6\n" + "Bxh6";
		Move[] moves = Move.parse(fragment);

		assertEquals(1, moves.length);
		assertEquals(Type.BISHOP, moves[0].white.type);
		assertEquals(Type.BISHOP, moves[0].black.type);
		assertTrue(moves[0].black.capture);
	}

	@Test
	void topalovVsKasparov1999() {
		var game = "1. e4 d6 2. d4 Nf6 3. Nc3 g6 4. Be3 Bg7 5. Qd2 c6 6. f3 b5 7. Nge2 Nbd7 8. Bh6 Bxh6 9. Qxh6 Bb7 10. a3 e5 11. O-O-O Qe7 12. Kb1 a6 13. Nc1 O-O-O 14. Nb3 exd4 15. Rxd4 c5 16. Rd1 Nb6 17. g3 Kb8 18. Na5 Ba8 19. Bh3 d5 20. Qf4+ Ka7 21. Rhe1 d4 22. Nd5 Nbxd5 23. exd5 Qd6 24. Rxd4 cxd4 25. Re7+ Kb6 26. Qxd4+ Kxa5 27. b4+ Ka4 28. Qc3 Qxd5 29. Ra7 Bb7 30. Rxb7 Qc4 31. Qxf6 Kxa3 32. Qxa6+ Kxb4 33. c3+ Kxc3 34. Qa1+ Kd2 5. Qb2+ Kd1 36. Bf1 Rd2 37. Rd7 Rxd7 38. Bxc4 bxc4 39. Qxh8 Rd3 40. Qa8 c3 41. Qa4+ Ke1 42. f4 f5 43. Kc1 Rd2 44. Qa7 1-0";

		Move[] moves = Move.parse(game);
		assertEquals(44, moves.length);

		assertEquals(Type.BISHOP, moves[7].black.type);
		assertEquals("h6", moves[7].black.to);
		assertTrue(moves[7].black.capture);

		assertTrue(moves[13].black.capture);
		assertEquals("d4", moves[13].black.to);

		assertEquals(Type.QUEEN, moves[43].white.type);
		assertEquals("a7", moves[43].white.to);
	}

	@Test
	void topalovVsWang2022() {
		var game = "1. e4 c5 2. Nf3 Nc6 3. d4 cxd4 4. Nxd4 Nf6 5. Nc3 e5 6. Nde2 Bc5 7. Ng3 d6\n"
				+ "8. Na4 Bb4+ 9. c3 Ba5 10. b4 Bc7 11. Be2 O-O 12. O-O a6 13. Be3 h6 14. h3\n"
				+ "Be6 15. Qc1 b5 16. Nb2 Bb6 17. a4 Bxe3 18. Qxe3 Rb8 19. axb5 axb5 20. f4\n"
				+ "Qb6 21. Qxb6 Rxb6 22. f5 Bc8 23. Rfd1 Rd8 24. Rd2 Bb7 25. Nd1 Ne7 26. Bf3\n"
				+ "Rc8 27. Ra3 d5 28. exd5 Nexd5 29. Ra7 Bc6 30. Nh5 Rbb8 31. Nxf6+ Nxf6 32.\n"
				+ "Bxc6 Rxc6 33. Re7 e4 34. Re5 h5 35. Kf2 h4 36. Ke3 Rc4 37. Rd6 Rc7 38. Rc5\n"
				+ "Ra7 39. Rd2 Ra1 40. Nf2 Re1+ 41. Re2 Rg1 42. Nxe4 Nxe4 43. Kxe4 Re8+ 44.\n"
				+ "Re5 Rc8 45. Kd3 Rd8+ 46. Kc2 Ra8 47. Kb2 Rb8 48. Kb3 f6 49. Rd5 Rb1+ 50.\n"
				+ "Kc2 Rg1 51. Kd3 Rf1 52. Rd2 Rf4 53. Re2 Kh7 54. Re4 Rxe4 55. Kxe4 Kh6 56.\n"
				+ "Kf4 Kh5 57. Rd7 Rc8 58. Rxg7 Rc4+ 59. Kf3 Rxc3+ 60. Ke4 Rc2 61. Kd5 Re2 62.\n"
				+ "Kc6 Re5 63. Rd7 Kg5 64. Rb7 Re2 65. Kxb5 Kxf5 66. Rg7 Ke6 67. Kb6 f5 68. b5\n"
				+ "Kf6 69. Rg8 Kf7 70. Rh8 Rxg2 71. Rxh4 Kf6 72. Rc4 Ke5 73. Kc6 Rg6+ 74. Kc5\n"
				+ "f4 75. b6 f3 76. b7 Rg8 77. Kb6 f2 78. Rc1 Ke4 79. Rf1 Kf3 80. Ka7 Rg1 81.\n"
				+ "Rxf2+ Kxf2 82. b8=Q 1/2-1/2";

		Move[] moves = Move.parse(game);
		assertEquals(82, moves.length);
		assertEquals(Type.QUEEN, moves[81].white.promotedTo);
	}

	@Test
	void vachierVsMamedyarov() {
		var game = "1. d4 Nf6 2. c4 e6 3. Nf3 d5 4. Nc3 c5 5. cxd5 cxd4 6. Qxd4 exd5 7. Bg5 Be7\n"
				+ "8. e3 O-O 9. Qa4 h6 10. Bh4 Nc6 11. Bb5 Ne4 12. Bxe7 Nxe7 13. Rc1 Qb6 14.\n"
				+ "O-O a6 15. Nxe4 dxe4 16. Qxe4 Qxb5 17. Qxe7 Qxb2 18. a3 Bf5 19. Rc7 b5 20.\n"
				+ "Qb4 Qxb4 21. axb4 Rfc8 22. Rfc1 Rxc7 23. Rxc7 a5 24. bxa5 Rxa5 25. h3 b4\n"
				+ "26. Nd4 Be4 27. f3 Bd5 28. e4 Ra1+ 29. Kh2 Rd1 30. exd5 Rxd4 31. Rb7 Rxd5 32. Rxb4 1/2-1/2";

		Move[] moves = Move.parse(game);
		assertEquals(32, moves.length);
		assertEquals(Castle.KING_SIDE, moves[13].white.castle);
		assertEquals(Type.ROOK, moves[31].white.type);
	}

	@Test
	void soVsRadjabov() {
		var game = "1. e4 e5 2. Nf3 Nc6 3. Bc4 Bc5 4. O-O Nf6 5. d3 d6 6. c3 a5 7. Re1 O-O 8.\n"
				+ "h3 h6 9. Nbd2 Be6 10. Bb5 Nd7 11. Nf1 d5 12. Be3 dxe4 13. dxe4 Qe7 14. Qe2\n"
				+ "Rfd8 15. Red1 a4 16. Qc2 a3 17. b4 Bd6 18. N1d2 Nb6 19. Bxb6 cxb6 20. Bc4\n"
				+ "Rdc8 21. Qd3 Nd8 22. Bxe6 Qxe6 23. Nf1 Bf8 24. Ne3 f6 25. Nd5 Nf7 26. Nd2\n"
				+ "Ra6 27. Rab1 b5 28. Qxb5 Nd6 29. Qd3 b5 30. Rbc1 Rac6 31. Rc2 f5 32. exf5\n"
				+ "Nxf5 33. Nb3 Rc4 34. Na5 e4 35. Re2 Re8 36. Nxc4 exd3 37. Rxe6 Rxe6 38.\n" + "Nxa3 1-0";

		Move[] moves = Move.parse(game);
		assertEquals(38, moves.length);
		assertEquals(Type.KNIGHT, moves[37].white.type);
	}

}
