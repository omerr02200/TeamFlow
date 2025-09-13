package com.works.services;

import com.works.configs.TeamSecurity;
import com.works.entities.TeamUser;
import com.works.repositories.RoleRepository;
import com.works.repositories.TeamUserRepository;
import com.works.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamUserService {
    private final TeamUserRepository teamUserRepository;

    private final UserService userService;

    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    public ResponseEntity save(TeamUser teamUser) {

//        List<User> users = teamUser.getUsers();
//        for(User user : users) {
//            Optional<TeamUser> optionalTeamUser = teamUserRepository.findByUsers_UidEquals(user.getUid());
//            if (optionalTeamUser.isPresent()) {
//                return new ResponseEntity("id: " + user.getUid() + ", bir takımın üyesidir eklenemez", HttpStatus.BAD_REQUEST);
//            }
//        }
//        return new ResponseEntity(teamUserRepository.save(teamUser), HttpStatus.OK);



//        Optional<TeamUser> teamUserOptional = teamUserRepository.findByUser_UidEquals(teamUser.getUser().getUid());
//        if (teamUserOptional.isPresent()) {
//            return new ResponseEntity("id: " + teamUser.getUser().getUid() + ", bir takımın üyesidir eklenemez", HttpStatus.BAD_REQUEST);
//        }



//        if(userService.isAdmin()){
//            return new ResponseEntity(teamUserRepository.save(teamUser), HttpStatus.OK);
//        } else if (userService.isManager()){
//            long uid = userService.user().getUid();
//            Optional<TeamUser> optionalTeamUser = teamUserRepository.findByUser_UidEquals(uid);
//            TeamUser teamUser1;
//            if(optionalTeamUser.isPresent()){
//                teamUser1 = optionalTeamUser.get();
//                Long tid1 = teamUser1.getTeam().getTid();
//                Long tid = teamUser.getTeam().getTid();
//                if(tid1 == tid){
//                    return new ResponseEntity(teamUserRepository.save(teamUser), HttpStatus.OK);
//                }
//            }
//            return new ResponseEntity("Bu takım yetkinizde değil", HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity(Messages.forBidden,  HttpStatus.FORBIDDEN);

        return new  ResponseEntity(teamUserRepository.save(teamUser), HttpStatus.CREATED);

    }

    public Page<TeamUser> list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TeamUser> teamUsers = teamUserRepository.findAll(pageable);
        return teamUsers;
    }
}
