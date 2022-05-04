package chess.domain.piece.unit;

import static chess.domain.PositionFixtures.A1;
import static chess.domain.PositionFixtures.A3;
import static chess.domain.PositionFixtures.B2;
import static chess.domain.PositionFixtures.C1;
import static chess.domain.PositionFixtures.H8;
import static chess.domain.piece.property.Team.WHITE;

import chess.domain.position.Position;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class BishopTest {

    @ParameterizedTest
    @MethodSource("availablePositions")
    @DisplayName("Bishop 은 대각선으로 이동할 수 있다.")
    void moveBishop(Position target) {
        Piece piece = new Bishop(WHITE);

        Assertions.assertThat(piece.availableMove(B2, target, true)).isEqualTo(true);
    }

    private static Stream<Position> availablePositions() {
        return Stream.of(A1, A3, C1, H8);
    }
}
