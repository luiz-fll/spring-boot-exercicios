# Autenticação

O seu desafio será implementar um serviço que valide um token de acesso recebido no header da requisição HTTP.

## Exemplo

O seu serviço recebe uma chamada no endpoint abaixo.

**[GET]** `{{host}}/foo-bar`

| Parâmetro |  Tipo  | Chave         | Valor                            |
|:---------:|:------:|---------------|:---------------------------------|
| `header`  | string | Authorization | Seu token. e.g (`vYQIYxOpyfr==`) |

Se o token enviado no header `Authorization` for válido, então, uma resposta de sucesso deve ser retornada.

```
HTTP/1.1 204 NoContent
```

Se o token for inválido, o seu serviço deve retornar uma mensagem de erro, indicando que o token é inválido. A
estrutura da mensagem de erro pode ser definida por você, mas deve conter informações claras sobre o problema.

```
HTTP/1.1 401 Unauthorized
```

```json
{
    ...
}
```

## Requisitos

- Deve ser criado um serviço para a validação do token. Implementar uma lógica concreta de validação é opcional,
  podendo ser utilizado um mock para este fim.
- Deve ser implementada uma forma de interceptar a requisição e validar o token antes que ela chegue ao seu controlador.
- A implementação deve ser feita de maneira que continue a funcionar corretamente após a adição de novos endpoints.

## Solução

- Foi criado um programa composto por um controller e um interceptor, AuthenticationController e
TokenValidationInterceptor. Também foi criada uma classe TokenValidationInterceptorConfig apenas para
configurar o interceptor, além da classe AuthenticationApplication, o ponto de entrada da aplicação
- Há 3 endpoints no controller. Um dos endpoints faz a validação de token, outro endpoint sempre permite
requisições e o terceiro endpoint sempre bloqueia requisições. Na prática, todos eles retornam status 204,
é o interceptor que deve agir antes da requisição chegar aos endpoints
- No interceptor, há código para avaliar para qual endpoint a requisição está indo. `/always-allowed`
sempre passará sem alterações, `/requires-token` possui uma comparação com a string 
`"Bearer rightToken"`. Se o conteúdo do header `Authorization` for diferente dessa string, o interceptor
envia uma resposta com status 401 e um JSON explicando o erro. `/always-blocked` sempre será interceptado
e retornará status 401. Demais requisições receberão status 404.
- Há uma classe de testes, AuthenticationApplicationTests, que usa TestRestTemplate para enviar 
requisições para os endpoints e verificar os status das respostas de acordo com o resultado esperado.