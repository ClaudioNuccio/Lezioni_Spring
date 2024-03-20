package com.example.lezione2.features.player.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonRootName(value = "players")
public class PlayerResponse {

    private Long id;
    private String name;
    private String surname;
    private int number;
    private String dateOfBirth;
}

