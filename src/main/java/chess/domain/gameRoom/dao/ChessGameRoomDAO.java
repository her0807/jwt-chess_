package chess.domain.gameRoom.dao;

import chess.domain.gameRoom.ChessGame;
import chess.domain.gameRoom.dto.ChessGameRoomPassInfoResponse;
import chess.domain.gameRoom.dto.ChessGameRoomShowInfoResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ChessGameRoomDAO {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ChessGameRoomShowInfoResponse> chessGameRoomInfoDTORowMapper = (rs, rowNum) ->
            new ChessGameRoomShowInfoResponse(
                    rs.getString(ID),
                    rs.getString(NAME)
            );

    public ChessGameRoomDAO(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String addGame(final ChessGame chessGame) {
        String sql = "INSERT INTO CHESS_GAME (name, password) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{ID});
            statement.setString(1, chessGame.getName());
            statement.setString(2, chessGame.getPassword());
            return statement;
        }, keyHolder);
        return String.valueOf(keyHolder.getKey().longValue());
    }

    public List<ChessGameRoomShowInfoResponse> findActiveGames() {
        String sql = "SELECT id, name FROM CHESS_GAME WHERE IS_END = false";
        return jdbcTemplate.query(sql, chessGameRoomInfoDTORowMapper);
    }

    public ChessGameRoomShowInfoResponse findShowGameById(final String gameId) {
        String sql = "SELECT id, name FROM CHESS_GAME WHERE ID = ? AND IS_END = FALSE ORDER BY created_at";
        return jdbcTemplate.queryForObject(sql, chessGameRoomInfoDTORowMapper, gameId);
    }

    public ChessGameRoomPassInfoResponse findPassGameById(final String gameId) {
        String sql = "SELECT id, name, password FROM CHESS_GAME WHERE ID = ? AND IS_END = FALSE ORDER BY created_at";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) ->
                new ChessGameRoomPassInfoResponse(
                        rs.getString(ID),
                        rs.getString(NAME),
                        rs.getString(PASSWORD)
                ), gameId);
    }

    public void updateGameEnd(final String gameId) {
        String sql = "UPDATE chess_game SET is_end = true WHERE id = ?";
        jdbcTemplate.update(sql, gameId);
    }

    public int deleteGameByIdAndPassword(String gameId, String password) {
        String sql = "DELETE FROM CHESS_GAME WHERE ID = ? AND PASSWORD = ?";

        return jdbcTemplate.update(sql,
                gameId,
                password);
    }
}
