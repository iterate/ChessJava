package fantasy;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fantasy.pieces.Bishop;
import fantasy.pieces.Color;
import fantasy.pieces.Knight;
import fantasy.pieces.Pawn;
import fantasy.pieces.Piece;
import fantasy.pieces.Rook;
import fantasy.pieces.Type;

public class Board {

	public Game game;

	final Set<Piece> pieces = new LinkedHashSet<>();
	final Set<Bet> bets = new LinkedHashSet<>();

	public boolean play(Move[] moves) {
		int i = 0;
		for (Move move : moves) {
			i++;

			try {
				play(move.white, Color.WHITE);
				if (move.black != null)
					play(move.black, Color.BLACK);
			} catch (ChessException e) {
				System.err.println("Error on game: " + game);
				System.err.println("\tMove " + i + ": " + e.getMessage());
				return false;
			}
		}
		score();
		return true;
	}

	public void score() {
		for (Bet bet : bets) {
			Piece p = bet.piece;
			bet.score = p.moves;
		}
	}

	// for testing and debugging only
	public boolean playAndPrintMoves(Move[] moves) {
		for (int i = 0; i < moves.length; i++) {
			Move move = moves[i];
			play(move.white, Color.WHITE);
			if (move.black != null)
				play(move.black, Color.BLACK);
			System.out.println("\nMove " + (i + 1) + ": " + move);
			System.out.println("-".repeat(100));
			System.out.println(this);
		}
		return true;
	}

	public void play(HalfMove halfMove, Color color) {

		if (halfMove.castle != null) {
			castle(halfMove, color);
			return;
		} else {
			Set<Piece> ofSameTypeAndColor = find(halfMove.type, color, halfMove.fromFile, halfMove.fromRank);

			for (Piece piece : ofSameTypeAndColor) {
				boolean legalMove;
				try {
					legalMove = piece.isLegalMove(halfMove);
				} catch (Exception e) {
					throw new RuntimeException("Error parsing " + halfMove + ". " + game, e);
				}

				if (legalMove) {

					if (piece.type == Type.ROOK || piece.type == Type.PAWN) {
						if (pathBlocked(piece.position, Position.parse(halfMove.to), color))
							continue;
					}

					if (halfMove.capture)
						makeCapture(piece, halfMove);

					piece.moveTo(halfMove.to);
					return;
				}
			}
		}
	}

	boolean pathBlocked(Position from, Position to, Color color) {

		if (from.y == to.y) {
			int fromX = Math.min(from.x, to.x);
			int toX = Math.max(from.x, to.x);

			for (int x = fromX; x < toX; x++) {
				var p = new Position(x, from.y);
				if (p.equals(from))
					continue;

				if (!isEmpty(p) && color.equals(pieceAt(p).color)) {
					return true;
				}
			}

		} else if (from.x == to.x) {

			int fromY = Math.min(from.y, to.y);
			int toY = Math.max(from.y, to.y);

			for (int y = fromY; y < toY; y++) {
				var p = new Position(from.x, y);
				if (p.equals(from))
					continue;

				if (!isEmpty(p) && color.equals(pieceAt(p).color)) {
					return true;
				}
			}
		}
		return false;
	}

	void makeCapture(Piece piece, HalfMove halfMove) {
		Piece victim = null;
		if (piece instanceof Pawn) {
			if (isEmpty(halfMove.to)) {
				Pawn pawn = (Pawn) piece;
				if (pawn.isEnPassantReady()) {
					Position toPosition = Position.parse(halfMove.to);

					if (piece.position.adjacentFiles(toPosition)) {
						Position victimPosition = new Position(toPosition.x, piece.position.y);
						victim = findPieceAtSquare(victimPosition.toNotation());
					}
				}
			} else {
				victim = pieceAt(halfMove.to);
			}

		} else {
			victim = pieceAt(halfMove.to);
		}
		if (victim == null) {
			System.err.println(this);
			throw new ChessException("Attempt to capture empty square: " + halfMove);
		}
		if (victim.color == piece.color)
			throw new ChessException("Attempt to capture piece of same color: " + halfMove + " capturing " + victim);
		victim.captured();
		piece.captures++;
	}

	Set<Piece> find(Type type, Color color, Character fromFile, Integer fromRank) {
		Set<Piece> result = new LinkedHashSet<>();
		for (Piece each : pieces) {
			if (each.isCaptured())
				continue;

			if (fromFile != null) {
				if (each.type.equals(type) && each.color.equals(color)
						&& Position.fileLabel(each.position.x) == fromFile)
					result.add(each);

			} else if (fromRank != null) {
				if (each.type.equals(type) && each.color.equals(color) && each.position.y == fromRank)
					result.add(each);
			}

			else {
				if (each.type.equals(type) && each.color.equals(color)) {
					result.add(each);
				}
			}
		}
		return result;
	}

	void castle(HalfMove halfMove, Color color) {
		if (Castle.KING_SIDE.equals(halfMove.castle)) {
			if (Color.WHITE.equals(color)) {
				pieceAt("e1").moveTo("g1");
				pieceAt("h1").moveTo("f1");
			} else if (Color.BLACK.equals(color)) {
				pieceAt("e8").moveTo("g8");
				pieceAt("h8").moveTo("f8");
			}

		} else if (Castle.QUEEN_SIDE.equals(halfMove.castle)) {
			if (Color.WHITE.equals(color)) {
				pieceAt("e1").moveTo("c1");
				pieceAt("a1").moveTo("d1");
			} else if (Color.BLACK.equals(color)) {
				pieceAt("e8").moveTo("c8");
				pieceAt("a8").moveTo("d8");
			}
		}
	}

	public Piece pieceAt(Position p) {
		Piece result = findPieceAtSquare(p);
		if (result == null)
			throw new ChessException("No piece at " + p.toNotation());
		return result;

	}

	public Piece pieceAt(String square) {
		return pieceAt(Position.parse(square));
	}

	public boolean isEmpty(String square) {
		return findPieceAtSquare(square) == null;
	}

	public boolean isEmpty(Position position) {
		return findPieceAtSquare(position) == null;
	}

	public Piece findPieceAtSquare(String square) {
		return findPieceAtSquare(Position.parse(square));
	}

	public Piece findPieceAtSquare(Position p) {
		for (Piece each : pieces) {
			if (each.isCaptured())
				continue;

			if (each.position.equals(p))
				return each;
		}
		return null;
	}

	public void add(Piece piece) {
		pieces.add(piece);
	}

	public static Board newGame() {
		Board board = new Board();

		board.add(new Rook(Color.WHITE, "a1"));
		board.add(new Knight(Color.WHITE, "b1"));
		board.add(new Bishop(Color.WHITE, "c1"));
		board.add(new Piece(Type.QUEEN, Color.WHITE, "d1"));
		board.add(new Piece(Type.KING, Color.WHITE, "e1"));
		board.add(new Bishop(Color.WHITE, "f1"));
		board.add(new Knight(Color.WHITE, "g1"));
		board.add(new Rook(Color.WHITE, "h1"));

		for (int i = 1; i <= 8; i++)
			board.add(new Pawn(Color.WHITE, Position.fileLabel(i) + "2"));

		board.add(new Rook(Color.BLACK, "a8"));
		board.add(new Knight(Color.BLACK, "b8"));
		board.add(new Bishop(Color.BLACK, "c8"));
		board.add(new Piece(Type.QUEEN, Color.BLACK, "d8"));
		board.add(new Piece(Type.KING, Color.BLACK, "e8"));
		board.add(new Bishop(Color.BLACK, "f8"));
		board.add(new Knight(Color.BLACK, "g8"));
		board.add(new Rook(Color.BLACK, "h8"));

		for (int i = 1; i <= 8; i++)
			board.add(new Pawn(Color.BLACK, Position.fileLabel(i) + "7"));

		return board;
	}

	public Set<Piece> pieces() {
		return pieces;
	}

	public String toString() {

		String[][] s = new String[8][8];

		for (int x = 0; x < 8; x++)
			for (int y = 0; y < 8; y++)
				s[x][y] = "-- ";

		for (Piece p : pieces) {
			if (!p.isCaptured())
				s[p.position.y - 1][p.position.x - 1] = Character.toLowerCase(p.color.name().charAt(0))
						+ p.abbreviation() + " ";
		}

		StringBuffer result = new StringBuffer("    ");

		for (int x = 8; x >= 1; x--) {
			result.append(Position.fileLabel(x) + "  ");
		}
		result.append("\n\n");
		for (int x = 0; x < 8; x++) {
			result.append((x + 1) + ":  ");
			for (int y = 7; y >= 0; y--) {
				result.append(s[x][y]);
			}
			result.append("\n\n");
		}
		return result.toString();
	}

	public List<Piece> pieces(Color color) {
		return pieces.stream().filter(piece -> piece.color == color).sorted().collect(Collectors.toList());
	}

	public void populate(Set<Bet> bets) {
		for (Bet bet : bets) {
			// TODO: Only replace pieces of bet.player
			pieces.remove(pieceAt(bet.piece.originalPosition.toNotation()));
			pieces.add(bet.piece);
			bet.piece.reset();
		}
		this.bets.clear();
		this.bets.addAll(bets);
	}

}
