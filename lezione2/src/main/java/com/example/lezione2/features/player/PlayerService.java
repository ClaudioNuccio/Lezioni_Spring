package com.example.lezione2.features.player;

import com.example.lezione2.features.player.dto.CreatePlayerRequest;
import com.example.lezione2.features.player.dto.PlayerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.lezione2.features.player.PlayerModel.*;

@Service
public class PlayerService {
    @Autowired
    PlayerRepository playerRepository;

    public PlayerResponse createPlayer(CreatePlayerRequest request) {
        PlayerModel playerRequestModel = mapRequestToModel(request);
        PlayerEntity playerRequestEntity = mapModelToEntity(playerRequestModel);
        PlayerEntity savedPlayerEntity = playerRepository.saveAndFlush(playerRequestEntity);
        PlayerModel playerResponseModel = mapEntityToModel(savedPlayerEntity);
        return mapModelToResponse(playerResponseModel);
    }

    public Optional<PlayerEntity> findSinglePlayer(Long id) {
        return playerRepository.findById(id);
    }

    public List<PlayerEntity> findAllPlayers() {
        return playerRepository.findAll();
    }

    public PlayerEntity updatePlayer(Long id, PlayerEntity playerEntity) {
        Optional<PlayerEntity> updatedPlayer = playerRepository.findById(id);
        if (updatedPlayer.isPresent()) {
            updatedPlayer.get().setName(playerEntity.getName());
            updatedPlayer.get().setSurname(playerEntity.getSurname());
            updatedPlayer.get().setNumber(playerEntity.getNumber());
            return updatedPlayer.get();
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
}
