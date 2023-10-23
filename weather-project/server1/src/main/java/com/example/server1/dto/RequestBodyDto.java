package com.example.server1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RequestBodyDto {
    private String user_id;
    private String type;
    private String path;
    private LocationDto body;
}
