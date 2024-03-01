package com.example.lezione2.features.team.dto;

import com.example.lezione2.features.player.dto.PlayerResponse;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeamAndPlayerResponse {

    private TeamResponse team;
    private List<PlayerResponse> players;
}
