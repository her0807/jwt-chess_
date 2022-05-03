package chess.dao.jdbctemplate;

import chess.domain.GameStatus;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class JdbcTemplateGameStatusDaoTest {

    private JdbcTemplateGameStatusDao jdbcTemplateGameStatusDao;
    private JdbcTemplateRoomDao jdbcTemplateRoomDao;

    private int id;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        jdbcTemplateGameStatusDao = new JdbcTemplateGameStatusDao(jdbcTemplate);
        jdbcTemplateRoomDao = new JdbcTemplateRoomDao(dataSource);

        id = jdbcTemplateRoomDao.create("room1", "1111");
        jdbcTemplateGameStatusDao.reset(GameStatus.READY, id);
    }

    @AfterEach
    void clear() {
        jdbcTemplate.execute("delete from room where id =" + id);
        jdbcTemplate.execute("delete from game_status where id =" + id);
    }

    @DisplayName("체스게임을 시작하면 처음 상태는 READY 여야 한다.")
    @Test
    void getStatus() {
        // given
        GameStatus initStatus = GameStatus.READY;

        // then
        Assertions.assertThat(jdbcTemplateGameStatusDao.getStatus(id)).isEqualTo(initStatus.toString());
    }

    @DisplayName("상태를 PLAYING 로 변경 후 변경 값을 확인한다.")
    @Test
    void update() {
        // given
        GameStatus initStatus = GameStatus.READY;
        GameStatus nextStatus = GameStatus.PLAYING;
        //when
        jdbcTemplateGameStatusDao.update(initStatus.toString(), nextStatus.toString(), id);
        // then
        Assertions.assertThat(jdbcTemplateGameStatusDao.getStatus(id)).isEqualTo(nextStatus.toString());
    }

    @DisplayName("리셋을 하면 초기값은 READY 이다.")
    @Test
    void reset() {
        // given
        GameStatus initStatus = GameStatus.READY;
        GameStatus nextStatus = GameStatus.PLAYING;
        //when
        jdbcTemplateGameStatusDao.update(initStatus.toString(), nextStatus.toString(), id);
        jdbcTemplateGameStatusDao.reset(initStatus, id);
        // then
        Assertions.assertThat(jdbcTemplateGameStatusDao.getStatus(id)).isEqualTo(initStatus.toString());
    }
}
