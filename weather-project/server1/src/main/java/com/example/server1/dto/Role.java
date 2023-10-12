package com.example.server1.dto;


import com.example.server1.entity.types.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
    private Long id;
    private RoleName roleName;
}
