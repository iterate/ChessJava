package fantasy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fantasy.pieces.Color;
import fantasy.pieces.Knight;
import fantasy.pieces.Type;

class PieceTest {

	@Test
	void isLegalPosition() {
		Knight knight = new Knight(Color.WHITE, "c3");

		assertTrue(knight.isLegalMove(new HalfMove(Type.KNIGHT, "b1")));
		assertTrue(knight.isLegalMove(new HalfMove(Type.KNIGHT, "d1")));
		assertTrue(knight.isLegalMove(new HalfMove(Type.KNIGHT, "d5")));
		assertTrue(knight.isLegalMove(new HalfMove(Type.KNIGHT, "b5")));

		assertTrue(knight.isLegalMove(new HalfMove(Type.KNIGHT, "e4")));
		assertTrue(knight.isLegalMove(new HalfMove(Type.KNIGHT, "e2")));
		assertTrue(knight.isLegalMove(new HalfMove(Type.KNIGHT, "a2")));
		assertTrue(knight.isLegalMove(new HalfMove(Type.KNIGHT, "a4")));

		assertFalse(knight.isLegalMove(new HalfMove(Type.KNIGHT, "c4")));
		assertFalse(knight.isLegalMove(new HalfMove(Type.KNIGHT, "h8")));
		assertFalse(knight.isLegalMove(new HalfMove(Type.KNIGHT, "d3")));

		Knight k2 = new Knight(Color.WHITE, "f3");
		assertFalse(k2.isLegalMove(new HalfMove(Type.KNIGHT, "c3")));
	}

}
