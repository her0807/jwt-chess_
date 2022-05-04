package chess.domain.player;

import chess.domain.generator.Generator;
import chess.domain.piece.Piece;
import chess.domain.position.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Player {

    private final List<Piece> pieces;
    private final Team team;

    public Player(List<Piece> pieces, Team team) {
        this.pieces = new ArrayList<>(pieces);
        this.team = team;
    }

    public Player(Generator generator, Team team) {
        this(generator.generate(), team);
    }

    public boolean hasPiece(final Position position) {
        return pieces.stream()
                .anyMatch(piece -> piece.exist(position));
    }

    public List<Piece> findAll() {
        return pieces.stream()
                .collect(Collectors.toUnmodifiableList());
    }

    public void move(final Position current, final Position destination) {
        final Piece piece = findPiece(current);
        piece.move(current, destination, team);
    }

    public void capture(final Position current, final Position destination) {
        final Piece piece = findPiece(current);
        piece.capture(current, destination, team);
    }

    public void remove(final Position position) {
        pieces.remove(findPiece(position));
    }

    private Piece findPiece(final Position position) {
        return pieces.stream()
                .filter(piece -> piece.exist(position))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("체스말이 존재하지 않습니다."));
    }

    public Score calculateScore() {
        return new Score(Collections.unmodifiableList(pieces));
    }

    public boolean hasKing() {
        return pieces.stream()
                .anyMatch(Piece::isKing);
    }

    public String getTeamName() {
        return team.getName();
    }
}
