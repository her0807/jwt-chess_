package chess.dto.response;

import chess.domain.ChessGame;
import chess.domain.ChessMap;
import chess.domain.player.Team;

public class ChessGameDto {

    private final String gameName;
    private final char[][] chessMap;
    private final String turn;
    private final boolean isRunning;

    public ChessGameDto(String gameName, char[][] chessMap, String turn, boolean isRunning) {
        this.gameName = gameName;
        this.chessMap = chessMap;
        this.turn = turn;
        this.isRunning = isRunning;
    }

    public static ChessGameDto of(final ChessGame chessGame, final String gameName) {
        final ChessMap chessMap = chessGame.createMap();
        final Team turn = chessGame.getTurn();
        final boolean isRunning = chessGame.isRunning();
        return new ChessGameDto(gameName, chessMap.getChessMap(), turn.getName(), isRunning);
    }

    public String getGameName() {
        return gameName;
    }

    public char[][] getChessMap() {
        return chessMap;
    }

    public String getTurn() {
        return turn;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
