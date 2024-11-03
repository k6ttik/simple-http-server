package tjv.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tjv.server.converter.UserConverter;
import tjv.server.dto.UserDto;
import tjv.server.entity.User;
import tjv.server.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    private final UserConverter userConverter;
    @Autowired
    public UserController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @PostMapping()
    public UserDto create(@RequestBody UserDto userDto) {
        try {
            return userConverter.toDto(
              userService.create(userConverter.toEntity(userDto))
            );
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

    }

    @PutMapping("{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UserDto userDto) {
        try{
            return userConverter.toDto(userService.update(id, userDto));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping()
    public List<UserDto> getAll() {
        List<User> list = new ArrayList<>();
        for (User user : userService.findAll()) {
            list.add(user);
        }
        return userConverter.toDtoList(list);
    }




}
