package chess.model.position;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionTest {

    @Test
    @DisplayName("체스판을 벗어나는 position이 주어지면 예외가 발생한다.")
    void outOfRangePositionTest() {
        assertThatThrownBy(() -> Position.from("a9"))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
