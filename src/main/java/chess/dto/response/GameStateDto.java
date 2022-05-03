package chess.dto.response;

import chess.domain.Team;

public class GameStateDto {

    private final Team gameState;
    private final boolean isRunning;

    public GameStateDto(Team gameState, boolean isFinished) {
        this.gameState = gameState;
        this.isRunning = isFinished;
    }

    public Team getGameState() {
        return gameState;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
