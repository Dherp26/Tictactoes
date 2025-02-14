package tictactoe.com.dharta.tictactoe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tictactoe.com.dharta.tictactoe.bean.IdBean;
import tictactoe.com.dharta.tictactoe.bean.UserBean;
import tictactoe.com.dharta.tictactoe.exception.BadRequestException;
import tictactoe.com.dharta.tictactoe.model.User;
import tictactoe.com.dharta.tictactoe.repository.UserRepository;

import static tictactoe.com.dharta.tictactoe.constant.Constant.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void saveUser(UserBean userBean) {
        User user = new User();
        user.setName(userBean.getName());
        user.setBoardSize(userBean.getBoardSize());

        userRepository.save(user);
    }

    @Transactional
    public void delete(IdBean idBean) {
        userRepository.findById(idBean.getId()).orElseThrow(
                () -> new BadRequestException(USER_NOT_FOUND));
        userRepository.deleteById(idBean.getId());
    }
}
