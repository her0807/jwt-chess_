package chess.dao;

import chess.entity.Room;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RoomDaoImpl implements RoomDao {

    private final JdbcTemplate jdbcTemplate;

    public RoomDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Room> actorRowMapper = (resultSet, rowNum) -> new Room(
            resultSet.getInt("roomId"),
            resultSet.getString("name"),
            resultSet.getString("password"),
            resultSet.getString("gameState"),
            resultSet.getString("turn")
    );

    @Override
    public void saveNewRoom(final String roomName, final String password, final String gameState, final String turn) {
        final String sql = "insert into room (name, password, gameState, turn) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, roomName, password, gameState, turn);
    }

    @Override
    public boolean hasDuplicatedName(final String roomName) {
        final String sql = "select * from room where name = ? LIMIT 1";
        final int count = jdbcTemplate.queryForList(sql, roomName).size();
        return count > 0;
    }

    @Override
    public void deleteRoomByName(final int roomId) {
        final String sql = "delete from room where roomId = ?";
        jdbcTemplate.update(sql, roomId);
    }

    public void saveTurn(final int roomId, final String turn) {
        final String sql = "update room set turn = ? where roomId = ?";
        jdbcTemplate.update(sql, turn, roomId);
    }

    @Override
    public void saveGameState(final int roomId, final String state) {
        final String sql = "update room set gameState = ? where roomId = ?";
        jdbcTemplate.update(sql, state, roomId);
    }

    @Override
    public Room findByRoomId(final int roomId) {
        final String sql = "select * from room where roomId = ?";
        return jdbcTemplate.queryForObject(sql, actorRowMapper, roomId);
    }

    @Override
    public List<Room> findAllRooms() {
        final String sql = "select * from room";
        return jdbcTemplate.query(sql, actorRowMapper);
    }
}
