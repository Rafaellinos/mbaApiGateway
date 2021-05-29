package com.apicrud.resource;

import com.apicrud.domain.Cliente;
import com.apicrud.dto.ClienteRequestDto;
import com.apicrud.dto.ClienteResponseDto;

import java.util.List;

public interface ClienteResource {

    public List<ClienteResponseDto> findAll(String nome);

    public ClienteResponseDto findById(Long id);

    public ClienteResponseDto create(ClienteRequestDto cliente);

    public ClienteResponseDto update(ClienteRequestDto novoCliente, Long id);

    public void delete(Long id);
}
