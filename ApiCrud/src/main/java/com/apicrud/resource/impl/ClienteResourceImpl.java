package com.apicrud.resource.impl;

import com.apicrud.dto.ClienteRequestDto;
import com.apicrud.dto.ClienteResponseDto;
import com.apicrud.resource.ClienteResource;
import com.apicrud.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResourceImpl implements ClienteResource {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<ClienteResponseDto> findAll(@RequestParam(required = false) String nome) {
        return clienteService.getClientes(nome)
                .parallelStream()
                .map((clienteResponseDto) -> clienteResponseDto.add(linkTo(methodOn(ClienteResourceImpl.class).findById(clienteResponseDto.getId())).withSelfRel()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClienteResponseDto findById(@PathVariable Long id) {
        return clienteService.getCliente(id).add(linkTo(methodOn(ClienteResourceImpl.class).findAll(null)).withRel("Lista de Clientes"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponseDto create(@RequestBody ClienteRequestDto cliente) {
        return clienteService.postCliente(cliente);
    }

    @PutMapping("/{id}")
    public ClienteResponseDto update(@RequestBody ClienteRequestDto novoCliente, @PathVariable Long id) {
        return clienteService.putCliente(novoCliente, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clienteService.deleteCliente(id);
    }
}
