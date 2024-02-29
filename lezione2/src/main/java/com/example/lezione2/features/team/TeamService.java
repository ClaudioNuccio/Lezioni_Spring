package com.example.lezione2.features.team;

import com.example.lezione2.features.team.dto.CreateTeamRequest;
import com.example.lezione2.features.team.dto.TeamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    TeamRepository teamRepository;


    public TeamResponse createTeam(CreateTeamRequest createTeamRequest) {
        TeamModel teamRequestToModel = TeamModel.mapRequestToModel(createTeamRequest);
        TeamEntity teamModelToEntity = TeamModel.mapModelToEntity(teamRequestToModel);
        TeamModel teamEntityToModel = TeamModel.mapEntityToModel(teamRepository.saveAndFlush(teamModelToEntity));
        return TeamModel.mapModelToResponse(teamEntityToModel);
    }

    public Optional<TeamEntity> findSingleTeam(Long id) {
        return teamRepository.findById(id);
    }

    public List<TeamEntity> findAllTeams() {
        return teamRepository.findAll();
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
}
