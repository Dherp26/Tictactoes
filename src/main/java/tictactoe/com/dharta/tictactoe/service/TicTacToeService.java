package tictactoe.com.dharta.tictactoe.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tictactoe.com.dharta.tictactoe.bean.GameBean;
import tictactoe.com.dharta.tictactoe.bean.UserBean;
import tictactoe.com.dharta.tictactoe.exception.BadRequestException;
import tictactoe.com.dharta.tictactoe.exception.NotFoundException;
import tictactoe.com.dharta.tictactoe.mapper.GameMapper;
import tictactoe.com.dharta.tictactoe.model.Game;
import tictactoe.com.dharta.tictactoe.model.User;
import tictactoe.com.dharta.tictactoe.repository.GameRepository;
import tictactoe.com.dharta.tictactoe.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static tictactoe.com.dharta.tictactoe.constant.Constant.*;
import static tictactoe.com.dharta.tictactoe.mapper.GameMapper.convertBoardToString;

@Service
@RequiredArgsConstructor
public class TicTacToeService {

    private static final Logger log = LoggerFactory.getLogger(TicTacToeService.class);
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public GameBean createGame(UserBean user, long id) {
        GameBean gameBean = new GameBean();
        User users = userRepository.findByIdAndName(id, user.getName())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        gameBean.setGameId(UUID.randomUUID().toString());
        gameBean.setStatus(NEW);

        List<List<Character>> board = createBoard(users.getBoardSize());
        gameBean.setBoard(board);
        Game game = new Game();
        game.setPlayer1(users);
        game.setGameId(game.getGameId());
        game.setBoard(convertBoardToString(gameBean.getBoard()));
        gameRepository.save(game);
        log.debug(SAVE_SUCCESS_MESSAGE, game.getGameId());

        return gameBean;
    }

    private List<List<Character>> createBoard(int size) {
        List<List<Character>> board = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Character> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add('-');
            }
            board.add(row);
        }
        return board;
    }

    public void connectGame(UserBean user, String gameId) {
        Game game = gameRepository.findByGameIdAndPlayer2(gameId, user.getName())
                .orElseThrow(() -> new NotFoundException(GAME_NOT_FOUND));
        User users = userRepository.findById(game.getPlayer2().getId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        if (game.getStatus().equals(NEW) && game.getGameId().equals(gameId)) {
            game.setPlayer2(users);
            game.setStatus(IN_PROGRESS);
        } else {
            game.setStatus(FAILED);
        }
    }

    public String makeMove(String gameId, int row, int col) throws NotFoundException {
        Game game = gameRepository.findByGameId(gameId).orElseThrow(() -> new NotFoundException(GAME_NOT_FOUND));

        if (game.getStatus().equals(FINISHED)) {
            throw new BadRequestException(GAME_OVER);
        }

        List<List<Character>> board = GameMapper.convertStringToBoard(game.getBoard());

        if (board.get(row).get(col) != '-') {
            throw new BadRequestException(GAME_INVALID_MOVE);
        }

        char currentPlayer = game.getStatus().equals(IN_PROGRESS) ? 'X' : 'O';

        board.get(row).set(col, currentPlayer);
        game.setBoard(convertBoardToString(board));

        if (checkWinner(board, row, col, currentPlayer)) {
            game.setWinner(currentPlayer == 'X' ? game.getPlayer1().getName() : game.getPlayer2().getName());
            game.setStatus(FINISHED);
            gameRepository.save(game);
            return "Winner is " + game.getWinner();
        }

        game.setStatus(game.getStatus().equals(IN_PROGRESS) ? "O_TURN" : IN_PROGRESS);
        gameRepository.save(game);
        return "Move has been set, next turn";
    }

    public GameBean getGame(String gameId) throws NotFoundException {
        Game game = gameRepository.findByGameId(gameId).orElseThrow(() -> new NotFoundException(GAME_NOT_FOUND));
        return GameMapper.toGameBean(game);
    }

    private boolean checkWinner(List<List<Character>> board, int row, int col, char symbol) {
        int size = board.size();
        boolean win = true;

        for (int i = 0; i < size; i++) {
            if (board.get(row).get(i) != symbol) {
                win = false;
                break;
            }
        }
        if (win) return true;

        for (int i = 0; i < size; i++) {
            if (board.get(i).get(col) != symbol) {
                win = false;
                break;
            }
        }

        if (row == col) {
            for (int i = 0; i < size; i++) {
                if (board.get(i).get(i) != symbol) {
                    win = false;
                    break;
                }
            }
        }

        if (row + col == size - 1) {
            for (int i = 0; i < size; i++) {
                if (board.get(i).get(size - 1 - i) != symbol) {
                    break;
                }
            }
            return win;
        }

        return false;
    }
}
