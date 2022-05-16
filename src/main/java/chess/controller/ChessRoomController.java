package chess.controller;

import chess.dto.request.RoomRequest;
import chess.dto.request.UserPasswordRequest;
import chess.dto.response.RoomResponse;
import chess.service.RoomService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ChessRoomController {

    private final RoomService roomService;

    public ChessRoomController(final RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("search")
    public List<RoomResponse> search() {
        return roomService.searchRooms();
    }

    @PostMapping("create")
    public List<RoomResponse> create(@RequestBody RoomRequest roomRequest) {
        return roomService.createRoom(roomRequest);
    }

    @PostMapping("delete/{id}")
    public List<RoomResponse> delete(@PathVariable("id") String id,
                                     @RequestBody UserPasswordRequest userPasswordRequest) {
        return roomService.deleteRoomFrom(id, userPasswordRequest);
    }
}
