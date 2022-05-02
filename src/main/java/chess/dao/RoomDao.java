package chess.dao;

import chess.domain.game.room.Room;
import chess.domain.game.room.RoomId;
import chess.domain.game.room.RoomPassword;
import chess.domain.piece.PieceColor;

import java.util.List;

public interface RoomDao {

    void createRoom(Room room);

    List<Room> getAllRooms();

    void deleteRoom(RoomId roomId, RoomPassword roomPassword);

    void updateTurnToWhite(RoomId roomId);

    void updateTurnToBlack(RoomId roomId);

    PieceColor getCurrentTurn(RoomId roomId);

    void validateRoomExisting(RoomId roomId);
}
