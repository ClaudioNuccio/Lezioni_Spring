package com.example.lezione2.features.player;

import com.example.lezione2.features.player.dto.CreatePlayerRequest;
import com.example.lezione2.features.player.dto.PlayerResponse;
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
    public PlayerResponse createPlayer(@RequestBody CreatePlayerRequest request) {
        return playerService.createPlayer(request);
    }

    @GetMapping(path = "/{id}")
    public Optional<PlayerEntity> getSinglePlayer(@PathVariable Long id) {
        return playerService.findSinglePlayer(id);
    }

    @GetMapping(path = "/players")
    public List<PlayerEntity> getAllPlayer() {
        return playerService.findAllPlayers();
    }

    @PutMapping(path = "/{id}/update")
    public PlayerEntity updatePlayer(@PathVariable Long id, @RequestBody PlayerEntity playerEntity) {
        return playerService.updatePlayer(id, playerEntity);
    }

    @DeleteMapping(path = "/{id}/delete")
    public Boolean deletePlayer(@PathVariable Long id) {
        return playerService.deletePlayer(id);
    }
}
