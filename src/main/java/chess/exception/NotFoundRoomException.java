package chess.exception;

public class NotFoundRoomException extends RuntimeException {
    public NotFoundRoomException() {
        super("해당하는 체스방을 찾을 수 없습니다.");
    }
}
