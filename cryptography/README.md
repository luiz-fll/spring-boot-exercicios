# Criptografia

O seu desafio será implementar a criptografia num serviço de forma transparente para a API e para as camadas de
serviço da sua aplicação. O objetivo é garantir que os campos sensíveis dos objetos de entidade não sejam visíveis
diretamente, realizando a criptografia em tempo de execução durante a conversão da entidade para a coluna correspondente
no banco de dados, e vice-versa.

## Exemplo

Considere os campos `userDocument` e `creditCardToken` como campos sensíveis que devem ser criptografados. A tabela de
exemplo seria a seguinte:

| id | userDocument     | creditCardToken | value |
|:---|:-----------------|:----------------|:------|
| 1  | MzYxNDA3ODE4MzM= | YWJjMTIz        | 5999  |
| 2  | MzI5NDU0MTA1ODM= | eHl6NDU2        | 1000  |
| 3  | NzYwNzc0NTIzODY= | Nzg5eHB0bw==    | 1500  |

A estrutura da entidade correspondente seria a seguinte:

| Campo           | Tipo   |
|:----------------|:-------|
| id              | Long   |
| userDocument    | String |
| creditCardToken | String |
| value           | Long   |

## Requisitos

- Implemente um CRUD simples considerando os campos mencionados acima como sensíveis.
- Utilize um algoritmo de criptografia simétrica ou assimétrica da sua preferência. <br>
  </br>

  Sugestões:
  </br>
  [AES](https://pt.wikipedia.org/wiki/Advanced_Encryption_Standard) para criptografia simétrica ou
  [RSA](https://en.wikipedia.org/wiki/PBKDF2) para criptografia assimétrica.

## Solução

- Foi criada uma aplicação Spring Boot que utiliza um banco de dados em memória (HSQL). Para gerar a tabela no banco
de dados, foi utilizado um script SQL localizado em `main/java/resources/schema.sql`. O ponto de entrada é a classe
`CryptographyApplication`
- Para modelar os dados do banco dentro da aplicação, foi utilizada a classe `UserEntity` com a anotação JPA `@Entity`
- Para acessar o banco de dados, foi criada a interface `UserRepository`, que é implementada automaticamente pelo
Spring Data JPA
- O Spring Web já inclui o Jackson, que converte POJOs para JSONs e vice-versa. Na `UserEntity`, há campos que não 
podem ser `null`, mas durante requisições e respostas HTTP, nem todos os campos são necessários (é o caso do método 
PUT/update do CRUD). Para isso, foi criada a record UserDTO (Data Transfer Object), que simplifica a transferência de 
dados e conversões POJO para JSON ou JSON para POJO
- A parte de criptografia é gerenciada pela classe `SensitiveDataConverter`. Essa classe é um `@Converter`, e age em
conjunto com o `UserRepository` para criptografar e descriptografar os campos `userDocument` e `creditCardToken`
durante as interações com o banco de dados.
- O algoritmo escolhido foi o AES. Ele utiliza uma única chave tanto para criptografar quanto para descriptografar.
Essa chave está guardada em `main/java/resources/application.properties`, codificada em Base64. No construtor de
`SensitiveDataConverter`, a chave é decodificada, e os seus bytes são utilizados para instanciar uma chave AES
- O fluxo de criptografia/descriptografia é: `String` original > decodificação UTF-8 para `bytes[]` > aplicação do AES
gerando `bytes[]` criptografados > codificação Base64 dos `bytes[]` > criação de `String` UTF-8 criptografada >
decodificação UTF-8 para `bytes[]` > decodificação Base64 dos `bytes[]` > aplicação do AES gerando `bytes[]` 
descriptografados > recriação da `String` UTF-8 original
- Tanto a chave como os métodos de (des)criptografia utilizam Base64 para codificação. Base64 não criptografa dados,
ele apenas codifica bytes de maneira que eles possam ser convertidos em texto/string. Base64 é utilizado quando há
necessidade de transferir e armazenar dados binários em formato de texto (ex: anexar arquivos em emails).
- A API REST do CRUD é implementada pela classe `UserController`. Toda a comunicação é feita por métodos HTTP,
`UserDTO`s e parâmetros de URL. A lógica da aplicação é implementada em `UserService`
- Duas exceções personalizadas foram criadas: `MissingDataException`, utilizada para emitir respostas HTTP BAD REQUEST
quando a criação de usuário tiver dados faltantes, e `NoSuchUserExistsException`, utilizada para emitir respostas
HTTP NOT FOUND quando a ID passada por parâmetro se refere a um usuário inexistente
- Há 4 classes de testes: `DataStorageTests`, que testa se o banco está armazenando dados corretamente (inclusive se 
está (des)criptografando strings); `SerializationDeserializationTests`, que testa se as conversões de
UserDTO para JSON (ou JSON para UserDTO) estão ocorrendo corretamente; `UserControllerTests`, que testa se os endpoints
estão funcionando como esperado e `UserServiceTests`, que testa isoladamente as funções de `UserService`. Os testes
isolam partes da aplicação por meio de anotações como `@JsonTest`, `@DataJpaTest`, `@WebMvcTest` e 
`@ExtendWith(MockitoExpression.class)`, evitando carregar o contexto da aplicação inteiro (como ocorre ao utilizar
`@SpringBootTest`)