package com.example.lezione2.features.player;


import com.example.lezione2.features.player.dto.CreatePlayerRequest;
import com.example.lezione2.features.player.dto.PlayerNetworkResponse;
import com.example.lezione2.features.player.dto.PlayerResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
//@TestPropertySource(value = {"classpath:application-test.properties"})
@AutoConfigureMockMvc
class PlayerControllerTest {
    @MockBean
    private PlayerRepository giocatoreRepository;
    //    @InjectMocks
    //    private GiocatoreService giocatoreService;
    @Mock
    private PlayerController giocatoreController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void cretePlayerValidRequestandValidResponse() throws Exception {
        final Long playerId = 1L;
        CreatePlayerRequest requestGioc = Fixtures.playerRequest();

        when(giocatoreRepository.saveAndFlush(Fixtures.playerEntity(requestGioc)))
                .thenReturn(Fixtures.playerEntityWithId(requestGioc, playerId));

        String giocatoreJSON = objectMapper.writeValueAsString(requestGioc);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/player/create")
                        .content(giocatoreJSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        PlayerResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), PlayerResponse.class);

        assertThat(Fixtures.playerResponse(requestGioc, playerId)).isEqualTo(response);
        assertThat(result.getResponse().getStatus()).isEqualTo(201);
    }

    @Test
    void cretePlayerWrongDOB() throws Exception {
        final Long playerId = 1L;
        CreatePlayerRequest requestGioc = Fixtures.playerRequestWithWrongDate();

        String giocatoreJSON = objectMapper.writeValueAsString(requestGioc);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/player/create")
                        .content(giocatoreJSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        assertThat("Date of Birth is wrong").isEqualTo(result.getResponse().getContentAsString());
        assertThat(result.getResponse().getStatus()).isEqualTo(600);
    }

    @Test
    void getSingleWithWrongId() throws Exception {
        final Long id = 965L;

        when(giocatoreRepository.findById(id)).thenReturn(Optional.empty());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/player/{id}", id))
                .andDo(print())
                .andReturn();
        assertThat("Id is wrong").isEqualTo(result.getResponse().getContentAsString());
        assertThat(result.getResponse().getStatus()).isEqualTo(530);
    }

    @Test
    void getAllPlayersReturnOneElement() throws Exception {
        final Long playerId = 1L;

        CreatePlayerRequest requestGioc = Fixtures.playerRequest();
        when(giocatoreRepository.findAll()).thenReturn(List.of(Fixtures.playerEntityWithId(requestGioc, playerId)));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/player/players"))
                .andDo(print())
                .andReturn();

        List<PlayerResponse> response = objectMapper.readerForListOf(PlayerResponse.class).withRootName("players")
                .readValue(result.getResponse().getContentAsString());

        assertThat(response.size()).isEqualTo(1);
        assertThat(Fixtures.playerResponse(requestGioc, playerId)).isEqualTo(response.get(0));
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void getAllPlayerWrongSituation() throws Exception{
        when(giocatoreRepository.findAll()).thenReturn(Collections.emptyList());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/player/players"))
                .andDo(print())
                .andReturn();


        PlayerNetworkResponse.Error response = objectMapper.readValue(result.getResponse().getContentAsString(), PlayerNetworkResponse.Error.class);


        assertThat("List empty").isEqualTo(response.getDescription());
        assertThat(530).isEqualTo(response.getCode());
    }
}
