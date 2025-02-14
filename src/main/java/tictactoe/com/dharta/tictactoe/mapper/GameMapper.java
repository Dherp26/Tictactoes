package tictactoe.com.dharta.tictactoe.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import tictactoe.com.dharta.tictactoe.bean.GameBean;
import tictactoe.com.dharta.tictactoe.model.Game;

import java.util.ArrayList;
import java.util.List;

public class GameMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static GameBean toGameBean(Game game) {
        GameBean gameBean = new GameBean();
        gameBean.setGameId(game.getGameId());
        gameBean.setPlayer1(game.getPlayer1().getName());
        gameBean.setPlayer2(game.getPlayer2().getName());
        gameBean.setWinner(game.getWinner());
        gameBean.setScore(game.getScore());
        gameBean.setStatus(game.getStatus());
        gameBean.setBoard(convertStringToBoard(game.getBoard()));
        return gameBean;
    }

    public static Game toGameEntity(GameBean gameBean) {
        Game game = new Game();
        game.setGameId(gameBean.getGameId());
        gameBean.setPlayer1(game.getPlayer1().getName());
        gameBean.setPlayer2(game.getPlayer2().getName());
        game.setWinner(gameBean.getWinner());
        game.setScore(gameBean.getScore());
        game.setStatus(gameBean.getStatus());
        game.setBoard(convertBoardToString(gameBean.getBoard()));
        return game;
    }
    
    public static List<List<Character>> convertStringToBoard(String boardString) {
        if (boardString == null || boardString.isEmpty()) return new ArrayList<>();
        try {
            return objectMapper.readValue(boardString, List.class);
        } catch (JsonProcessingException e) {
            return new ArrayList<>();
        }
    }

    public static String convertBoardToString(List<List<Character>> boardList) {
        try {
            return objectMapper.writeValueAsString(boardList);
        } catch (JsonProcessingException e) {
            return "[]";
        }
    }
}
