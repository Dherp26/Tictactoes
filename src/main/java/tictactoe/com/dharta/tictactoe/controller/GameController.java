package tictactoe.com.dharta.tictactoe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tictactoe.com.dharta.tictactoe.bean.GameBean;
import tictactoe.com.dharta.tictactoe.bean.UserBean;
import tictactoe.com.dharta.tictactoe.exception.NotFoundException;
import tictactoe.com.dharta.tictactoe.service.TicTacToeService;

@Controller
@RequestMapping("api/tictactoe/game")
@RequiredArgsConstructor
public class GameController {
    private final TicTacToeService ticTacToeService;

    @PostMapping(value = "/create")
    @ResponseBody
    public GameBean createGame(@RequestBody UserBean userBean) {
        return ticTacToeService.createGame(userBean);
    }

    @PostMapping("/{gameId}/connect")
    @ResponseBody
    public ResponseEntity<String> connectGame(@RequestBody UserBean user, @PathVariable String gameId) {
        ticTacToeService.connectGame(user, gameId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{gameId}/move")
    @ResponseBody
    public String makeMove(@PathVariable String gameId, @RequestParam int row, @RequestParam int col)
            throws NotFoundException {
        return ticTacToeService.makeMove(gameId, row, col);
    }

    @GetMapping("/{gameId}")
    @ResponseBody
    public GameBean getGame(@PathVariable String gameId) throws NotFoundException {
        return ticTacToeService.getGame(gameId);
    }
}
