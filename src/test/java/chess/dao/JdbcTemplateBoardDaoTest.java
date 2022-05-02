package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.board.Board;
import chess.domain.board.strategy.WebBasicBoardStrategy;
import chess.domain.piece.Blank;
import chess.domain.piece.WhitePawn;
import chess.domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class JdbcTemplateBoardDaoTest {

    private JdbcTemplateBoardDao jdbcTemplateBoardDao;
    private int id;

    @Autowired
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @BeforeEach
    void setUp() {
        JdbcTemplateGameDao jdbcTemplateGameDao = new JdbcTemplateGameDao(jdbcTemplate, new GameMapper());
        id = jdbcTemplateGameDao.create("asdf", "1234");
        jdbcTemplateBoardDao = new JdbcTemplateBoardDao(jdbcTemplate, new BoardMapper());
        Board board = new Board();
        board.initBoard(new WebBasicBoardStrategy());
        jdbcTemplateBoardDao.init(board.toMap(), id);
    }

    @Test
    @DisplayName("기본 보드를 가져온다.")
    void getBoard() {
        Board board = new Board();
        board.initBoard(new WebBasicBoardStrategy());
        assertThat(jdbcTemplateBoardDao.getBoard(id)).isEqualTo(board.toMap());
    }

    @Test
    @DisplayName("이동 업데이트 로직을 확인한다.")
    void update() {
        jdbcTemplateBoardDao.update("a3", "white_pawn", id);
        jdbcTemplateBoardDao.update("a2", "blank", id);

        Board board = new Board();
        board.initBoard(new WebBasicBoardStrategy());
        board.move(new Position("a3"), new WhitePawn());
        board.move(new Position("a2"), new Blank());

        assertThat(jdbcTemplateBoardDao.getBoard(id)).isEqualTo(board.toMap());
    }

    @Test
    @DisplayName("리셋을 확인한다.")
    void reset() {
        jdbcTemplateBoardDao.update("a3", "white_pawn", id);
        jdbcTemplateBoardDao.update("a2", "blank", id);

        Board board = new Board();
        jdbcTemplateBoardDao.reset(board.toMap(), id);

        assertThat(jdbcTemplateBoardDao.getBoard(id)).isEqualTo(board.toMap());
    }
}
