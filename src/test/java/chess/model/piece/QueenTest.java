package chess.model.piece;

import static chess.model.Team.*;
import static chess.model.position.File.*;
import static chess.model.position.Rank.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import chess.model.board.Board;
import chess.model.position.File;
import chess.model.position.Position;
import chess.model.position.Rank;

class QueenTest {

    @DisplayName("target 위치로 움직일 수 없으면 false를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"SEVEN,D", "ONE,D", "SEVEN,G", "SEVEN,A", "ONE,A", "ONE,G"})
    void canMove_false(Rank rank, File file) {
        Map<Position, Piece> board = Board.init().getBoard();
        Piece queen = new Queen(BLACK);
        boolean actual = queen.canMove(Position.of(FOUR, D), Position.of(rank, file), board);

        assertThat(actual).isFalse();
    }

    @DisplayName("target 위치로 움직일 수 있으면 true를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"SIX,F", "SIX,B", "TWO,B", "TWO,B", "FOUR,H", "FOUR,A", "TWO,D", "SIX,D"})
    void canMove_true(Rank rank, File file) {
        Map<Position, Piece> board = Board.init().getBoard();
        Piece queen = new Queen(BLACK);
        boolean actual = queen.canMove(Position.of(FOUR, D), Position.of(rank, file), board);

        assertThat(actual).isTrue();
    }
}
