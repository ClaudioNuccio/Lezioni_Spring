package com.example.lezione2.features.team;

import com.example.lezione2.features.contract.ContractEntity;
import com.example.lezione2.features.contract.ContractRepository;
import com.example.lezione2.features.player.PlayerEntity;
import com.example.lezione2.features.player.PlayerRepository;
import com.example.lezione2.features.team.dto.CreateTeamRequest;
import com.example.lezione2.features.team.dto.TeamAndPlayerResponse;
import com.example.lezione2.features.team.dto.TeamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    PlayerRepository playerRepository;


    public TeamResponse createTeam(CreateTeamRequest createTeamRequest) {
        TeamModel teamRequestToModel = TeamModel.mapRequestToModel(createTeamRequest);
        TeamEntity teamModelToEntity = TeamModel.mapModelToEntity(teamRequestToModel);
        TeamModel teamEntityToModel = TeamModel.mapEntityToModel(teamRepository.saveAndFlush(teamModelToEntity));
        return TeamModel.mapModelToResponse(teamEntityToModel);
    }

    public Optional<TeamResponse> findSingleTeam(Long id) {
        Optional<TeamEntity> response = teamRepository.findById(id);
        if (response.isPresent()) {
            return Optional.of(TeamModel.mapModelToResponse(TeamModel.mapEntityToModel(response.get())));
        } else return Optional.empty();
    }

    public List<TeamResponse> findAllTeams() {
        List<TeamEntity> response = teamRepository.findAll();
//        return response.stream().map(teamEntity -> {
//            TeamModel entityToModel = TeamModel.mapEntityToModel(teamEntity);
//            return TeamModel.mapModelToResponse(entityToModel);
//        }).toList();

        List<TeamResponse> result = new ArrayList<>();
        for(TeamEntity teamEntity: response){
            TeamModel entityToModel = TeamModel.mapEntityToModel(teamEntity);
            result.add(TeamModel.mapModelToResponse(entityToModel));
        }
        return result;
    }

    public TeamResponse updateTeam(Long id, CreateTeamRequest createTeamRequest) {
        Optional<TeamEntity> updatedTeam = teamRepository.findById(id);
        if (updatedTeam.isPresent()) {
            updatedTeam.get().setName(createTeamRequest.getName());
            updatedTeam.get().setCity(createTeamRequest.getCity());
            updatedTeam.get().setLeague(createTeamRequest.getLeague());
            TeamModel teamEntityToModel = TeamModel.mapEntityToModel(teamRepository.saveAndFlush(updatedTeam.get()));
            return TeamModel.mapModelToResponse(teamEntityToModel);
        } else {
            return null;
        }
    }

    public Boolean deleteTeam(Long id) {
        if (teamRepository.existsById(id)) {
            teamRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public TeamAndPlayerResponse getTeamAndPlayer(Long teamId) {

        Optional<TeamEntity> teamEntity = teamRepository.findById(teamId);
        List<ContractEntity> teamContracts = contractRepository.getContractsByTeam(teamId);
        List<PlayerEntity> teamPlayers = new ArrayList<>();
        for(int i = 0; i < teamContracts.size(); i++){
            ContractEntity teamContract = teamContracts.get(i);
            PlayerEntity player = teamContract.getPlayerEntity();
            teamPlayers.add(player);
        }
        System.out.println(teamEntity);
        System.out.println(teamPlayers);
        return new TeamAndPlayerResponse();

    }
}
