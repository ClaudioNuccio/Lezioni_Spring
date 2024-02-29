package com.example.lezione2.features.team;

import com.example.lezione2.features.contract.ContractEntity;
import com.example.lezione2.features.contract.ContractService;
import com.example.lezione2.features.team.dto.CreateTeamRequest;
import com.example.lezione2.features.team.dto.TeamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/team")
public class TeamController {
    @Autowired
    TeamService teamService;
    @Autowired
    ContractService contractService;

    @PostMapping(path = "/create")
    public TeamResponse createTeam(@RequestBody CreateTeamRequest createTeamRequest) {
        return teamService.createTeam(createTeamRequest);
    }

    @GetMapping(path = "/{id}")
    public Optional<TeamResponse> getSingleTeam(@PathVariable Long id) {
        return teamService.findSingleTeam(id);
    }

    @GetMapping(path = "/teams")
    public List<TeamResponse> getAllTeam() {
        return teamService.findAllTeams();
    }

    @PutMapping(path = "/{id}/update")
    public TeamResponse updateTeam(@PathVariable Long id, @RequestBody CreateTeamRequest createTeamRequest) {
        return teamService.updateTeam(id, createTeamRequest);
    }

    @DeleteMapping(path = "/{id}/delete")
    public Boolean deleteTeam(@PathVariable Long id) {
        return teamService.deleteTeam(id);
    }

    @PutMapping(path = "/contract/create")
    public ContractEntity createContract(@RequestParam long idPlayer, @RequestParam long idTeam) {
        return contractService.addPlayerToTeam(idPlayer,idTeam);
    }
//    @GetMapping(path="/{id}/teamandplayers")
//    public
}
