package chess.domain.game;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import chess.domain.Color;
import chess.domain.board.BoardFixtures;
import chess.domain.board.Point;
import chess.domain.board.Route;
import chess.domain.piece.King;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;

class RunningTest {

    @Test
    @DisplayName("게임 시작시 에러가 발생한다.")
    void startTest() {
        GameState gameState = new Running(BoardFixtures.INITIAL, Color.WHITE);

        assertThatThrownBy(gameState::start)
            .isInstanceOf(UnsupportedOperationException.class)
            .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("게임 종료시 종료 상태로 변한다.")
    void finishTest() {
        GameState gameState = new Running(BoardFixtures.INITIAL, Color.WHITE);

        assertThat(gameState.finish()).isInstanceOf(Finished.class);
    }

    @Test
    @DisplayName("move 명령시 running 상태로 변한다.")
    void moveToRunningTest() {
        Route route = Route.of(List.of("a2", "a3"));

        GameState gameState = new Running(BoardFixtures.INITIAL, Color.WHITE);

        GameState movedState = gameState.move(route);

        assertThat(movedState).isInstanceOf(Running.class);
    }

    @Test
    @DisplayName("move 명령시 king이 죽으면 종료 상태로 변한다.")
    void moveToFinishTest() {
        Route route = Route.of(List.of("e1", "e8"));

        GameState gameState = new Running(BoardFixtures.create(Map.of(
            Point.of("e8"), new King(Color.BLACK),
            Point.of("e1"), new Queen(Color.WHITE)
        )), Color.WHITE);

        GameState movedState = gameState.move(route);

        assertThat(movedState).isInstanceOf(Finished.class);
    }

    @Test
    @DisplayName("진행상태는 실행가능한 상태이다.")
    void runningIsRunnable() {
        GameState state = new Running(BoardFixtures.EMPTY, Color.WHITE);

        boolean isRunnable = state.isRunnable();

        assertThat(isRunnable).isTrue();
    }

    @Test
    @DisplayName("진행상태에서는 보드의 정보를 얻을 수 있다.")
    void gettingResponse() {
        GameState state = new Running(BoardFixtures.EMPTY, Color.WHITE);

        Map<Point, Piece> pointPieces = state.getPointPieces();

        assertThat(pointPieces).hasSize(64);

    }
}
