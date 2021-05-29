package com.apicrud.service;

import com.apicrud.domain.Cliente;
import com.apicrud.dto.ClienteRequestDto;
import com.apicrud.dto.ClienteResponseDto;
import com.apicrud.dto.TokenDto;
import com.apicrud.dto.UserDto;
import com.apicrud.repository.AuthorizationServerClient;
import com.apicrud.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private AuthorizationServerClient authorizationServerClient;

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente save(Cliente cliente) {
        var novoCliente = clienteRepository.save(cliente);
        authorizationServerClient.create(getHeaderMap(), new UserDto(
                cliente.getEmail(), cliente.getPassword(), cliente.getEmail()
        ));
        return novoCliente;
    }

    public Map<String, String> getHeaderMap(){
        TokenDto tokenDto = this.authorizationServerClient.getToken(getTokenHeader(), "admin", "123456");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + tokenDto.getAccess_token());
        return headers;
    }

    public Map<String, String> getTokenHeader(){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Authorization", "Basic Y29kZXJlZjokMmEkMTAkcDlQazBmUU5BUVNlc0k0dnV2S0EwT1phbkREMg==");
        return headers;
    }

    public static ClienteResponseDto mapClienteToResposeDto(Cliente cliente) {
        return ClienteResponseDto.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .email(cliente.getEmail())
                .dataNascimento(cliente.getDataNascimento())
                .build();
    }

    public ClienteResponseDto getCliente(Long id) {
        return mapClienteToResposeDto(clienteRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public List<ClienteResponseDto> getClientes(String nome) {
        List<Cliente> clientes;
        if (!Objects.isNull(nome)) {
            clientes = clienteRepository.findByNomeIgnoreCaseContaining(nome);
        } else {
            clientes = clienteRepository.findAll();
        }
        return clientes
                .stream()
                .map(ClienteService::mapClienteToResposeDto)
                .collect(Collectors.toList());
    }

    public ClienteResponseDto postCliente(ClienteRequestDto clienteRequestDto) {
        var cliente = clienteRepository.save(mapClienteRequestDtoToCliente(clienteRequestDto));
        return mapClienteToResposeDto(cliente);
    }

    public Cliente mapClienteRequestDtoToCliente(ClienteRequestDto clienteRequestDto) {
        return Cliente.builder()
                .nome(clienteRequestDto.getNome())
                .dataNascimento(clienteRequestDto.getDataNascimento())
                .email(clienteRequestDto.getEmail())
                .password(clienteRequestDto.getPassword()).build();
    }

    public ClienteResponseDto putCliente(ClienteRequestDto clienteRequestDto, Long id) {
        return clienteRepository.findById(id)
                .map(ClienteService::mapClienteToResposeDto)
                .orElseGet(() -> postCliente(clienteRequestDto));
    }

    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }

}
