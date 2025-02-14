package tictactoe.com.dharta.tictactoe.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String status;
    private int boardSize;

    @OneToMany(mappedBy = "player1")
    private List<Game> player1;

    @OneToMany(mappedBy = "player2")
    private List<Game> player2;
}
