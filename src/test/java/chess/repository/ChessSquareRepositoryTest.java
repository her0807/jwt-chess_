package chess.repository;

import chess.model.board.Board;
import chess.model.piece.Initializer;
import chess.model.piece.Piece;
import chess.model.piece.Team;
import chess.model.square.File;
import chess.model.square.Rank;
import chess.model.square.Square;
import chess.model.status.Running;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
class ChessSquareRepositoryTest {

    @Autowired
    private ChessSquareRepository repository;
    @Autowired
    private ChessBoardRepository chessBoardRepository;
    private int boardId;
    private Square square;

    @BeforeEach
    void setup() {
        final Board board = chessBoardRepository.save(new Board(new Running(), Team.WHITE));
        this.boardId = board.getId();
        this.square = repository.save(new Square(File.A, Rank.TWO, boardId));
    }

    @Test
    void save() {
        final Square square = repository.save(new Square(File.B, Rank.TWO, boardId));

        assertAll(
                () -> assertThat(square.getFile()).isEqualTo(File.B),
                () -> assertThat(square.getRank()).isEqualTo(Rank.TWO)
        );
    }

    @Test
    void getBySquareTest() {
        final Square square = repository.getBySquareAndBoardId(new Square(File.A, Rank.TWO), boardId);

        assertAll(
                () -> assertThat(square.getFile()).isEqualTo(File.A),
                () -> assertThat(square.getRank()).isEqualTo(Rank.TWO)
        );
    }

    @Test
    void saveAllSquares() {
        int saveCount = repository.saveAllSquares(boardId, Initializer.initialize().keySet());

        assertThat(saveCount).isEqualTo(64);
    }

    @Test
    void findAllSquaresAndPieces() {
        Map<Square, Piece> all = repository.findAllSquaresAndPieces(boardId);

        for (Square square : all.keySet()) {
            assertThat(all.get(square).name()).isEqualTo("p");
        }
    }
}
