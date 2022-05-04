package chess.domain;

import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.domain.state.State;
import chess.domain.state.StateType;

public class ChessGame {

    private State state;

    public ChessGame(State state) {
        this.state = state;
    }

    public void start() {
        state = state.start();
    }

    public void end() {
        state = state.end();
    }

    public void move(Position source, Position target) {
        state = state.move(source, target);
    }

    public boolean isRunning() {
        return state.isRunning();
    }

    public void checkRunning() {
        if (isRunning()) {
            throw new IllegalArgumentException("게임이 아직 진행중입니다.");
        }
    }

    public boolean isFinished() {
        return state.isFinished();
    }

    public double score(Color color) {
        ChessBoard chessBoard = state.chessBoard();
        return chessBoard.calculateScore(color);
    }

    public Result result() {
        return state.winner();
    }

    public Piece findPiece(Position position) {
        return state.chessBoard().getPieceByPosition(position);
    }

    public StateType getStateType() {
        return state.getStateType();
    }

    public Board board() {
        return state.chessBoard().getBoard();
    }
}
