package com.example.lezione2.features.contract;

import com.example.lezione2.features.player.PlayerEntity;
import com.example.lezione2.features.team.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "id_player")
    private PlayerEntity playerEntity;

    @OneToOne
    @JoinColumn(name = "id_team")
    private Team team;

}
