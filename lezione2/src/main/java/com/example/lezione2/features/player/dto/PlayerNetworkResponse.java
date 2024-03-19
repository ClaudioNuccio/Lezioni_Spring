package com.example.lezione2.features.player.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public class PlayerNetworkResponse {

    @Data
    @Builder
    public static class Success extends PlayerNetworkResponse {
        PlayerResponse player;
    }
    @Data
    @Builder
    public static class Error extends PlayerNetworkResponse {
        private int code;
        private String description;
    }

    @Data
    @Builder
    public static class SuccessWithList extends PlayerNetworkResponse {
        List<PlayerResponse> players;
    }
}
