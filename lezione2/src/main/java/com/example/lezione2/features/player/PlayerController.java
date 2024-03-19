package com.example.lezione2.features.player;

import com.example.lezione2.features.player.dto.CreatePlayerRequest;
import com.example.lezione2.features.player.dto.PlayerNetworkResponse;
import com.example.lezione2.features.player.dto.PlayerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/player")
public class PlayerController {
    @Autowired
    PlayerService playerService;

    @PostMapping(path = "/create")
    public ResponseEntity<?> createPlayer(@RequestBody CreatePlayerRequest request) {
        PlayerNetworkResponse response = playerService.createPlayer(request);
        if (response instanceof PlayerNetworkResponse.Success) {
            return  ResponseEntity.status(HttpStatus.CREATED).body(((PlayerNetworkResponse.Success) response).getPlayer());
        } else {
            int code = ((PlayerNetworkResponse.Error) response).getCode();
            String description = ((PlayerNetworkResponse.Error) response).getDescription();
            return ResponseEntity.status(code).body(description);
        }

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getSinglePlayer(@PathVariable Long id) {
        PlayerNetworkResponse response = playerService.findSinglePlayer(id);
        if (response instanceof PlayerNetworkResponse.Success) {
            return  ResponseEntity.status(HttpStatus.CREATED).body(((PlayerNetworkResponse.Success) response).getPlayer());
        } else {
            int code = ((PlayerNetworkResponse.Error) response).getCode();
            String description = ((PlayerNetworkResponse.Error) response).getDescription();
            return ResponseEntity.status(code).body(description);
        }
    }

    @GetMapping(path = "/players")
    public PlayerNetworkResponse getAllPlayer() {
        return playerService.findAllPlayers();
    }

    @PutMapping(path = "/{id}/update")
    public PlayerResponse updatePlayer(@PathVariable Long id, @RequestBody CreatePlayerRequest createPlayerRequest) {
        return playerService.updatePlayer(id, createPlayerRequest);
    }

    @DeleteMapping(path = "/{id}/delete")
    public Boolean deletePlayer(@PathVariable Long id) {
        return playerService.deletePlayer(id);
    }
}
