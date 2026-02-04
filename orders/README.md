# Autenticação JWT para API de Pedidos

Você está implementando uma API de pedidos internos para uma empresa pequena.
Funcionários precisam se autenticar através de JWT para acessar pedidos.

## Exemplo

### Login

**[POST]** `/auth/login`

Recebe credenciais e retorna um JWT válido.

#### Request

```json
{
  "username": "admin",
  "password": "123456"
}
```

#### Response (sucesso)

```
HTTP/1.1 200 OK
```

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

#### Response (erro)

```
HTTP/1.1 401 Unauthorized
```

```json
{
  "error": "Invalid credentials"
}
```

### Listar pedidos (endpoint protegido)

**[GET]** `/orders`

#### Header

```
Authorization: Bearer <token>
```

#### Response (sucesso)

```
HTTP/1.1 200 OK
```

```json
[
  {
    "id": 1,
    "product": "Notebook",
    "price": 4500
  },
  {
    "id": 2,
    "product": "Mouse",
    "price": 150
  }
]
```

#### Response (sem token ou token inválido)

```
HTTP/1.1 401 Unauthorized
```

## Requisitos

* Use JWT assinado (HMAC SHA-256). O token deve conter:
  * `subject` (username)
  * `issuedAt`
  * `expiration` (ex: 15 minutos)
* A secret deve ficar em `application.properties`.
* Configurar o `SecurityFilterChain` para:
  * Permitir `/auth/login`
  * Proteger `/orders`
  * Desativar sessão (`STATELESS`)
  * Desativar CSRF

## Solução

* Foi criado um sistema que utiliza tokens JWT para autenticação como exigido. Ele utiliza HyperSQL como banco de dados.
* No package `dto`, há todas as DTOs necessárias para modelar o corpo de requisições e respostas HTTP como exigido.
* Em `persistence`, há classes de Entity e Repository, e também um `DataIntializer`. O banco possui uma tabela de usuários
e outra de pedidos, então há entidades e repositórios para ambas.
* Em `security`, há classes relacionadas à configuração do Spring Secuirty. `JwtService` utiliza a biblioteca JJWT para
gerar tokens e validação/leitura do payload. `JwtAuthenticationFilter` é um filtro personalizado para ser colocado na
`SecurityFilterChain` definida em `SecurityConfig`. Esse filtro é responsável por validar o token e criar o
`SecurityContext` com um `Authentication`. O contexto nunca é restaurado de uma sessão, ele é criado em cada requisição
HTTP. `LoginService` verifica se os dados passados em `/auth/login` constam no banco de dados e emite tokens.
* `OrdersController` possui um endpoint para `/auth/login` e outro para `/orders`. Diferente do `formLogin`, a 
requisição efetivamente passa pelo controller e pelo `LoginService` para que o processo de login ocorra.
* `/auth/login` é de acesso público, enquanto que `/orders` é protegido. Isso é definido em `SecurityConfig`.
* `OrdersService` é um serviço que usa `OrdersRepository` para retornar a lista de pedidos do usuário autenticado. O 
controller recupera o `username` do usuário autenticado através da anotação `@AuthenticationPrincipal`.
* `application.properties` guarda a chave secreta e o tempo de expiração do token (15 minutos). A chave serve tanto para
assinar tokens novos quanto para validar tokens assinados por ela.