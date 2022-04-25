package chess.domain.game;

import chess.domain.pieces.Color;
import chess.domain.pieces.Piece;
import chess.domain.position.Position;
import chess.machine.Result;

import java.util.Map;
import java.util.Optional;

public final class Game {

    private static final Color FIRST_TURN = Color.WHITE;

    private final ConsoleBoard board;
    private Color turn = FIRST_TURN;

    public Game(final Initializer initializer) {
        board = new ConsoleBoard(initializer);
    }

    public Optional<Piece> piece(final Position position) {
        return board.piece(position);
    }

    public void move(final String source, final String target) {
        final Position sourcePosition = Position.of(source);
        final Position targetPosition = Position.of(target);
        validateNotEquals(sourcePosition, targetPosition);
        validateCorrectTurn(sourcePosition);
        board.move(sourcePosition, targetPosition);
        changeTurn();
    }

    private void validateNotEquals(final Position sourcePosition, final Position targetPosition) {
        if (sourcePosition.equals(targetPosition)) {
            throw new IllegalArgumentException("출발지와 목적지가 동일할 수 없습니다.");
        }
    }

    private void validateCorrectTurn(final Position sourcePosition) {
        final Optional<Piece> wrappedPiece = board.piece(sourcePosition);
        if (wrappedPiece.isPresent() && !wrappedPiece.get().isSameColor(turn)) {
            throw new IllegalArgumentException("지금은 " + turn.value() + "의 턴입니다.");
        }
    }

    private void changeTurn() {
        turn = Color.opposite(turn);
    }

    public boolean isEnd() {
        return board.isEnd();
    }

    public double calculateScore(final Color color) {
        return board.calculateScore(color);
    }

    public Map<Result, Color> calculateScoreWinner() {
        return board.calculateScoreWinner();
    }

    public Map<Result, Color> calculateFinalWinner() {
        return board.calculateFinalWinner();
    }
}
