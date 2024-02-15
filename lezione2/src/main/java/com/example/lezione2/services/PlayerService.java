package com.example.lezione2.services;

import com.example.lezione2.entities.Player;
import com.example.lezione2.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    @Autowired
    PlayerRepository playerRepository;

    public Player createPlayer(Player player) {
        return playerRepository.saveAndFlush(player);
    }

    public Optional<Player> findSinglePlayer(Long id) {
        return playerRepository.findById(id);
    }

    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    public Player updatePlayer(Long id, Player player) {
        Optional<Player> updatedPlayer = playerRepository.findById(id);
        if (updatedPlayer.isPresent()) {
            updatedPlayer.get().setName(player.getName());
            updatedPlayer.get().setSurname(player.getSurname());
            updatedPlayer.get().setNumber(player.getNumber());
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
