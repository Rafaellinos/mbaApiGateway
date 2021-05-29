package com.apicrud.repository;

import com.apicrud.dto.TokenDto;
import com.apicrud.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(url="http://localhost:9092", name = "authorization-server")
public interface AuthorizationServerClient {

    @PostMapping("/oauth/token?grant_type=password&username={username}&password={password}")
    TokenDto getToken(@RequestHeader Map<String, String> headerMap, @PathVariable String username, @PathVariable String password);


    @PostMapping("/user/save")
    UserDto create(@RequestHeader Map<String, String> headerMap, @RequestBody UserDto userDto);
}
