package chess.dto.response;

import chess.domain.Status;

public class StatusResponse {

    private final boolean isOk;
    private final String whiteScore;
    private final String blackScore;

    public StatusResponse(Status status) {
        this.isOk = true;
        this.whiteScore = String.valueOf(status.getWhiteScore());
        this.blackScore = String.valueOf(status.getBlackScore());
    }

    public boolean isOk() {
        return isOk;
    }

    public String getWhiteScore() {
        return whiteScore;
    }

    public String getBlackScore() {
        return blackScore;
    }
}
