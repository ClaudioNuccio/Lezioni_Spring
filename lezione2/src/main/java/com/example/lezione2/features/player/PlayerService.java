package com.example.lezione2.features.player;

import com.example.lezione2.features.player.dto.CreatePlayerRequest;
import com.example.lezione2.features.player.dto.PlayerNetworkResponse;
import com.example.lezione2.features.player.dto.PlayerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.lezione2.features.player.PlayerModel.*;

@Service
public class PlayerService {
    @Autowired
    PlayerRepository playerRepository;

    public PlayerNetworkResponse createPlayer(CreatePlayerRequest request) {
        if (convertDate(request.getDateOfBirth()) == null) {
            return PlayerNetworkResponse.Error.builder().code(600).description("Date of Birth is wrong").build();
            //return new ResponseEntity<>("Date of birth is wrong", HttpStatus.BAD_REQUEST);
        } else {
            PlayerModel playerRequestModel = mapRequestToModel(request);
            PlayerEntity playerRequestEntity = mapModelToEntity(playerRequestModel);
            PlayerEntity savedPlayerEntity = playerRepository.saveAndFlush(playerRequestEntity);
            PlayerModel playerResponseModel = mapEntityToModel(savedPlayerEntity);
            return PlayerNetworkResponse.Success.builder().player(mapModelToResponse(playerResponseModel)).build();
        }
    }

    public PlayerNetworkResponse findSinglePlayer(Long id) {
        Optional<PlayerEntity> response = playerRepository.findById(id);
        if (response.isPresent()) {
            return PlayerNetworkResponse.Success.builder().player(PlayerModel.mapModelToResponse(PlayerModel.mapEntityToModel(response.get()))).build();
        } else {
            return PlayerNetworkResponse.Error.builder().code(530).description("Id is wrong").build();
        }
    }

    public PlayerNetworkResponse findAllPlayers() {
        List<PlayerEntity> response = playerRepository.findAll();

        if(response.isEmpty()){
            return PlayerNetworkResponse.Error.builder().code(530).description("List empty").build();
        } else {
            List<PlayerResponse> result = new ArrayList<>();
            for (PlayerEntity playerEntity : response) {
                PlayerModel entityToModel = PlayerModel.mapEntityToModel(playerEntity);
                result.add(PlayerModel.mapModelToResponse(entityToModel));
            }
            return PlayerNetworkResponse.SuccessWithList.builder().players(result).build();
        }

    }

    public PlayerResponse updatePlayer(Long id, CreatePlayerRequest createPlayerRequest) {
        Optional<PlayerEntity> updatedPlayer = playerRepository.findById(id);
        if (updatedPlayer.isPresent()) {
            updatedPlayer.get().setName(createPlayerRequest.getName());
            updatedPlayer.get().setSurname(createPlayerRequest.getSurname());
            updatedPlayer.get().setNumber(createPlayerRequest.getNumber());
            updatedPlayer.get().setDateOfBirth(convertDate(createPlayerRequest.getDateOfBirth()));
            PlayerModel playerEntityToModel = PlayerModel.mapEntityToModel(playerRepository.saveAndFlush(updatedPlayer.get()));
            return PlayerModel.mapModelToResponse(playerEntityToModel);
        } else {
            return null;
        }
    }

    public Boolean deletePlayer(Long id) {
        if (playerRepository.existsById(id)) {
            playerRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private OffsetDateTime convertDate(String date) {
        try {
            return OffsetDateTime.parse(date);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
