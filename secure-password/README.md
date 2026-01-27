# Senha segura

O seu desafio será implementar um serviço que valide se uma senha é considerada segura com base em critérios
pré-definidos.

## Exemplo

O seu serviço recebe uma chamada para validar uma senha.

**[POST]** `{{host}}/validate-password`

```json
{
    "password": "vYQIYxO&p$yfI^r"
}
```

Se a senha atender a todos os critérios de segurança, então, seu serviço deve retornar uma resposta de sucesso.

```
HTTP/1.1 204 NoContent
```

Caso contrário, retorne uma mensagem informando quais critérios não foram atendidos. Você pode escolher como será sua
estrutura para as mensagens de erro.

```
HTTP/1.1 400 Bad Request
```

```json
{
    ...
}
```

## Requisitos

- Verificar se a senha possui pelo menos 08 caracteres.
- Verificar se a senha contém pelo menos uma letra maiúscula.
- Verificar se a senha contém pelo menos uma letra minúscula.
- Verificar se a senha contém pelo menos um dígito numérico.
- Verificar se a senha contém pelo menos um character especial (e.g, !@#$%).

## Solução

- Foi criado um endpoint `/validate-password` com a classe `SecurePasswordController` anotada com `@RestController`.
- Foi criado um Data Transfer Object para lidar com o body das requisições (RequestDTO).
- Foi criado um serviço `SecurePasswordService` anotado com `@Service` que possui um único método `validate`.
Este método usa regex para validar, e gera um `Map<String, Boolean>` verificando cada etapa de validação.
O método não retorna nada em caso de sucesso, mas dispara uma exceção em caso de senha inválida.
- A `InvalidPasswordException` é uma exceção disparada por `validate` que guarda o mapa gerado.
O `InvalidPasswordExceptionHandler`, localizado no `SecurePasswordController`, se encarrega de lidar com a exceção
(indicado pela anotação `@ExceptionHandler`), emitir o HTTP status Bad Request (através da anotação 
`@ResponseStatus`) e enviar o mapa no body da resposta HTTP.
