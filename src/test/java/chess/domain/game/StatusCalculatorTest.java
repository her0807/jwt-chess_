package chess.domain.game;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import chess.domain.board.Board;
import chess.domain.piece.Team;

public class StatusCalculatorTest {
    @Test
    void calculate() {
        StatusCalculator scoreCalculator = new StatusCalculator(Board.create().getValue());
        assertThat(scoreCalculator.calculate(Team.WHITE)).isEqualTo(38);
    }
}