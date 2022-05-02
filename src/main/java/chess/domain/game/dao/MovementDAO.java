package chess.domain.game.dao;

import chess.domain.game.Movement;
import chess.domain.position.Position;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MovementDAO {

    private final RowMapper<Movement> movementRowMapper = (rs, rowNum) -> new Movement(
            Position.of(rs.getString("source")),
            Position.of(rs.getString("target"))
    );
    private final JdbcTemplate jdbcTemplate;

    public MovementDAO(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addMoveCommand(final Movement movement) {
        String sql = "INSERT INTO movement (game_id, source, target, turn)"
                + "SELECT ?, ?, ?, ?"
                + "WHERE (SELECT turn FROM movement WHERE game_id = ? ORDER BY created_at DESC LIMIT 1) = ?"
                + "   OR (SELECT turn FROM movement WHERE game_id = ? ORDER BY created_at DESC LIMIT 1) IS NULL;";

        jdbcTemplate.update(sql,
                movement.getGameId(),
                movement.getSource(),
                movement.getTarget(),
                movement.getTeam().name(),
                movement.getGameId(),
                movement.getTeam().changeTeam().name(),
                movement.getGameId()
        );
    }

    public List<Movement> findMovementByGameId(final String gameId) {
        String sql = "SELECT * FROM CHESS_GAME cg JOIN MOVEMENT m on cg.id = m.game_id WHERE cg.is_end=false AND cg.id=(?) ORDER BY m.created_at";
        return jdbcTemplate.query(
                sql,
                movementRowMapper,
                gameId);
    }
}
