package chess.controller;

import chess.dto.RoomsDto;
import chess.dto.response.ScoreResponseDto;
import chess.service.ChessService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ChessController {
    private final ChessService chessService;

    public ChessController(final ChessService chessService) {
        this.chessService = chessService;
    }

    @GetMapping("/rooms")
    public String rooms(final Model model) {
        List<RoomsDto> roomsDto = new ArrayList<>();
        Map<Long, String> rooms = chessService.rooms();
        for (Map.Entry<Long, String> roomEntry : rooms.entrySet()) {
            roomsDto.add(new RoomsDto(roomEntry.getKey(), roomEntry.getValue()));
        }
        model.addAttribute("rooms", roomsDto);
        return "rooms";
    }

    @GetMapping("/chess/{roomId}")
    public String chess(@PathVariable final Long roomId, final Model model) {
        double whiteScore = chessService.whiteScore(roomId);
        double blackScore = chessService.blackScore(roomId);
        ScoreResponseDto scoreResponseDto = new ScoreResponseDto(whiteScore, blackScore);
        model.addAttribute("roomId", roomId);
        model.addAttribute("score", scoreResponseDto);
        return "chess";
    }
}