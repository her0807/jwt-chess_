package chess.db;

import chess.domain.piece.Color;
import chess.domain.state.StateSwitch;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StateDAO {

    private static final String FIND_COLOR_SQL = "select color from state where roomID = ?";
    private static final String CONVERT_COLOR_SQL = "update state set color = ? where roomID = ?";
    private static final String CHECK_SAVE_SQL = "select count(*) from room where id = ?";
    private static final String CHECK_END_SQL = "select count(*) from state where roomID = ? and now = ?";
    private static final String FIND_ALL_USERS_SQL = "select id from room";
    private static final String TERMINATE_GAME_SQL = "delete from room where id = ?";
    private static final String INITIALIZE_ROOM_SQL = "insert into room (name, password) values (?, ?)";
    private static final String INITIALIZE_COLOR_SQL = "insert into state values (?, ?, ?)";
    private static final String TERMINATE_STATE_SQL = "update state set now = ? where roomID = ?";
    private static final String DELIMITER = ", ";

    private JdbcTemplate jdbcTemplate;

    public StateDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<String> stringRowMapper = (resultSet, rowNum) ->
            resultSet.getString("id");

    private final RowMapper<String> colorRowMapper = (resultSet, rowNum) ->
            resultSet.getString("color");

    public Color findColor(String roomId) {
        String color = jdbcTemplate.queryForObject(FIND_COLOR_SQL, colorRowMapper, roomId);
        return Color.valueOf(color);
    }

    public void convertColor(Color color, String roomId) {
        jdbcTemplate.update(CONVERT_COLOR_SQL, color.name(), roomId);
    }

    public void initializeRoom(String name, String password) {
        jdbcTemplate.update(INITIALIZE_ROOM_SQL, name, password);
    }

    public void initializeColor(String roomId) {
        jdbcTemplate.update(INITIALIZE_COLOR_SQL, roomId, StateSwitch.ON.name(), Color.WHITE.name());
    }

    public void terminateState(String roomId) {
        jdbcTemplate.update(TERMINATE_STATE_SQL, StateSwitch.OFF.name(), roomId);
    }

    public boolean isEndedGame(String id) {
        int count = jdbcTemplate.queryForObject(CHECK_END_SQL, Integer.class, id, StateSwitch.OFF.name());
        return count > 0;
    }
}
