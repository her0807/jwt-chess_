package chess.domain.position;

import static chess.domain.position.Row.FIVE;
import static chess.domain.position.Row.FOUR;
import static chess.domain.position.Row.ONE;
import static chess.domain.position.Row.SIX;
import static chess.domain.position.Row.THREE;
import static chess.domain.position.Row.TWO;
import static chess.domain.position.Row.values;
import static chess.domain.position.Row.valuesByDescending;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RowTest {

    @Test
    @DisplayName("행은 1~8의 범위를 가짐")
    void row_mustInOneToEight() {
        List<Integer> values = Stream.of(values())
                .map(Row::value)
                .collect(Collectors.toList());

        assertThat(values).doesNotContain(9);
    }

    @Test
    @DisplayName("정렬된 전체 로우를 가지고 옴")
    void allRows_sortedByDescending() {
        List<Integer> orderedValues = valuesByDescending();

        assertThat(orderedValues).containsExactly(8, 7, 6, 5, 4, 3, 2, 1);
    }

    @Test
    @DisplayName("시작 로우와 목표 로우 사이에 있는 로우들을 반환한다")
    void calculate_rowPaths() {
        assertThat(ONE.rowPaths(SIX)).containsExactly(TWO, THREE, FOUR, FIVE);
    }

    @Test
    @DisplayName("시작 로우와 목표 로우 사이에 있는 로우들을 순서에 맞게 반환한다")
    void calculate_rowPathsInRightOrder() {
        assertThat(SIX.rowPaths(TWO)).containsExactly(FIVE, FOUR, THREE);
    }
}
