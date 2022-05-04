package chess.domain;

import chess.domain.player.Player;
import chess.domain.player.Result;
import chess.domain.player.Score;
import chess.domain.player.Team;
import chess.domain.position.Position;
import chess.exception.IllegalRequestDataException;

import java.util.ArrayList;
import java.util.List;

public class ChessGame {

    private final Player whitePlayer;
    private final Player blackPlayer;

    private Team turn;

    public ChessGame(Player whitePlayer, Player blackPlayer) {
        this(whitePlayer, blackPlayer, Team.WHITE);
    }

    public ChessGame(Player whitePlayer, Player blackPlayer, Team turn) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.turn = turn;
    }

    public ChessMap createMap() {
        return ChessMap.of(whitePlayer.findAll(), blackPlayer.findAll());
    }

    public boolean isRunning() {
        return whitePlayer.hasKing() && blackPlayer.hasKing();
    }

    public void move(final Player currentPlayer, final Player opponentPlayer,
                     final Position currentPosition, final Position destinationPosition) {
        validateMovable(currentPlayer, currentPosition, destinationPosition);
        final boolean hasPieceOfOpponentPlayer = opponentPlayer.hasPiece(destinationPosition);
        if (hasPieceOfOpponentPlayer) {
            capture(currentPlayer, opponentPlayer, currentPosition, destinationPosition);
            return;
        }
        currentPlayer.move(currentPosition, destinationPosition);
        turn = turn.next();
    }

    private void capture(final Player currentPlayer, final Player opponentPlayer, final Position currentPosition,
                         Position destinationPosition) {
        currentPlayer.capture(currentPosition, destinationPosition);
        opponentPlayer.remove(destinationPosition);
        turn = turn.next();
    }

    private void validateMovable(final Player currentPlayer, final Position currentPosition,
                                 final Position destinationPosition) {
        if (!currentPlayer.hasPiece(currentPosition)) {
            throw new IllegalRequestDataException("선택한 출발 위치에 현재 유저의 체스말이 존재하지 않습니다.");
        }
        if (currentPlayer.hasPiece(destinationPosition)) {
            throw new IllegalRequestDataException("선택한 도착 위치에 이미 체스말이 존재합니다.");
        }
        if (hasPieceBetweenPosition(currentPosition, destinationPosition)) {
            throw new IllegalRequestDataException("이동 경로에 체스말이 존재합니다.");
        }
    }

    private boolean hasPieceBetweenPosition(final Position current, final Position destination) {
        final List<Position> positions = current.findAllBetweenPosition(destination);
        boolean hasPieceOfBlackPlayer = positions.stream()
                .anyMatch(blackPlayer::hasPiece);
        boolean hasPieceOfWhitePlayer = positions.stream()
                .anyMatch(whitePlayer::hasPiece);

        return hasPieceOfBlackPlayer || hasPieceOfWhitePlayer;
    }

    public List<GameResult> findGameResult() {
        final Score whitePlayerScore = whitePlayer.calculateScore();
        final Score blackPlayerScore = blackPlayer.calculateScore();

        final Result whitePlayerResult = Result.from(whitePlayerScore.getScore(), blackPlayerScore.getScore(),
                whitePlayer.hasKing(), blackPlayer.hasKing());
        final Result blackPlayerResult = Result.from(blackPlayerScore.getScore(), whitePlayerScore.getScore(),
                blackPlayer.hasKing(), whitePlayer.hasKing());

        return createGameResult(whitePlayerScore, blackPlayerScore, whitePlayerResult, blackPlayerResult);
    }

    private List<GameResult> createGameResult(final Score whitePlayerScore, final Score blackPlayerScore,
                                              final Result whitePlayerResult, final Result blackPlayerResult) {
        final List<GameResult> results = new ArrayList<>();
        results.add(new GameResult(whitePlayer.getTeamName(), whitePlayerScore, whitePlayerResult));
        results.add(new GameResult(blackPlayer.getTeamName(), blackPlayerScore, blackPlayerResult));
        return results;
    }

    public Player getCurrentPlayer() {
        if (turn == Team.WHITE) {
            return whitePlayer;
        }
        return blackPlayer;
    }

    public Player getOpponentPlayer() {
        if (turn == Team.WHITE) {
            return blackPlayer;
        }
        return whitePlayer;
    }

    public Team getTurn() {
        return turn;
    }
}
