package fantasy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PositionTest {

	@Test
	void test() {

		Position a1 = Position.parse("a1");
		assertEquals(1, a1.x);
		assertEquals(1, a1.y);

		Position h8 = Position.parse("h8");
		assertEquals(8, h8.x);
		assertEquals(8, h8.y);

		Position d4 = Position.parse("d4");
		assertEquals(4, d4.x);
		assertEquals(4, d4.y);

		Position h1 = Position.parse("h1");
		assertEquals(8, h1.x);
		assertEquals(1, h1.y);

	}

	@Test
	void adjacentFiles() {
		assertTrue(Position.parse("a1").adjacentFiles(Position.parse("b2")));
		assertTrue(Position.parse("d1").adjacentFiles(Position.parse("e8")));
		assertFalse(Position.parse("d1").adjacentFiles(Position.parse("h1")));
	}

}
