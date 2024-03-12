package com.example.lezione2.features.player;

import com.example.lezione2.features.player.dto.CreatePlayerRequest;
import com.example.lezione2.features.player.dto.NetworkResponse;
import com.example.lezione2.features.player.dto.PlayerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public NetworkResponse createPlayer(CreatePlayerRequest request) {
        if (convertDate(request.getDateOfBirth()) == null) {
            return NetworkResponse.Error.builder().code(600).description("Date of Birth is wrong").build();
            //return new ResponseEntity<>("Date of birth is wrong", HttpStatus.BAD_REQUEST);
        } else {
            PlayerModel playerRequestModel = mapRequestToModel(request);
            PlayerEntity playerRequestEntity = mapModelToEntity(playerRequestModel);
            PlayerEntity savedPlayerEntity = playerRepository.saveAndFlush(playerRequestEntity);
            PlayerModel playerResponseModel = mapEntityToModel(savedPlayerEntity);
            return NetworkResponse.Success.builder().playerResponse(mapModelToResponse(playerResponseModel)).build();
        }
    }

    public Optional<PlayerResponse> findSinglePlayer(Long id) {
        Optional<PlayerEntity> response = playerRepository.findById(id);
        if (response.isPresent()) {
            return Optional.of(PlayerModel.mapModelToResponse(PlayerModel.mapEntityToModel(response.get())));
        } else return Optional.empty();
    }

    public List<PlayerResponse> findAllPlayers() {
        List<PlayerEntity> response = playerRepository.findAll();
        List<PlayerResponse> result = new ArrayList<>();
        for (PlayerEntity playerEntity : response) {
            PlayerModel entityToModel = PlayerModel.mapEntityToModel(playerEntity);
            result.add(PlayerModel.mapModelToResponse(entityToModel));
        }
        return result;
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
            OffsetDateTime dateofBirth = OffsetDateTime.parse(date);
            return dateofBirth;
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
