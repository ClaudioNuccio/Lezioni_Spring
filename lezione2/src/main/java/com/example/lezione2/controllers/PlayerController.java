package com.example.lezione2.controllers;

import com.example.lezione2.entities.Player;
import com.example.lezione2.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/player")
public class PlayerController {
    @Autowired
    PlayerService playerService;

    @PostMapping(path = "/create")
    public Player createPlayer(@RequestBody Player player) {
        return playerService.createPlayer(player);
    }

    @GetMapping(path = "/{id}")
    public Optional<Player> getSinglePlayer(@PathVariable Long id) {
        return playerService.findSinglePlayer(id);
    }

    @GetMapping(path = "/players")
    public List<Player> getAllPlayer() {
        return playerService.findAllPlayers();
    }

    @PutMapping(path = "/{id}/update")
    public Player updatePlayer(@PathVariable Long id, @RequestBody Player player) {
        return playerService.updatePlayer(id, player);
    }

    @DeleteMapping(path = "/{id}/delete")
    public Boolean deletePlayer(@PathVariable Long id) {
        return playerService.deletePlayer(id);
    }
}
