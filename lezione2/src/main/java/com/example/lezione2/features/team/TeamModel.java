package com.example.lezione2.features.team;

import com.example.lezione2.features.player.PlayerEntity;
import com.example.lezione2.features.player.PlayerModel;
import com.example.lezione2.features.team.dto.CreateTeamRequest;
import com.example.lezione2.features.team.dto.TeamResponse;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeamModel {
    private Long id;
    private String name;
    private String city;
    private String league;
    public static TeamModel mapRequestToModel(CreateTeamRequest createTeamRequest) {
        return new TeamModel(
                null,
                createTeamRequest.getName(),
                createTeamRequest.getCity(),
                createTeamRequest.getLeague()
        );
    }
    public static TeamEntity mapModelToEntity(TeamModel teamModel) {
        return new TeamEntity(
                teamModel.getId(),
                teamModel.getName(),
                teamModel.getCity(),
                teamModel.getLeague()
        );
    }
    public static TeamModel mapEntityToModel(TeamEntity teamEntity) {
        return new TeamModel(
                teamEntity.getId(),
                teamEntity.getName(),
                teamEntity.getCity(),
                teamEntity.getLeague()
        );
    }


    public static TeamResponse mapModelToResponse(TeamModel teamModel) {
        return new TeamResponse(
                teamModel.getId(),
                teamModel.getName(),
                teamModel.getCity(),
                teamModel.getLeague()
        );
    }
}
