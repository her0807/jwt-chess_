package chess.database.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import chess.database.dto.RoomDto;
import chess.domain.board.Board;
import chess.domain.board.InitialBoardGenerator;
import chess.domain.board.Point;
import chess.domain.board.Route;
import chess.domain.game.GameState;
import chess.domain.game.Ready;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@Sql("/sql/chess-test.sql")
@SpringBootTest
class BoardDaoTest {

    private static final String TEST_ROOM_NAME = "TESTING";
    private static final String TEST_ROOM_PASSWORD = "1234";
    private static final String TEST_CREATION_ROOM_NAME = "TESTING22";
    private static final String TEST_CREATION_ROOM_PASSWORD = "4321";

    private static GameState state;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RoomDao roomDao;
    private GameDao gameDao;
    private BoardDao boardDao;

    @BeforeEach
    void setUp() {
        roomDao = new RoomDao(jdbcTemplate);
        gameDao = new GameDao(jdbcTemplate);
        boardDao = new BoardDao(jdbcTemplate);

        state = new Ready();

        RoomDto testRoomDto = new RoomDto(TEST_ROOM_NAME, TEST_ROOM_PASSWORD);
        RoomDto testRoomDto2 = new RoomDto(TEST_CREATION_ROOM_NAME, TEST_CREATION_ROOM_PASSWORD);

        testRoomDto = roomDao.create(testRoomDto);
        testRoomDto2 = roomDao.create(testRoomDto2);

        gameDao.create(testRoomDto.getId(), state);
        gameDao.create(testRoomDto2.getId(), state);

        boardDao = new BoardDao(jdbcTemplate);
        Board board = Board.of(new InitialBoardGenerator());

        boardDao.saveBoard(testRoomDto.getId(), board);
    }


    @Test
    @DisplayName("말의 위치와 종류를 저장한다.")
    public void insert() {
        Board board = Board.of(new InitialBoardGenerator());
        RoomDto roomDto = roomDao.findByName(TEST_CREATION_ROOM_NAME);

        assertThatCode(
            () -> boardDao.saveBoard(roomDto.getId(), board))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("말의 위치와 종류를 조회한다.")
    public void select() {
        RoomDto roomDto = roomDao.findByName(TEST_ROOM_NAME);
        Board findBoard = boardDao.readBoard(roomDto.getId());
        assertThat(findBoard.getPointPieces().size()).isEqualTo(64);
    }

    @Test
    @DisplayName("말의 위치를 움직인다.")
    public void update() {
        RoomDto roomDto = roomDao.findByName(TEST_ROOM_NAME);
        Route route = Route.of(List.of("a2", "a4"));

        assertThatCode(
            () -> boardDao.updatePiece(roomDto.getId(), route.getSource(), route.getDestination()))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("말을 삭제한다.")
    public void delete() {
        RoomDto roomDto = roomDao.findByName(TEST_ROOM_NAME);
        Point point = Point.of("b2");

        assertThatCode(() -> boardDao.deletePiece(roomDto.getId(), point))
            .doesNotThrowAnyException();
    }
}
