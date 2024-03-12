package com.example.lezione2.features.player;

import com.example.lezione2.features.player.dto.CreatePlayerRequest;

import static com.example.lezione2.features.player.PlayerModel.mapModelToEntity;
import static com.example.lezione2.features.player.PlayerModel.mapRequestToModel;

public class Fixtures {

    public static CreatePlayerRequest playerRequestWithWrongDate(){
        return CreatePlayerRequest.builder()
                .name("Manuel")
                .surname("Basso")
                .number(22)
                .dateOfBirth("20220900:22222222")
                .build();
    }
    public static CreatePlayerRequest playerRequestWithMalformedDate(){
        return CreatePlayerRequest.builder()
                .name("Manuel")
                .surname("Basso")
                .number(22)
                .dateOfBirth("2002-35-15T20:00:00Z")
                .build();
    }
    public static CreatePlayerRequest playerRequestWithoutTime(){
        return CreatePlayerRequest.builder()
                .name("Manuel")
                .surname("Basso")
                .number(22)
                .dateOfBirth("2002-35-15")
                .build();
    }

    public static CreatePlayerRequest playerRequest(){
        return CreatePlayerRequest.builder()
                .name("Manuel")
                .surname("Basso")
                .number(22)
                .dateOfBirth("1989-03-01T20:00:00Z")
                .build();
    }

    public static PlayerEntity playerEntity(CreatePlayerRequest playerRequest){
        PlayerModel playerRequestModel = mapRequestToModel(playerRequest);
        return mapModelToEntity(playerRequestModel);
    }

    public static PlayerEntity playerEntityWithId(CreatePlayerRequest playerRequest){
        PlayerModel playerRequestModel = mapRequestToModel(playerRequest);
        PlayerEntity playerEntity = mapModelToEntity(playerRequestModel);
        playerEntity.setId(1L);
        return playerEntity;
    }


}
