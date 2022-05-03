package chess.dao;

import chess.domain.ChessGame;
import chess.domain.Color;
import chess.dto.RoomDto;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDaoImpl implements BoardDao {

    private final JdbcTemplate jdbcTemplate;

    public BoardDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Color findTurn(Long boardId) {
        final String sql = "select turn from board where id = ?";
        final String turn = jdbcTemplate.queryForObject(sql, String.class, boardId);

        return Color.from(turn);
    }

    @Override
    public void deleteBoard(Long boardId) {
        final String sql = "delete from board where id = ?";

        jdbcTemplate.update(sql, boardId);
    }

    @Override
    public boolean existsBoardByName(String title) {
        final String sql = "select count(*) from board where title = ?";
        final Integer numOfGame = jdbcTemplate.queryForObject(sql, Integer.class, title);
        return !numOfGame.equals(0);
    }

    @Override
    public Long save(ChessGame chessGame) {
        final String sql = "insert into board (turn, title, password) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, chessGame.getBoard().getTurn().name());
            ps.setString(2, chessGame.getRoom().getTitle());
            ps.setString(3, chessGame.getRoom().getPassword());
            return ps;
        }, keyHolder);
        return Long.valueOf(keyHolder.getKey().longValue());
    }

    @Override
    public void updateTurn(Long boardId, Color turn) {
        final String sql = "update board set turn = ? where id = ?";
        jdbcTemplate.update(sql, turn.name(), boardId);
    }

    @Override
    public List<RoomDto> findAllRooms() {
        final String sql = "select id, title from board";
        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> new RoomDto(resultSet.getLong("id"), resultSet.getString("title")));
    }

    @Override
    public String findPasswordById(Long boardId) {
        final String sql = "select password from board where id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, boardId);
    }
}
