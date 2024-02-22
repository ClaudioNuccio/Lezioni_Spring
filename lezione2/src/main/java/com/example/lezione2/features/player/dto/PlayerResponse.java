package com.example.lezione2.features.player.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerResponse {

    private Long id;
    private String name;
    private String surname;
    private int number;
    private String dateOfBirth;
}

