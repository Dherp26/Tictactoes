package tictactoe.com.dharta.tictactoe.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBean {
    private String name;
    private String status;
    private int boardSize;
}
