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
