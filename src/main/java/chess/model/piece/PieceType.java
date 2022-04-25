package chess.model.piece;

import java.util.Arrays;

public enum PieceType {

    p(Pawn::new),
    r(Rook::new),
    b(Bishop::new),
    n(Knight::new),
    q(Queen::new),
    k(King::new),
    empty(((id, team, squareId) -> new Empty(id, squareId))),
    ;

    private final PieceMapper function;

    PieceType(PieceMapper function) {
        this.function = function;
    }

    public static Piece getPiece(int pieceId, String name, Team team, int squareId) {
        return Arrays.stream(values())
                .filter(pieceType -> pieceType.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기물입니다: " + name))
                .function.mapToPiece(pieceId, team, squareId);
    }

}
