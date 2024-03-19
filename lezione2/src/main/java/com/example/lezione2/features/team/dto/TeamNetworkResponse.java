package com.example.lezione2.features.team.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public class TeamNetworkResponse {

    @Data
    @Builder
    public static class Success extends TeamNetworkResponse {
        TeamResponse team;
    }
    @Data
    @Builder
    public static class Error extends TeamNetworkResponse {
        private int code;
        private String description;
    }

    @Data
    @Builder
    public static class SuccessWithList extends TeamNetworkResponse {
        List<TeamResponse> teams;
    }
}
