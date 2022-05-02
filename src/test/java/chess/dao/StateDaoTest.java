package chess.dao;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import chess.model.board.Board;
import chess.model.state.State;
import chess.model.state.running.BlackTurn;
import chess.model.state.running.WhiteTurn;

@JdbcTest
@Sql({"/db/schema.sql"})
class StateDaoTest {

    private StateDao stateDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        stateDao = new StateDao(jdbcTemplate);
    }

    @DisplayName("데이터를 삽입한다.")
    @Test
    void insert() {
        State state = new BlackTurn(Board.init());
        stateDao.delete(1L);
        stateDao.insert(1L, state);

        assertThat(stateDao.find(1L, Board.init()).isBlackTurn()).isTrue();
    }

    @DisplayName("데이터를 삭제한다.")
    @Test
    void delete() {
        assertThat(stateDao.delete(1L)).isEqualTo(1);
    }

    @DisplayName("데이터를 업데이트한다.")
    @Test
    void update() {
        State nowState = new BlackTurn(Board.init());
        State nextState = new WhiteTurn(Board.init());
        stateDao.update(1L, nowState, nextState);

        assertThat(stateDao.find(1L, Board.init()).isWhiteTurn()).isTrue();
    }
}
