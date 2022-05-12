package chess.domain.board;

import chess.database.PieceCache;
import chess.domain.piece.Empty;
import chess.domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomBoardGenerator implements BoardGenerator {

    private final Map<Point, Piece> pointPieces;
    public CustomBoardGenerator(Map<Point, Piece> pointPieces) {
        this.pointPieces = new HashMap<>(pointPieces);
    }

    @Override
    public Map<Point, Piece> generate() {
        for (int verticalIndex = LineNumber.MAX; verticalIndex >= LineNumber.MIN; verticalIndex--) {
            generateLine(verticalIndex);
        }
        return Map.copyOf(pointPieces);
    }

    private void generateLine(int verticalIndex) {
        for (int horizontalIndex = LineNumber.MIN; horizontalIndex <= LineNumber.MAX; horizontalIndex++) {
            pointPieces.computeIfAbsent(Point.of(horizontalIndex, verticalIndex), ignored -> Empty.getInstance());
            pointPieces.computeIfAbsent(Point.of(horizontalIndex, verticalIndex),
                ignored -> Empty.getInstance());
        }
    }
}
