package com.works.restcontrollers;

import com.works.entities.Role;
import com.works.entities.User;
import com.works.entities.dto.UserLoginDto;
import com.works.entities.dto.UserRegisterDto;
import com.works.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Validated
public class UserRestController {
    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        try {
            return new ResponseEntity(userService.register(userRegisterDto), HttpStatus.OK);
        }catch (Exception e) {
            String errorMsg = e.getMessage();
            if(errorMsg.contains("tekrar eden kayıt")) {
                errorMsg = "Email zaten var ";
            }
            return new ResponseEntity(errorMsg + userRegisterDto.getEmail(), HttpStatus.BAD_REQUEST);
        }

//        return new ResponseEntity(userService.register(userRegisterDto), HttpStatus.OK);
    }

    @GetMapping("list")
    @PreAuthorize("hasRole('admin')")
    public Page<User> list(@RequestParam(defaultValue = "0") int page,
                           @Min(5) @Max(10) @RequestParam(defaultValue = "10") int size) {
        return userService.findAll(page, size);
    }

    @GetMapping("single")
    public ResponseEntity single(@Min(1) @RequestParam(defaultValue = "1") Long uid) {
        return userService.singleUser(uid);
    }

    @GetMapping("search")
    public Page<User> search(@RequestParam(defaultValue = "") String q,
                             @RequestParam(defaultValue = "0") int page,
                                 @Min(5) @Max(10) @RequestParam(defaultValue = "10") int size) {
        return userService.userSearch(q, page, size);
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody UserLoginDto userLoginDto) {
        return userService.login(userLoginDto);
    }

    @PostMapping("registerAll")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity registerAll(@Valid @RequestBody List<UserRegisterDto> userRegisterDto) {
       return new ResponseEntity<>(userService.registerAll(userRegisterDto), HttpStatus.OK);
    }

    @PutMapping("update")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity update(@Valid @RequestBody User user) {
        try {
            return new ResponseEntity(userService.update(user), HttpStatus.OK);
        }catch (Exception e) {
            String errorMsg = e.getMessage();
            if(errorMsg.contains("tekrar eden kayıt")) {
                errorMsg = "Email zaten var ";
            }
            return new ResponseEntity(errorMsg + user.getEmail(), HttpStatus.BAD_REQUEST);
        }

//        return new ResponseEntity(userService.register(userRegisterDto), HttpStatus.OK);
    }

    @PutMapping("updateRole")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity updateRole (@RequestParam long uid, @RequestParam long rid) {
        return userService.updateRole(uid, rid);
    }

    @DeleteMapping("delete/{uid}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity deleteUser(@PathVariable long uid) {
        return userService.deleteuser(uid);
    }
}