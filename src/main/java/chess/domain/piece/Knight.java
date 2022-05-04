package chess.domain.piece;

import chess.domain.player.Team;
import chess.domain.position.MoveChecker;
import chess.domain.position.Position;
import chess.exception.IllegalRequestDataException;

public class Knight extends Piece {

    public Knight(Position position) {
        super(State.KNIGHT, position);
    }

    @Override
    public Position move(final Position currentPosition, final Position destinationPosition, final Team team) {
        boolean isMoveOfKnight = MoveChecker.isForKnight(currentPosition, destinationPosition);
        if (!isMoveOfKnight) {
            throw new IllegalRequestDataException("나이트는 상하좌우로 1칸 이동 후 대각선으로 1칸 이동해야 합니다.");
        }

        position = destinationPosition;
        return position;
    }
}
