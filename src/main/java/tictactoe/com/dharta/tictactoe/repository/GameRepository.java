package tictactoe.com.dharta.tictactoe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tictactoe.com.dharta.tictactoe.model.Game;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {

    Optional<Game> findByGameId(String gameId);

    @Query(value = "select * from games g where g.game_id = :gameId and g.player_2 = :player2 and ", nativeQuery = true)
    Optional<Game> findByGameIdAndPlayer2(String gameId, String player2);

}
