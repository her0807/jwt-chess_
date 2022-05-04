package chess.domain;

import static chess.domain.piece.property.Team.BLACK;
import static chess.domain.piece.property.Team.WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import chess.domain.board.ChessBoardGenerator;
import chess.domain.piece.unit.Bishop;
import chess.domain.piece.unit.King;
import chess.domain.piece.unit.Knight;
import chess.domain.piece.unit.Pawn;
import chess.domain.piece.unit.Piece;
import chess.domain.piece.unit.Queen;
import chess.domain.piece.unit.Rook;
import chess.domain.position.Position;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class ChessBoardGeneratorTest {

    @Test
    @DisplayName("체스보드의 사이즈는 64이다.")
    void checkChessBoardSize() {
        ChessBoardGenerator chessBoardGenerator = new ChessBoardGenerator();
        assertThat(chessBoardGenerator.generate().size()).isEqualTo(64);
    }

    @ParameterizedTest
    @MethodSource("whitePawns")
    @DisplayName("흰색 플레이어의 초기 Pawn의 row 위치는 2이다")
    void checkPositionWhitePawn(Position position) {
        ChessBoardGenerator chessBoardGenerator = new ChessBoardGenerator();
        Piece piece = chessBoardGenerator.generate().get(position);

        assertAll(
                () -> assertEquals(piece.checkSameTeam(WHITE), true),
                () -> assertThat(piece).isInstanceOf(Pawn.class)
        );
    }

    private static Stream<Position> whitePawns() {
        return Stream.of(PositionFixtures.A2, PositionFixtures.B2, PositionFixtures.C2, PositionFixtures.D2, PositionFixtures.E2, PositionFixtures.F2, PositionFixtures.G2, PositionFixtures.H2);
    }

    @ParameterizedTest
    @MethodSource("columns")
    @DisplayName("검은색 플레이어의 초기 Pawn의 row 위치는 7이다")
    void checkPositionBlackPawn(Position position) {
        ChessBoardGenerator chessBoardGenerator = new ChessBoardGenerator();
        Piece piece = chessBoardGenerator.generate().get(position);

        assertAll(
                () -> assertEquals(piece.checkSameTeam(BLACK), true),
                () -> assertThat(piece).isInstanceOf(Pawn.class)
        );
    }

    private static Stream<Position> columns() {
        return Stream.of(PositionFixtures.A7, PositionFixtures.B7, PositionFixtures.C7, PositionFixtures.D7, PositionFixtures.E7, PositionFixtures.F7, PositionFixtures.G7, PositionFixtures.H7);
    }

    @Test
    @DisplayName("흰색 플레이어의 초기 Rook 위치는 A1, H1이다.")
    void checkPositionWhiteRook() {
        ChessBoardGenerator chessBoardGenerator = new ChessBoardGenerator();
        Piece leftRook = chessBoardGenerator.generate().get(PositionFixtures.A1);
        Piece rightRook = chessBoardGenerator.generate().get(PositionFixtures.H1);

        assertAll(
                () -> assertEquals(leftRook.checkSameTeam(WHITE), true),
                () -> assertEquals(rightRook.checkSameTeam(WHITE), true),
                () -> assertThat(leftRook).isInstanceOf(Rook.class),
                () -> assertThat(rightRook).isInstanceOf(Rook.class)
        );
    }

    @Test
    @DisplayName("검은색 플레이어의 초기 Rook 위치는 A8, H8이다.")
    void checkPositionBlackRook() {
        ChessBoardGenerator chessBoardGenerator = new ChessBoardGenerator();
        Piece leftRook = chessBoardGenerator.generate().get(PositionFixtures.A8);
        Piece rightRook = chessBoardGenerator.generate().get(PositionFixtures.H8);

        assertAll(
                () -> assertEquals(leftRook.checkSameTeam(BLACK), true),
                () -> assertEquals(rightRook.checkSameTeam(BLACK), true),
                () -> assertThat(leftRook).isInstanceOf(Rook.class),
                () -> assertThat(rightRook).isInstanceOf(Rook.class)
        );
    }

    @Test
    @DisplayName("흰색 플레이어의 초기 Knight 위치는 B1, G1이다.")
    void checkPositionWhiteKnight() {
        ChessBoardGenerator chessBoardGenerator = new ChessBoardGenerator();
        Piece leftKnight = chessBoardGenerator.generate().get(PositionFixtures.B1);
        Piece rightKnight = chessBoardGenerator.generate().get(PositionFixtures.G1);

        assertAll(
                () -> assertEquals(leftKnight.checkSameTeam(WHITE), true),
                () -> assertEquals(rightKnight.checkSameTeam(WHITE), true),
                () -> assertThat(leftKnight).isInstanceOf(Knight.class),
                () -> assertThat(rightKnight).isInstanceOf(Knight.class)
        );
    }

    @Test
    @DisplayName("검은색 플레이어의 초기 Knight 위치는 B8, G8이다.")
    void checkPositionBlackKnight() {
        ChessBoardGenerator chessBoardGenerator = new ChessBoardGenerator();
        Piece leftKnight = chessBoardGenerator.generate().get(PositionFixtures.B8);
        Piece rightKnight = chessBoardGenerator.generate().get(PositionFixtures.G8);

        assertAll(
                () -> assertEquals(leftKnight.checkSameTeam(BLACK), true),
                () -> assertEquals(rightKnight.checkSameTeam(BLACK), true),
                () -> assertThat(leftKnight).isInstanceOf(Knight.class),
                () -> assertThat(rightKnight).isInstanceOf(Knight.class)
        );
    }

    @Test
    @DisplayName("흰색 플레이어의 초기 Bishop 위치는 C1, F1이다.")
    void checkPositionWhiteBishop() {
        ChessBoardGenerator chessBoardGenerator = new ChessBoardGenerator();
        Piece leftBishop = chessBoardGenerator.generate().get(PositionFixtures.C1);
        Piece rightBishop = chessBoardGenerator.generate().get(PositionFixtures.F1);

        assertAll(
                () -> assertEquals(leftBishop.checkSameTeam(WHITE), true),
                () -> assertEquals(rightBishop.checkSameTeam(WHITE), true),
                () -> assertThat(leftBishop).isInstanceOf(Bishop.class),
                () -> assertThat(rightBishop).isInstanceOf(Bishop.class)
        );
    }

    @Test
    @DisplayName("검은색 플레이어의 초기 Bishop 위치는 C8, F8이다.")
    void checkPositionBlackBishop() {
        ChessBoardGenerator chessBoardGenerator = new ChessBoardGenerator();
        Piece leftBishop = chessBoardGenerator.generate().get(PositionFixtures.C8);
        Piece rightBishop = chessBoardGenerator.generate().get(PositionFixtures.F8);

        assertAll(
                () -> assertEquals(leftBishop.checkSameTeam(BLACK), true),
                () -> assertEquals(rightBishop.checkSameTeam(BLACK), true),
                () -> assertThat(leftBishop).isInstanceOf(Bishop.class),
                () -> assertThat(rightBishop).isInstanceOf(Bishop.class)
        );
    }

    @Test
    @DisplayName("흰색 플레이어의 초기 Queen 위치는 D1이다.")
    void checkPositionWhiteQueen() {
        ChessBoardGenerator chessBoardGenerator = new ChessBoardGenerator();
        Piece piece = chessBoardGenerator.generate().get(PositionFixtures.D1);

        assertAll(
                () -> assertEquals(piece.checkSameTeam(WHITE), true),
                () -> assertThat(piece).isInstanceOf(Queen.class)
        );
    }

    @Test
    @DisplayName("검은색 플레이어의 초기 Queen 위치는 D8이다.")
    void checkPositionBlackQueen() {
        ChessBoardGenerator chessBoardGenerator = new ChessBoardGenerator();
        Piece piece = chessBoardGenerator.generate().get(PositionFixtures.D8);

        assertAll(
                () -> assertEquals(piece.checkSameTeam(BLACK), true),
                () -> assertThat(piece).isInstanceOf(Queen.class)
        );
    }

    @Test
    @DisplayName("흰색 플레이어의 초기 King 위치는 E1이다.")
    void checkPositionWhiteKing() {
        ChessBoardGenerator chessBoardGenerator = new ChessBoardGenerator();
        Piece piece = chessBoardGenerator.generate().get(PositionFixtures.E1);

        assertAll(
                () -> assertEquals(piece.checkSameTeam(WHITE), true),
                () -> assertThat(piece).isInstanceOf(King.class)
        );
    }

    @Test
    @DisplayName("검은색 플레이어의 초기 King 위치는 E8이다.")
    void checkPositionBlackKing() {
        ChessBoardGenerator chessBoardGenerator = new ChessBoardGenerator();
        Piece piece = chessBoardGenerator.generate().get(PositionFixtures.E8);

        assertAll(
                () -> assertEquals(piece.checkSameTeam(BLACK), true),
                () -> assertThat(piece).isInstanceOf(King.class)
        );
    }
}
