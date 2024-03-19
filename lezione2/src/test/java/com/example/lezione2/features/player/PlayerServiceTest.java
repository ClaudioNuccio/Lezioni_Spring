package com.example.lezione2.features.player;

import com.example.lezione2.features.player.dto.CreatePlayerRequest;
import com.example.lezione2.features.player.dto.PlayerNetworkResponse;
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
        PlayerNetworkResponse result = playerService.createPlayer(playerRequestWithWrongData);
        if (result instanceof PlayerNetworkResponse.Error) {
            assertThat(result).isNotNull();
            assertThat(((PlayerNetworkResponse.Error) result).getCode()).isEqualTo(600L);
            assertThat(((PlayerNetworkResponse.Error) result).getDescription()).isEqualTo("Date of Birth is wrong");
        }

    }

    @Test
    void testCreatePlayerWithMalformedDate() {
        CreatePlayerRequest playerRequestWithMalformedDate = Fixtures.playerRequestWithMalformedDate();
        PlayerNetworkResponse result = playerService.createPlayer(playerRequestWithMalformedDate);
        if (result instanceof PlayerNetworkResponse.Error) {
            assertThat(result).isNotNull();
            assertThat(((PlayerNetworkResponse.Error) result).getCode()).isEqualTo(600L);
            assertThat(((PlayerNetworkResponse.Error) result).getDescription()).isEqualTo("Date of Birth is wrong");
        }

    }

    @Test
    void testCreatePlayerWithDateMissingTime() {
        CreatePlayerRequest playerRequestWithoutTime = Fixtures.playerRequestWithoutTime();
        PlayerNetworkResponse result = playerService.createPlayer(playerRequestWithoutTime);
        if (result instanceof PlayerNetworkResponse.Error) {
            assertThat(result).isNotNull();
            assertThat(((PlayerNetworkResponse.Error) result).getCode()).isEqualTo(600L);
            assertThat(((PlayerNetworkResponse.Error) result).getDescription()).isEqualTo("Date of Birth is wrong");
        }

    }

    @Test
    void testCreatePlayerWithCorrectValues() {
        CreatePlayerRequest playerRequestWithCorrectValues = Fixtures.playerRequest();
        final Long playerId = 1L;

        when(playerRepository.saveAndFlush(Fixtures.playerEntity(playerRequestWithCorrectValues)))
                .thenReturn(Fixtures.playerEntityWithId(playerRequestWithCorrectValues, playerId));

        PlayerNetworkResponse result = playerService.createPlayer(playerRequestWithCorrectValues);
        if (result instanceof PlayerNetworkResponse.Success) {
            assertThat(result).isNotNull();
            assertThat(((PlayerNetworkResponse.Success) result).getPlayer().getId()).isEqualTo(playerId);
            assertThat(((PlayerNetworkResponse.Success) result).getPlayer().getName()).isEqualTo("Manuel");
        }

    }


}