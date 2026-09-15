package com.example.backend.dto;

import lombok.*;
import com.example.backend.entity.Service;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse {

    private Integer id;
    private String name;

    public ServiceResponse(Service service) {
        this.id = service.getId();
        this.name = service.getName();
    }
}