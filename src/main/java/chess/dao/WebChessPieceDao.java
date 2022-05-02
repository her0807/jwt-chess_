package chess.dao;

import chess.domain.pieces.Color;
import chess.domain.pieces.Piece;
import chess.domain.pieces.Symbol;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class WebChessPieceDao implements PieceDao<Piece> {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public WebChessPieceDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Piece save(Piece piece) {
        final String sql = "INSERT INTO piece (type, color, position_id) VALUES (:type, :color, :position_id)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        List<String> keys = List.of("type", "color", "position_id");
        List<Object> values = List.of(piece.getType().symbol().name(), piece.getColor().name(), piece.getPositionId());

        jdbcTemplate.update(sql, ParameterSourceCreator.makeParameterSource(keys, values), keyHolder);
        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();

        return new Piece(id, piece.getColor(), piece.getType(), piece.getPositionId());
    }

    @Override
    public Optional<Piece> findByPositionId(int positionId) {
        final String sql = "SELECT * FROM piece WHERE position_id=:position_id";
        List<String> keys = List.of("position_id");
        List<Object> values = List.of(positionId);
        SqlParameterSource namedParameters = ParameterSourceCreator.makeParameterSource(keys, values);

        final Piece piece = DataAccessUtils.singleResult(jdbcTemplate.query(sql, namedParameters, (rs, rowNum) -> makePiece(rs)));
        return Optional.ofNullable(piece);
    }

    private Piece makePiece(ResultSet resultSet) throws SQLException {
        return new Piece(
                resultSet.getInt("id"),
                Color.findColor(resultSet.getString("color")),
                Symbol.findSymbol(resultSet.getString("type")).type(),
                resultSet.getInt("position_id")
        );
    }

    @Override
    public int updatePiece(Piece source, Piece target) {
        final String sql = "UPDATE piece SET type=:type, color=:color WHERE id=:piece_id";
        List<String> keys = List.of("type", "color", "piece_id");
        List<Object> values = List.of(target.getType().symbol().name(), target.getColor().name(), source.getId());
        SqlParameterSource namedParameters = ParameterSourceCreator.makeParameterSource(keys, values);
        return jdbcTemplate.update(sql, namedParameters);
    }

    @Override
    public int deleteByPositionId(int positionId) {
        final String sql = "DELETE FROM piece WHERE position_id=:position_id";
        List<String> keys = List.of("position_id");
        List<Object> values = List.of(positionId);
        SqlParameterSource namedParameters = ParameterSourceCreator.makeParameterSource(keys, values);
        return jdbcTemplate.update(sql, namedParameters);
    }

    @Override
    public List<Piece> getAllByBoardId(int boardId) {
        final String sql =
                "SELECT pi.id, pi.type, pi.color, pi.position_id FROM piece pi JOIN position po ON pi.position_id=po.id "
                        + "JOIN board nb ON po.board_id=nb.id WHERE nb.id=:board_id";
        List<String> keys = List.of("board_id");
        List<Object> values = List.of(boardId);
        SqlParameterSource namedParameters = ParameterSourceCreator.makeParameterSource(keys, values);
        return jdbcTemplate.query(sql, namedParameters, (rs, rowNum) -> makePiece(rs));
    }

    @Override
    public void saveAll(List<Piece> pieces) {
        final String sql = "INSERT INTO piece (type, color, position_id) VALUES (:type, :color, :position_id)";

        List<Map<String, Object>> batchValues = makeBatchValues(pieces);
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(batchValues);
        jdbcTemplate.batchUpdate(sql, batch);
    }

    private List<Map<String, Object>> makeBatchValues(List<Piece> pieces) {
        List<Map<String, Object>> batchValues = new ArrayList<>(pieces.size());
        for (Piece piece : pieces) {
            batchValues.add(
                    new MapSqlParameterSource("type", piece.getType().symbol().name())
                            .addValue("color", piece.getColor().name())
                            .addValue("position_id", piece.getPositionId())
                            .getValues());
        }
        return batchValues;
    }
}
