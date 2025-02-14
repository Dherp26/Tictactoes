package tictactoe.com.dharta.tictactoe.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tictactoe.com.dharta.tictactoe.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
