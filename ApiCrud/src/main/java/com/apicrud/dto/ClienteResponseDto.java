package com.apicrud.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;

@Builder
@Data
public class ClienteResponseDto extends RepresentationModel<ClienteResponseDto> implements Serializable {

    private Long id;
    private String nome;
    private Date dataNascimento;
    private String email;

}
