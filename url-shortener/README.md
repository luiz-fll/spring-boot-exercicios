# Encurtador de URLs

O seu desafio será implementar um serviço que permite encurtar URLs longas para torná-las mais compactas e fáceis de
compartilhar.

## Exemplo

O seu serviço recebe uma chamada para encurtar uma URL.

**[POST]** `{{host}}/shorten-url`

```json
{
    "url": "https://backendbrasil.com.br"
}
```

E retorna um JSON com a URL encurtada:

```
HTTP/1.1 200 OK
```

```json
{
    "url": "https://xxx.com/DXB6V"
}
```

## Requisitos

- O encurtador de URLs recebe uma URL longa como parâmetro inicial.
- O encurtamento será composto por um mínimo de 05 e um máximo de 10 caracteres.
- Apenas letras e números são permitidos no encurtamento.
- A URL encurtada será salva no banco de dados com um prazo de validade (você pode escolher a duração desejada).
- Ao receber uma chamada para a URL encurtada `https://xxx.com/DXB6V`, você deve fazer o redirecionamento para a
  URL original salva no banco de dados. Caso a URL não seja encontrada no banco, retorne o código de
  status `HTTP 404 (Not Found)`.

## Solução

- Foi criado um sistema que possui dois endpoints: um que recebe requisições GET em `/{codigo_encurtado}` e outro
para requisições POST para gerar encurtamentos (`/shorten-url`), definidos em `UrlShortenerController`.
- `UrlEntity` modela as entradas no banco de dados, `UrlShortenerRepository` é a interface de acesso. Cada entrada
possui o código encurtado `code`, a URL original `url` e a data de expiração `expired_at`. `code` é chave primária.
O banco escolhido foi o HyperSQL, e a geração da tabela foi feita com o script `main/java/resources/schema.sql`.
- `UrlShortenerService` cuida da geração de códigos e realiza acessos no banco de dados. 
- `UrlShortenerProperties` extrai propriedades dentro do `application.properties`. São elas
`shortener.service.baseurl` para obter a URL base e `shortener.service.alphabet` para obter os caracteres a serem
utilizados na geração do código encurtado (letras maiúsculas, minúsculas e digitos).
- `UrlDTO` é um Data Transfer Object para modelar o body de requisições e respostas HTTP.
- Ao acessar uma URL, pode ocorrer de ela estar expirada ou não existir. As exceções `ExpiredURLException` e
`UnknownURLCodeException` são lançadas nestes casos, com HTTP status codes definidos pela anotação `@ResponseStatus`.