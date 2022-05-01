package chess.model.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommandTest {

    @Test
    @DisplayName("Command가 start 또는 end가 아니면 예외가 발생한다.")
    void validateMenu() {
        assertThatThrownBy(() -> {
            new Start("aaa");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("command가 end이면 true를 반환한다.")
    void isEnd() {
        Command command = new End("end");
        assertThat(command.isEnd()).isTrue();
    }
}
