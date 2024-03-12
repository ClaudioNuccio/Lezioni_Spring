package com.example.lezione2.features.player;

import com.example.lezione2.features.player.dto.CreatePlayerRequest;
import com.example.lezione2.features.player.dto.NetworkResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

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
        NetworkResponse result = playerService.createPlayer(playerRequestWithWrongData);
        if (result instanceof NetworkResponse.Error) {
            assertThat(result).isNotNull();
            assertThat(((NetworkResponse.Error) result).getCode()).isEqualTo(600L);
            assertThat(((NetworkResponse.Error) result).getDescription()).isEqualTo("Date of Birth is wrong");
        }

    }
    @Test
    void testCreatePlayerWithMalformedDate() {
        CreatePlayerRequest playerRequestWithMalformedDate = Fixtures.playerRequestWithMalformedDate();
        NetworkResponse result = playerService.createPlayer(playerRequestWithMalformedDate);
        if (result instanceof NetworkResponse.Error) {
            assertThat(result).isNotNull();
            assertThat(((NetworkResponse.Error) result).getCode()).isEqualTo(600L);
            assertThat(((NetworkResponse.Error) result).getDescription()).isEqualTo("Date of Birth is wrong");
        }

    }
    @Test
    void testCreatePlayerWithDateMissingTime() {
        CreatePlayerRequest playerRequestWithoutTime = Fixtures.playerRequestWithoutTime();
        NetworkResponse result = playerService.createPlayer(playerRequestWithoutTime);
        if (result instanceof NetworkResponse.Error) {
            assertThat(result).isNotNull();
            assertThat(((NetworkResponse.Error) result).getCode()).isEqualTo(600L);
            assertThat(((NetworkResponse.Error) result).getDescription()).isEqualTo("Date of Birth is wrong");
        }

    }

    @Test
    void testCreatePlayerWithCorrectValues() {
        CreatePlayerRequest playerRequestWithCorrectValues = Fixtures.playerRequest();
        when(playerRepository.saveAndFlush(Fixtures.playerEntity(playerRequestWithCorrectValues)))
                .thenReturn(Fixtures.playerEntityWithId(playerRequestWithCorrectValues));
        NetworkResponse result = playerService.createPlayer(playerRequestWithCorrectValues);
        if (result instanceof NetworkResponse.Success) {
            assertThat(result).isNotNull();
            assertThat(((NetworkResponse.Success) result).getPlayerResponse().getId()).isEqualTo(1L);
            assertThat(((NetworkResponse.Success) result).getPlayerResponse().getName()).isEqualTo("Manuel");
        }

    }


}