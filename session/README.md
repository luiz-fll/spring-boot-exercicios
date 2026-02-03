# Login de Usuário com Sessão

Você vai implementar um sistema simples de login de usuários para uma aplicação fictícia. 
O sistema possui usuários cadastrados no banco de dados.
O usuário faz login com email e senha.
Após o login, ele pode acessar uma rota protegida que retorna os seus próprios dados.

## Exemplo

O seu sistema deve ter um endpoint que recebe dados de login:

**[POST]** `/login`

```json
{
  "email": "user@test.com",
  "password": "123456"
}
```

O sistema deve consultar o banco de dados e criar a sessão. Endpoints adicionais devem ser criados para
verificar se a sessão está funcionando corretamente:

**[GET]** `/public`

Resposta:
````
HTTP/1.1 200 OK
````
```json
{
  "message": "public endpoint"
}
```
Este endpoint é sempre acessível, sem necessidade de login.

**[GET]** `/me`

Resposta:
````
HTTP/1.1 401 Unauthorized
````
caso não esteja logado, ou
````
HTTP/1.1 200 OK
````
```json
{
  "email": "user@test.com"
}
```

## Requisitos

- O usuário deve conseguir fazer login, manter a sessão ativa e acessar `/me` após login.
- Um usuário não autenticado não pode acessar `/me`, mas pode acessar `/public`.
- A senha não pode ser armazenada em plain text. Utilize um algoritmo de hashing.
- O Controller não deve ter acesso à senha enviada pelo usuário.
- Utilizar Spring Security com sessão.

## Solução

- Foi criado um sistema que utiliza um banco de dados HSQL para armazenar usuários. Ele possui os endpoints
`/login`, `/me`, `/public` e `/logout`. O acesso ao banco foi implementado com Spring Data JPA através de
`UserRepository` e `UserEntity`. `DataInitializer` insere um usuário de teste no banco de dados.
- O `UserController` contém os endpoints `/me` e `/public`, enquanto que `/login` e `/logout` são definidos
pelo Spring Security e não alcançam o controller. O `UserController` conta com `UserService` para a lógica
de negócio, `UserDTO` e `MessageDTO` para lidar com as respostas HTTP.
- `SecurityConfig` e `CustomUserDetailsService` cuidam da lógica de autenticação e autorização do Spring Security.
Em `SecurityConfig`, é definido um bean para o `PasswordEncoder` e outro para a `SecurityFilterChain`.
- Em `/login` não foi utilizado um body com JSON, mas sim com um form HTML. O Spring Security não possui um
filtro padrão para JSON, mas possui `formLogin` para realizar login com `HttpSession`. O `formLogin` trata
o campo `email` como `username` internamente, mas é apenas uma questão de nomenclatura.
- O Spring Security requer a criação de um `userDetails` para realizar o processo de autenticação. Para isso, 
foi necessário implementar `CustomUserDetailsService` para realizar o acesso no banco de dados e efetuar o 
mapeamento. A classe `User` é um builder de `UserDetais` pertencente ao Spring Security, não confundir com 
`UserEntity`.
- Na criação de `SecurityFilterChain`, foi utilizado requestMatchers para permitir o acesso ao endpoint `/public` 
sem necessidade de login. `/login` já é configurado pelo próprio Spring para ter acesso liberado. Por padrão,
`formLogin` trabalha com redirects pensados para uso em um browser, o que não é ideal para APIs. Com `exceptionHandling`
é possível alterar esse comportamento para utilizar respostas HTTP ao invés de redirects. `/logout` invalida a sessão 
HTTP.
