package tictactoe.com.dharta.tictactoe.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameBean {
    private String gameId;
    private String player1;
    private String player2;
    private String winner;
    private int score;
    private String status;
    private List<List<Character>> board;
}
