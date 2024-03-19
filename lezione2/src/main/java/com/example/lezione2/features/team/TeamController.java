package com.example.lezione2.features.team;

import com.example.lezione2.features.contract.ContractEntity;
import com.example.lezione2.features.contract.ContractService;
import com.example.lezione2.features.team.dto.CreateTeamRequest;
import com.example.lezione2.features.team.dto.TeamAndPlayerResponse;
import com.example.lezione2.features.team.dto.TeamNetworkResponse;
import com.example.lezione2.features.team.dto.TeamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/team")
public class TeamController {
    @Autowired
    TeamService teamService;
    @Autowired
    ContractService contractService;

    @PostMapping(path = "/create")
    public ResponseEntity<?> createTeam(@RequestBody CreateTeamRequest createTeamRequest) {
        TeamNetworkResponse.Success response = (TeamNetworkResponse.Success) teamService.createTeam(createTeamRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response.getTeam());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getSingleTeam(@PathVariable Long id) {
        TeamNetworkResponse response = teamService.findSingleTeam(id);
        if (response instanceof TeamNetworkResponse.Success) {
            return  ResponseEntity.status(HttpStatus.CREATED).body(((TeamNetworkResponse.Success) response).getTeam());
        } else {
            int code = ((TeamNetworkResponse.Error) response).getCode();
            String description = ((TeamNetworkResponse.Error) response).getDescription();
            return ResponseEntity.status(code).body(description);
        }
    }

    @GetMapping(path = "/teams")
    public ResponseEntity<?> getAllTeam() {
        TeamNetworkResponse response = teamService.findAllTeams();

        if (response instanceof TeamNetworkResponse.SuccessWithList) {
            return  ResponseEntity.status(HttpStatus.OK).body(((TeamNetworkResponse.SuccessWithList) response).getTeams());
        } else {
            int code = ((TeamNetworkResponse.Error) response).getCode();
            String description = ((TeamNetworkResponse.Error) response).getDescription();
            return ResponseEntity.status(code).body(description);
        }
    }

    @PutMapping(path = "/{id}/update")
    public ResponseEntity<?> updateTeam(@PathVariable Long id, @RequestBody CreateTeamRequest createTeamRequest) {
        TeamNetworkResponse response = teamService.updateTeam(id, createTeamRequest);

        if (response instanceof TeamNetworkResponse.Success) {
            return  ResponseEntity.status(HttpStatus.OK).body(((TeamNetworkResponse.Success) response).getTeam());
        } else {
            int code = ((TeamNetworkResponse.Error) response).getCode();
            String description = ((TeamNetworkResponse.Error) response).getDescription();
            return ResponseEntity.status(code).body(description);
        }
    }

    @DeleteMapping(path = "/{id}/delete")
    public Boolean deleteTeam(@PathVariable Long id) {
        return teamService.deleteTeam(id);
    }

    @PutMapping(path = "/contract/create")
    public ContractEntity createContract(@RequestParam long idPlayer, @RequestParam long idTeam) {
        return contractService.addPlayerToTeam(idPlayer,idTeam);
    }
    @GetMapping(path="/{teamId}/teamandplayers")
    public TeamAndPlayerResponse getTeamAndPlayer(@PathVariable Long teamId){
        return teamService.getTeamAndPlayer(teamId);
    }
}
