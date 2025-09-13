package com.works.services;

import com.works.entities.Task;
import com.works.entities.Team;
import com.works.entities.User;
import com.works.repositories.TeamRepository;
import com.works.utils.Messages;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    public ResponseEntity save(Team team) {
        return new ResponseEntity(teamRepository.save(team),  HttpStatus.OK);
    }

    public Page<Team> list(int page, @Min(1) int size) {
        Pageable pageable = PageRequest.of(page, size);
        return teamRepository.findAll(pageable);
    }

    public ResponseEntity update(Team team) {
        Optional<Team> optionalTeam = teamRepository.findById(team.getTid());
        if(optionalTeam.isPresent()) {
            Team updatedTeam = optionalTeam.get();
            updatedTeam.setName(team.getName());
            updatedTeam.setDescription(team.getDescription());
            return new ResponseEntity(teamRepository.save(updatedTeam), HttpStatus.OK);
        }
        return new ResponseEntity(Messages.teamNotFound, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity delete(long tid) {
        Optional<Team> optionalTeam = teamRepository.findById(tid);
        if(optionalTeam.isPresent()) {
            teamRepository.deleteById(tid);
            return new ResponseEntity(Messages.deletedTeam, HttpStatus.OK);
        }
        return new ResponseEntity(Messages.teamNotFound, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity singleTeam(Long tid) {
        Optional<Team> team = teamRepository.findById(tid);
        if (team.isPresent()) {
            return new ResponseEntity(team.get(), HttpStatus.OK);
        }
        return new ResponseEntity(Messages.teamNotFound, HttpStatus.BAD_REQUEST);
    }
}
