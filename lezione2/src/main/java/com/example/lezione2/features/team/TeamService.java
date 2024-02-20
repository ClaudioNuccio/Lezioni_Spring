package com.example.lezione2.features.team;

import com.example.lezione2.features.team.Team;
import com.example.lezione2.features.team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    TeamRepository teamRepository;

    public Team createTeam(Team team) {
        return teamRepository.saveAndFlush(team);
    }

    public Optional<Team> findSingleTeam(Long id) {
        return teamRepository.findById(id);
    }

    public List<Team> findAllTeams() {
        return teamRepository.findAll();
    }

    public Team updateTeam(Long id, Team team) {
        Optional<Team> updatedTeam = teamRepository.findById(id);
        if (updatedTeam.isPresent()) {
            updatedTeam.get().setName(team.getName());
            updatedTeam.get().setCity(team.getCity());
            updatedTeam.get().setLeague(team.getLeague());
            return updatedTeam.get();
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
