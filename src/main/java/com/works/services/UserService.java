package com.works.services;

import com.works.entities.Role;
import com.works.entities.User;
import com.works.entities.dto.UserLoginDto;
import com.works.entities.dto.UserRegisterDto;
import com.works.repositories.RoleRepository;
import com.works.repositories.UserRepository;
import com.works.utils.Messages;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final HttpServletRequest request;

    private final JWTService jwtService;
    private final AuthenticationConfiguration configuration;

    public User register (UserRegisterDto userRegisterDto){
        User user = modelMapper.map(userRegisterDto, User.class);
        user.setCreated(new Date());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Page<User> findAll(int page, int size) {
        Pageable  pageable = PageRequest.of(page, size);
        Page<User> userList = userRepository.findAll(pageable);
        for (User user : userList) {
            user.setEnabled(true);
        }
        return userList;
    }

    public ResponseEntity singleUser(long uid){
        Optional<User> optionalUser = userRepository.findById(uid);
        if (optionalUser.isPresent()) {
            return new ResponseEntity(optionalUser.get(), HttpStatus.OK);
        }
        String msg = "Kullanıcı bulunamadı";
        return new  ResponseEntity(msg, HttpStatus.BAD_REQUEST);
    }

    public Page<User> userSearch(String q, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        //Page<User> userList = userRepository.findByFirstNameContainsAndLastNameContainsAllIgnoreCase(q, q, pageable);
        Page<User> userList = userRepository.findByFirstNameContainsOrLastNameContainsOrEmailContainsAllIgnoreCase(q, q, q, pageable);
        return  userList;
    }

    public List<User> registerAll(List<UserRegisterDto> userRegisterDto) {
        List<User> userList = new ArrayList<>();
        for (UserRegisterDto userRegisterDto1 : userRegisterDto) {
            User user = modelMapper.map(userRegisterDto1, User.class);
            user.setCreated(new Date());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userList.add(user);
        }
        userRepository.saveAll(userList);

        return userList;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmailEqualsIgnoreCase(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            System.out.println(user);
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    user.getEnabled(),
                    true,
                    true,
                    true,
                    parseRole(user.getRoles())
            );
        }

        throw new UsernameNotFoundException(username + " user not found");
    }

    private Collection<? extends GrantedAuthority> parseRole(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

//    public ResponseEntity login(UserLoginDto userLoginDto) {
//        Optional<User> user = userRepository.findByEmailEqualsIgnoreCase(userLoginDto.getEmail());
//
//        if (user.isPresent()) {
//            user.get().setLastLoginDate(new Date());
//            //userRepository.save(user.get());
//            request.getSession().setAttribute("user", user.get());
//            //System.out.println("session: " + request.getSession().getAttribute("user"));
//
//            return new ResponseEntity(user.get(), HttpStatus.OK);
//        }
//        return new ResponseEntity("Kullanıcı bulunamadı", HttpStatus.BAD_REQUEST);
//    }

    public ResponseEntity login(UserLoginDto userLoginDto) {
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            configuration.getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));
            UserDetails userDetails = loadUserByUsername(userLoginDto.getEmail());
            String jwt = jwtService.generateToken(userDetails);
            map.put("jwt", jwt);
            Optional<User> optionalUser = userRepository.findByEmailEqualsIgnoreCase(userLoginDto.getEmail());
            optionalUser.get().setLastLoginDate(new Date());
            map.put("user", optionalUser.get());
            return new ResponseEntity(map, HttpStatus.OK);
        } catch (Exception e) {
            map.put("status", 401);
            map.put("error", e.getMessage());
            return new ResponseEntity(map, HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity update (User user){

        Optional<User> userOptional = userRepository.findById(user.getUid());
        if (userOptional.isPresent()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User rtUser = userRepository.saveAndFlush(user);
            //rtUser.setPassword(null);
            return new ResponseEntity(rtUser, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


//    public String role(){
//
//        User user = (User) request.getSession().getAttribute("user");
//
////        request.getSession().setAttribute("roles", user.getRoles());
////        Object roles = request.getSession().getAttribute("roles");
////        return roles.toString();
//
//        return user.getRoles().toString();
//    }

    public User user() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmailEqualsIgnoreCase(name);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    public Boolean isAdmin(){

        boolean isAdmin = false;

        if( user().getRoles().stream().anyMatch(role -> role.getName().contains("admin")) ){
            isAdmin = true;
        }

        return isAdmin;
    }

    public Boolean isManager(){

        boolean isManager = false;

        if( user().getRoles().stream().anyMatch(role -> role.getName().contains("manager")) ){
            isManager = true;
        }

        return isManager;
    }

    public ResponseEntity updateRole(long uid, long rid) {
//        System.out.println( user().getRoles());
//        boolean isAdmin = user().getRoles().stream().anyMatch(role -> role.getName().contains("admin"));

//        if(isAdmin()) {
//            User user = userRepository.findById(uid).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
//            Role role = roleRepository.findById(rid).orElseThrow(() -> new UsernameNotFoundException("Role not found"));
//            user.getRoles().clear();
//            user.getRoles().add(role);
//            return new  ResponseEntity(user.getRoles(), HttpStatus.OK);
//        }else
//            return new  ResponseEntity("yetkisiz", HttpStatus.BAD_REQUEST);

        User user = userRepository.findById(uid).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        Role role = roleRepository.findById(rid).orElseThrow(() -> new UsernameNotFoundException("Role not found"));
        user.getRoles().clear();
        user.getRoles().add(role);
        return new  ResponseEntity(user.getRoles(), HttpStatus.OK);
    }

    public ResponseEntity deleteuser(long uid) {
        Optional<User> optionalUser = userRepository.findById(uid);
        User user = new User();
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            userRepository.delete(user);
            return new ResponseEntity( user.getEmail() + " kullanıcısı silindi" , HttpStatus.OK);
        }

        return new ResponseEntity(user.getEmail() + " böyle bir kullanıcı yok", HttpStatus.BAD_REQUEST);
    }
}
