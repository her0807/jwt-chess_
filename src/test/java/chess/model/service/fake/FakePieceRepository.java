package chess.model.service.fake;

import chess.model.piece.Piece;
import chess.model.piece.Team;
import chess.model.square.File;
import chess.repository.PieceRepository;

import java.util.List;

public class FakePieceRepository implements PieceRepository<Piece> {

    @Override
    public Piece save(Piece piece, int squareId) {
        return null;
    }

    @Override
    public Piece findBySquareId(int squareId) {
        return null;
    }

    @Override
    public int updatePieceSquareId(int originSquareId, int newSquareId) {
        return 0;
    }

    @Override
    public int deletePieceBySquareId(int squareId) {
        return 0;
    }

    @Override
    public List<Piece> getAllPiecesByBoardId(int boardId) {
        return null;
    }

    @Override
    public int countPawnsOnSameFile(int roomId, File column, Team team) {
        return 0;
    }

    @Override
    public int saveAllPieces(List<Piece> pieces) {
        return 0;
    }
}
