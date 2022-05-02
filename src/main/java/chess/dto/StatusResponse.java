package chess.dto;

import java.util.List;

public class StatusResponse {

    private static final int WHITE_SCORE_INDEX = 0;
    private static final int BLACK_SCORE_INDEX = 1;

    private String whiteScore;
    private String blackScore;

    public StatusResponse(String whiteScore, String blackScore) {
        this.whiteScore = whiteScore;
        this.blackScore = blackScore;
    }

    public static StatusResponse createStatsResponse(List<Double> status) {
        return new StatusResponse(
                status.get(WHITE_SCORE_INDEX).toString(),
                status.get(BLACK_SCORE_INDEX).toString());
    }

    public String getWhiteScore() {
        return whiteScore;
    }

    public String getBlackScore() {
        return blackScore;
    }

    @Override
    public String toString() {
        return "StatusRes{" +
                "whiteScore='" + whiteScore + '\'' +
                ", blackScore='" + blackScore + '\'' +
                '}';
    }
}
