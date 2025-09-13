package com.works.configs;

import com.works.entities.TeamUser;
import com.works.repositories.TeamUserRepository;
import com.works.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("teamSecurity")
@RequiredArgsConstructor
public class TeamSecurity {

    private final UserService  userService;
    private final TeamUserRepository teamUserRepository;

    public boolean isManager(Long teamId){
        Long uid = userService.user().getUid();

        if(userService.isManager()){
            Optional<TeamUser> optionalTeamUser = teamUserRepository.findByUser_UidEquals(uid);
            if(optionalTeamUser.isPresent()){
                TeamUser teamUser = optionalTeamUser.get();
                Long tid = teamUser.getTeam().getTid();
                if(tid.equals(teamId)){
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isTeamManager(long id) {

        Long taskUserTeamId = findByTeamId(id);
        Long userTeamId = findByTeamId(userService.user().getUid());
        if(taskUserTeamId.equals(userTeamId)) {
            return true;
        }

        return false;
    }

    public long findByTeamId(Long id) {
        Optional<TeamUser> optionalTeamUser = teamUserRepository.findByUser_UidEquals(id);
        if(optionalTeamUser.isPresent()){
            TeamUser teamUser = optionalTeamUser.get();
            return teamUser.getTeam().getTid();
        }
        return 0;
    }

}
