package ru.az.secu.api;


import org.hibernate.annotations.CreationTimestamp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.az.secu.dto.UserAdminDto;
import ru.az.secu.dto.UserDto;
import ru.az.secu.dto.UserStatisticDto;
import ru.az.secu.model.User;
import ru.az.secu.services.MyException;
import ru.az.secu.services.UserAdminService;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/admin/users")
@PreAuthorize("hasAnyAuthority('admin:read')")
@RestController
public class UserAdminRestController {

    private final UserAdminService userAdminService;

    public UserAdminRestController(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }

    @GetMapping("all")
    public ResponseEntity<List<UserAdminDto>> getUsers() throws MyException {
        return ResponseEntity.ok(
                userAdminService.findAll().stream()
                        .map(UserAdminDto::create).collect(Collectors.toList())
        );
    }

    @GetMapping("count")
    public ResponseEntity<UserStatisticDto> getCount() throws MyException {
        List<User> all = userAdminService.findAll();
        UserStatisticDto userStatisticDto = new UserStatisticDto();
        long sizeAll = all.size();
        userStatisticDto.setCount(sizeAll);
        long sizeActive = all.stream().filter(User::getActive).count();
        userStatisticDto.setCountActive(sizeActive);
        userStatisticDto.setCountNotActive(sizeAll-sizeActive);
        return ResponseEntity.ok(userStatisticDto);
    }

    @GetMapping("delete")
    public HttpStatus deleteById(
            @RequestParam Long id
    ) {
        userAdminService.delete(id);
        return HttpStatus.OK;
    }

    @PostMapping("add")
    public ResponseEntity<UserDto> add(
            @RequestBody UserAdminDto userAdminDto
    ) {
        User userFromDb = userAdminService.add(UserAdminDto.create(userAdminDto));
        return ResponseEntity.ok(UserDto.create(userFromDb));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> findById(
            @PathVariable Long id
    ) throws MyException {
        return ResponseEntity.ok(UserDto.create(userAdminService.findById(id)));
    }

    @PostMapping("update")
    public ResponseEntity<UserDto> update(
            @RequestBody UserDto userDto
    ) throws MyException {
        User userFromDb = userAdminService.update(UserDto.create(userDto));
        return ResponseEntity.ok(UserDto.create(userFromDb));
    }

    @PostMapping("change-active/{id}")
    public ResponseEntity<UserAdminDto> changeActive(
            @PathVariable Long id
    ) throws MyException {
        return ResponseEntity.ok(UserAdminDto.create(userAdminService.changeActive(id)));
    }

}
