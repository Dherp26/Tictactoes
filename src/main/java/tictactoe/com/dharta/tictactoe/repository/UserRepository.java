package tictactoe.com.dharta.tictactoe.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tictactoe.com.dharta.tictactoe.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query(value = "select * from users u where u.id = :id and u.name = :name ", nativeQuery = true)
    Optional<User> findByIdAndName(@Param("id") long gameId, @Param("name") String name);
}
