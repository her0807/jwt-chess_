package chess.model.piece;

import chess.model.board.Board;
import chess.model.square.Square;

import java.util.Collections;
import java.util.List;

public abstract class PointMovingPiece extends Piece {

    protected PointMovingPiece(Team team) {
        super(team);
    }

    protected PointMovingPiece(int id, Team team, int squareId) {
        super(id, team, squareId);
    }

    @Override
    public boolean movable(Square source, Square target) {
        return getDirection().stream()
                .anyMatch(direction -> source.findLocation(direction, target));
    }

    public boolean canMoveWithoutObstacle(Board board, Square source, Square target) {
        Piece targetPiece = board.get(target);
        return isNotAlly(targetPiece);
    }

    @Override
    public List<Square> getRoute(Square source, Square target) {
        return Collections.emptyList();
    }
}
