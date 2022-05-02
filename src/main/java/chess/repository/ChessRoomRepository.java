package chess.repository;

import chess.model.room.Room;
import chess.model.status.Status;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ChessRoomRepository implements RoomRepository<Room> {

    private final JdbcTemplate jdbcTemplate;

    public ChessRoomRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Room> findAll() {
        String sq = "SELECT * FROM room r JOIN board b on r.board_id=b.id";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sq);
        List<Room> rooms = new ArrayList<>();
        while (sqlRowSet.next()) {
            rooms.add(new Room(
                    sqlRowSet.getInt("id"),
                    sqlRowSet.getString("title"),
                    sqlRowSet.getString("password"),
                    sqlRowSet.getInt("board_id"))
            );
        }
        return rooms;
    }

    @Override
    public List<Room> findAllByBoardStatus(Status status) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(
                "SELECT * FROM room r JOIN board b on r.board_id=b.id WHERE b.status=?", status.name());
        List<Room> rooms = new ArrayList<>();
        while (sqlRowSet.next()) {
            rooms.add(new Room(
                    sqlRowSet.getInt("id"),
                    sqlRowSet.getString("title"),
                    sqlRowSet.getString("password"),
                    sqlRowSet.getInt("board_id"))
            );
        }
        return rooms;
    }

    @Override
    public Room save(Room room) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("room").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", room.getTitle());
        parameters.put("password", room.getPassword());
        parameters.put("board_id", room.getBoardId());

        Number number = simpleJdbcInsert.executeAndReturnKey(parameters);
        return new Room(number.intValue(), room.getTitle(), room.getPassword(), room.getBoardId());
    }

    @Override
    public Room getById(int roomId) {
        return jdbcTemplate.queryForObject("SELECT * FROM room WHERE id = ?", roomRowMapper(), roomId);
    }

    @Override
    public void deleteById(int roomId) {
        jdbcTemplate.update("DELETE FROM room WHERE id = ?", roomId);
    }

    private RowMapper<Room> roomRowMapper() {
        return (resultSet, rowNum) -> new Room(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getString("password"),
                resultSet.getInt("board_id")
        );
    }
}
