package com.example.lezione2.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@jakarta.persistence.Entity
@jakarta.persistence.Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private int number;
}
