package chess.model.piece;

import chess.model.board.Board;
import chess.model.square.Square;

import java.util.Collections;
import java.util.List;

public abstract class LinearMovingPiece extends Piece {

    private static final int LINEAR_MOVING_PIECE_MAX_DISTANCE = 7;

    protected LinearMovingPiece(Team team) {
        super(team);
    }

    public LinearMovingPiece(int id, Team team, int squareId) {
        super(id, team, squareId);
    }

    @Override
    public boolean movable(Square source, Square target) {
        List<Square> route = getRoute(source, target);
        return !route.isEmpty();
    }

    @Override
    public boolean canMoveWithoutObstacle(Board board, Square source, Square target) {
        Piece targetPiece = board.get(target);
        List<Square> route = getRoute(source, target);
        if (route.isEmpty()) {
            return false;
        }
        return checkEachSquare(board, targetPiece, route);
    }

    private boolean checkEachSquare(Board board, Piece targetPiece, List<Square> route) {
        for (Square square : route) {
            Piece tempPiece = board.get(square);
            if (tempPiece.equals(targetPiece) && isNotAlly(targetPiece)) {
                return true;
            }
            if (tempPiece.isNotEmpty()) {
                return false;
            }
        }
        return false;
    }

    public List<Square> getRoute(Square source, Square target) {
        return getDirection().stream()
                .map(direction -> source.findRoad(direction, LINEAR_MOVING_PIECE_MAX_DISTANCE))
                .filter(squares -> squares.contains(target))
                .findFirst()
                .orElseGet(Collections::emptyList);
    }
}
