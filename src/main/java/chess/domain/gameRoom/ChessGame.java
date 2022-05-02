package chess.domain.gameRoom;

import chess.domain.board.BoardGenerator;
import chess.domain.board.ChessBoard;
import chess.domain.board.ChessBoardGenerator;
import chess.domain.game.Movement;
import chess.domain.position.Position;

public final class ChessGame {

    private String id;
    private String name;
    private String password;
    private boolean isEnd;
    private ChessBoard chessBoard;

    private ChessGame(final BoardGenerator boardGenerator) {
        this.chessBoard = new ChessBoard(boardGenerator);
    }

    public static ChessGame initChessGame() {
        ChessGame chessGame = new ChessGame(new ChessBoardGenerator());
        return chessGame;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void execute(final Movement movement) {
        chessBoard.move(
                Position.of(movement.getSource()),
                Position.of(movement.getTarget())
        );
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public boolean isKingDied() {
        return !chessBoard.checkKingExist();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
