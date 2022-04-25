package chess.domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.position.Position;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PawnTest {

    @ParameterizedTest
    @MethodSource("pawnMovement")
    @DisplayName("폰은 수직으로 한 칸 이동한다")
    void move_verticalOneStep(Position source, Position target, boolean result) {
        Type pawn = new Pawn();
        assertThat(pawn.isMovable(source, target)).isEqualTo(result);
    }

    private static Stream<Arguments> pawnMovement() {
        return Stream.of(
                Arguments.of(Position.of("a1"), Position.of("a2"), true),
                Arguments.of(Position.of("a1"), Position.of("b1"), false),
                Arguments.of(Position.of("e5"), Position.of("e4"), true)
        );
    }

    @ParameterizedTest
    @MethodSource("pawnFirstMovement")
    @DisplayName("폰은 처음에만 직진으로 2칸 움직일 수 있다")
    void move_first_allowVerticalTwoStep(Position source, Position target, boolean result) {
        Type pawn = new Pawn();
        assertThat(pawn.isMovable(source, target)).isEqualTo(result);
    }

    private static Stream<Arguments> pawnFirstMovement() {
        return Stream.of(
                Arguments.of(Position.of("a2"), Position.of("a4"), true),
                Arguments.of(Position.of("a4"), Position.of("a6"), false),
                Arguments.of(Position.of("b7"), Position.of("b5"), true),
                Arguments.of(Position.of("b5"), Position.of("b3"), false)
        );
    }

    @ParameterizedTest
    @MethodSource("pawnDiagonalMovement")
    @DisplayName("폰은 대각으로 1칸 움직일 수 있다")
    void move_diagonalOneStep(Position source, Position target, boolean result) {
        Type pawn = new Pawn();
        assertThat(pawn.isMovable(source, target)).isEqualTo(result);
    }

    private static Stream<Arguments> pawnDiagonalMovement() {
        return Stream.of(
                Arguments.of(Position.of("a1"), Position.of("b2"), true),
                Arguments.of(Position.of("a1"), Position.of("c3"), false),
                Arguments.of(Position.of("e5"), Position.of("d4"), true),
                Arguments.of(Position.of("e5"), Position.of("c3"), false)
        );
    }
}
