# Trabalho API GATEWAY & Integration

Integrantes:
 - Antonio Augusto Gaspar Merlini - 1904938
 - Edicrei Fagner Marcondes - 1904807
 - Jefferson Marques da Silva - 1904841
 - Melissao de Souza Amélia - 170071
 - Rafael Veloso Lino de Souza - 1905398

### Iniciar:

```bash
docker-compose up -d
```

> Aplicação demora de 5 à 10 minutos pra subir, devido ao build e download de dependências

### Como usar:

Collection do postman disponível nas pasta ./postman.
Realizar o Login da API auth:9092 e passar no header o token para cliente:8081.

Ex:

Login:
```bash
access_token=$(curl --location --request POST 'http://localhost:9092/oauth/token?grant_type=password&username=admin&password=123456' \
--header 'Authorization: Basic Y29kZXJlZjokMmEkMTAkcDlQazBmUU5BUVNlc0k0dnV2S0EwT1phbkREMg==' \
--header 'Content-Type: application/json' | cut -c 18-53)
```

Criar Cliente:
```bash
curl --location --request POST 'http://localhost:8081/clientes' \
--header 'Authorization: Bearer '$(echo $access_token) \
--header 'Content-Type: application/json' \
--data-raw '{
    "nome": "José",
    "dataNascimento": "1989-09-12",
    "email": "jose@email.com",
    "password": "123"
}'
```

Obter Clientes:
```bash
curl --location --request GET 'http://localhost:8081/clientes' \
--header 'Authorization: Bearer '$(echo $access_token)
```