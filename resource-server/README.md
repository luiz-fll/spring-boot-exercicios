# Autenticação com OAuth 2.0 + OpenID Connect

Você irá implementar um Resource Server em Spring Boot que protege seus endpoints utilizando OAuth 2.0 com OpenID Connect (OIDC).
O sistema não será responsável por autenticar usuários diretamente: em vez disso, ele confia em um Authorization Server externo (ex.: Keycloak, Auth0 ou um mock configurado localmente).

Sua API:

* Não possui tela de login
* Não cria tokens
* Apenas valida Access Tokens JWT emitidos por um Authorization Server compatível com OIDC

## Exemplo

1. O usuário se autentica em um Authorization Server (fora do seu sistema).
2. O Authorization Server retorna um Access Token JWT.
3. O cliente (frontend ou Postman) chama a sua API enviando:

   ```http
   Authorization: Bearer <access_token>
   ```
4. Sua aplicação:

   * Valida o token (assinatura, expiração, issuer)
   * Extrai informações do usuário a partir dos claims
   * Autoriza ou nega acesso aos endpoints

### Endpoint público

**[GET]** `/public`

Não exige autenticação, sempre retorna sucesso.

```http
HTTP/1.1 200 OK
```

```json
{
  "message": "Endpoint público"
}
```

### Endpoint protegido

**[GET]** `/profile`

Exige um Access Token válido.

Exemplo de resposta:

```json
{
  "sub": "a1b2c3d4",
  "email": "usuario@email.com",
  "name": "Usuário Exemplo",
  "issuer": "https://auth-server.example.com"
}
```

Se o token não for enviado ou for inválido:

```http
HTTP/1.1 401 Unauthorized
```

## Requisitos

* Utilizar Spring Security OAuth2 Resource Server
* Configurar validação automática de JWT
* Utilizar OpenID Connect para obter informações do usuário (claims padrão como `sub`, `email`, `name`)

## Solução

* Foi criado um sistema simples que lê os dados de um token JWT emitido por um Authorization Server. O sistema possui
dois endpoints (`/public` e `/profile`) definidos em `ResourceServerController`, e um bean `SecurityFilterChain` definido
em `ResourceServerApplication`. Ele também possui dois DTOs para enviar no corpo das respostas HTTP. O Authorization
Server é um container Docker criado com Keycloak (localizado na pasta `keycloak`).
* Detalhes da configuração do Keycloak:
  * Para iniciar o servidor, abra um terminal na pasta `keycloak` e execute `docker compose up`.
  * O Auth Server está na porta 8080, enquanto que a aplicação Spring Boot está na porta 8081.
  * O servidor possui um realm chamado `exercise`, que contém um usuário:
    * username: `test-user`
    * senha: `123`
  * O servidor está configurado para teste com Postman. Para obter um token válido pelo Postman:
    * Crie uma requisição GET para `localhost:8081/profile`
    * Na aba Authorization, selecione o Type `OAuth 2.0`
    * Na aba Authorization, procure a seção `Configure New Token` e preencha com os seguintes dados:
      * Token name: `(qualquer nome)`
      * Grant Type: `Authorization Code`
      * Callback URL: `https://oauth.pstmn.io/v1/callback`
      * Authorize using browser: `(desmarcado)`
      * Auth URL: `http://localhost:8080/realms/exercise/protocol/openid-connect/auth`
      * Access Token URL: `http://localhost:8080/realms/exercise/protocol/openid-connect/token`
      * Client ID: `resource-server`
      * Client Secret: `(deixe em branco)`
      * Scope: `openid`
      * State: `(deixe em branco)`
      * Client Authentication: `Send as Basic Auth header`
    * Clique no botão `Get New Access Token` e faça o login. O Postman irá receber um token após o login.
    * Envie a requisição GET para `localhost:8081/profile`. A aplicação irá responder com os dados do token corretamente.
* O Spring Security por padrão já válida todos os campos do token por padrão, incluindo data de expiração e issuer.
* Em `application.properties`, há uma propriedade para mudar a porta da aplicação Spring para 8081, e outra contendo a
URI do Authorization Server. Isso é necessário para que a aplicação receba uma chave pública do Auth Server e valide a 
assinatura.
* Em `keycloak`, há um arquivo `docker-compose.yml` e outro `realm-export.json`. Um cria o container Docker com o Keycloak,
o outro configura o servidor Keycloak com o realm preparado para este exercício. 