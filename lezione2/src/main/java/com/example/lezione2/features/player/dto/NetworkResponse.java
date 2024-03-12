package com.example.lezione2.features.player.dto;

import lombok.Builder;
import lombok.Data;

public class NetworkResponse{

    @Data
    @Builder
    public static class Success extends NetworkResponse{
        PlayerResponse playerResponse;
    }
    @Data
    @Builder
    public static class Error extends NetworkResponse{
        private int code;
        private String description;
    }
}
