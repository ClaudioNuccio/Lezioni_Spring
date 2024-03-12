package com.example.lezione2.features.player;

import com.example.lezione2.features.player.dto.CreatePlayerRequest;
import com.example.lezione2.features.player.dto.PlayerResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;
    @InjectMocks
    private PlayerService playerService;

    @Test
    void testCreatePlayerWithDateError() {
        CreatePlayerRequest playerRequestWithWrongData = Fixtures.playerRequestWithWrongDate();
        ResponseEntity<?> result = playerService.createPlayer(playerRequestWithWrongData);
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode().value()).isEqualTo(600);
        assertThat(result.getBody()).isEqualTo("Date of birth is wrong");
    }

    @Test
    void testCreatePlayerWithCorrectValues(){
        CreatePlayerRequest playerRequestWithCorrectValues = Fixtures.playerRequest();
        when(playerRepository.saveAndFlush(Fixtures.playerEntity(playerRequestWithCorrectValues)))
                .thenReturn(Fixtures.playerEntityWithId(playerRequestWithCorrectValues));
        ResponseEntity<?> result = playerService.createPlayer(playerRequestWithCorrectValues);
        PlayerResponse response = (PlayerResponse)result.getBody();

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Manuel");


    }


}