package chess.domain.piece;

import static chess.domain.piece.Team.WHITE;
import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.board.position.Position;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RookTest {

    @ParameterizedTest
    @DisplayName("이동 경로에 이동할 수 있는지 반환한다.")
    @MethodSource("provideTargetPositionAndOtherPositions")
    void canMove(final Position targetPosition, final List<Position> otherPositions, final boolean expected) {
        //given
        final Position sourcePosition = Position.from("a1");
        final Piece piece = new Rook(WHITE);
        //when
        final boolean actual = piece.canMove(sourcePosition, targetPosition, otherPositions);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> provideTargetPositionAndOtherPositions() {
        return Stream.of(
                Arguments.of(Position.from("a4"), Collections.emptyList(), true),
                Arguments.of(Position.from("d1"), Collections.emptyList(), true),
                Arguments.of(Position.from("c3"), Collections.emptyList(), false),
                Arguments.of(Position.from("a4"), Collections.singletonList(Position.from("a3")), false)
        );
    }
}