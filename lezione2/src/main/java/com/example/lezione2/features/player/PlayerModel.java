package com.example.lezione2.features.player;

import com.example.lezione2.features.player.dto.CreatePlayerRequest;
import com.example.lezione2.features.player.dto.PlayerResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerModel {

    private Long id;
    private String name;
    private String surname;
    private int number;


    public static PlayerModel mapEntityToModel(PlayerEntity playerEntity){
        return new PlayerModel(
                playerEntity.getId(),
                playerEntity.getName(),
                playerEntity.getSurname(),
                playerEntity.getNumber()
        );
    }


    public static PlayerEntity mapModelToEntity(PlayerModel playerModel){
        return new PlayerEntity(
                playerModel.getId(),
                playerModel.getName(),
                playerModel.getSurname(),
                playerModel.getNumber()
        );
    }


    public static PlayerResponse mapModelToResponse(PlayerModel playerModel){
        return new PlayerResponse(
                playerModel.getId(),
                playerModel.getName(),
                playerModel.getSurname(),
                playerModel.getNumber()
        );
    }



    public static PlayerModel mapRequestToModel(CreatePlayerRequest createPlayerRequest){
        return new PlayerModel(
                null,
                createPlayerRequest.getName(),
                createPlayerRequest.getSurname(),
                createPlayerRequest.getNumber()
        );
    }


}
