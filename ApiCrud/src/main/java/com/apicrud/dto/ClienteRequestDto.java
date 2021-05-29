package com.apicrud.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class ClienteRequestDto {

    private String nome;
    private Date dataNascimento;
    private String email;
    private String password;

}
