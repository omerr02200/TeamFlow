package com.works.restcontrollers;

import com.works.entities.Team;
import com.works.services.TeamService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("team")
@Validated
public class TeamRestController {
    private final TeamService teamService;

    @PostMapping("save")
    public ResponseEntity save(@Valid @RequestBody Team team) {
        return teamService.save(team);
    }

    @GetMapping("list")
    public Page<Team> list(@RequestParam(defaultValue = "0") int page,
                           @Min(1) @RequestParam(defaultValue = "10") int size) {
        return teamService.list(page, size);
    }

    @PutMapping("update")
    public ResponseEntity update(@Valid @RequestBody Team team) {
        return teamService.update(team);
    }

    @DeleteMapping("delete/{tid}")
    public ResponseEntity delete(@PathVariable long tid) {
        return teamService.delete(tid);
    }

    @GetMapping("single")
    public ResponseEntity single(@RequestParam Long tid) {
        return teamService.singleTeam(tid);
    }
}
