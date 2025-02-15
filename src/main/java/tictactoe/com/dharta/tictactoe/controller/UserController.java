package tictactoe.com.dharta.tictactoe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tictactoe.com.dharta.tictactoe.bean.IdBean;
import tictactoe.com.dharta.tictactoe.bean.UserBean;
import tictactoe.com.dharta.tictactoe.service.UserService;

@RestController
@RequestMapping("api/tictactoe/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/create")
    @ResponseBody
    public ResponseEntity<?> createUser(@RequestBody UserBean userBean) {
        userService.saveUser(userBean);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody IdBean id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
