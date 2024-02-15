package com.example.lezione2.controllers;

import com.example.lezione2.entities.Contract;
import com.example.lezione2.entities.Player;
import com.example.lezione2.entities.Team;
import com.example.lezione2.services.ContractService;
import com.example.lezione2.services.PlayerService;
import com.example.lezione2.services.TeamService;
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
    public Team createTeam(@RequestBody Team team) {
        return teamService.createTeam(team);
    }

    @GetMapping(path = "/{id}")
    public Optional<Team> getSingleTeam(@PathVariable Long id) {
        return teamService.findSingleTeam(id);
    }

    @GetMapping(path = "/teams")
    public List<Team> getAllTeam() {
        return teamService.findAllTeams();
    }

    @PutMapping(path = "/{id}/update")
    public Team updateTeam(@PathVariable Long id, @RequestBody Team team) {
        return teamService.updateTeam(id, team);
    }

    @DeleteMapping(path = "/{id}/delete")
    public Boolean deleteTeam(@PathVariable Long id) {
        return teamService.deleteTeam(id);
    }

    @PutMapping(path = "/contract/create")
    public Contract createContract(@RequestParam long idPlayer, @RequestParam long idTeam) {
        return contractService.addPlayerToTeam(idPlayer,idTeam);
    }
}
