package com.works.restcontrollers;

import com.works.entities.TeamUser;
import com.works.services.TeamUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("teamuser")
@RequiredArgsConstructor
@Validated
public class TeamUserRestController {
    private final TeamUserService teamUserService;

    @PostMapping("save")
    @PreAuthorize("hasRole('admin') or hasRole('manager') and @teamSecurity.isManager(#teamUser.team.tid)")
    public ResponseEntity save(@Valid @RequestBody TeamUser teamUser) {
        return teamUserService.save(teamUser);
    }

    @GetMapping("list")
    public Page<TeamUser> list(@RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "10") Integer size) {
        return teamUserService.list(page, size);
    }

}
