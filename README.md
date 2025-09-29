# Movatar


# API Movatar consumindo OMDb API, DiceBear API e Youtube Data API v3

## Recursos do Projeto

- Java 21
- Spring BOOT 3.5.6 + Spring Framework
- Lombok
- Swagger
- REST API

## Objetivo

- A API Movatar é integrada com a OMBd API que verifica a existencia do filme escolhido e retorna dados sobre o filme, após isso a API gera um avatar com a utilizaçao da DiceBear API baseado no imdb Id do filme ou entao retorna o trailer do filme selecionado utilizando a Youtube Data API v3.

## Funcionamento
- Todas as requisiçoes do projeto sao realizadas via requisiçoes HTTP, baseada no modelo de comunicação REST 
- Para a utilização da API corretamente, é necessario gerar uma API key para a OMBd API e outra key para a Youtube DATA API v3.
  (A DiceBear API nao requer a utilizaçao de chave key para o Funcionamento). 

- As chaves ficam armazenadas em variaveis de ambientes no application.properties

#### - OMBd API
- Para gerar sua chave de API (key) voce deve acessar o site do provedor, https://www.omdbapi.com/, selecionar a opçao API Key e realizar o seu cadastro para receber a chave via email.

#### - Youtube Data API v3
Para gerar a chave de API do youtube existem alguns passos a serem tomados:
  + Acesse o Google Cloud Console.
  + Crie um novo projeto e ative a YouTube Data API v3.
  + Vá para "Credenciais" e gere uma Chave de API (API Key).
  + Para medidas de segurança, limite o uso da chave para apenas Youtube Data API v3.

OBS: O uso do Youtube Data API v3 é gratuito, porem, tem limite de uso, é interessante conferir o limite de cotas no proprio Google Cloud Console.


## Variaveis de ambientes
As variaveis de ambientes armazenam dados de configuração e encontram-se centralizadas no arquivo application.properties. Para o funcionamento da API Movatar, lidamos com algumas variaveis:


```bash
  omdb.api.base.url=http://www.omdbapi.com/
  youtube.api.search.url=https://www.googleapis.com/youtube/v3/search
  dicebear.api.base.url=https://api.dicebear.com/9.x/

  dicebear.avatar.style=micah

  omdb.api.key=(SUA_CHAVE_API)
  youtube.api.key=(SUA_CHAVE_API)
  
```
  - O primeiro bloco define as URL das requisiçoes das APIs sendo utilizadas pela API Movatar
  - dicebear.avatar.style=micah : essa variavel define o tipo de animaçao do DiceBear que será utilizado para gerar o avatar. Devido o grande numero de variaçoes de animaçoes da API, foi preferivel deixar a configuração da animaçao como variavel de ambiente para evitar falhas de comunicação.

    *conferir na documentação da DiceBear API os estilos disponiveis para o caso de alteração da variavel*
  - o ultimo bloco define as chaves de API a serem utilizadas pelo programa, antes de iniciar o projeto pela primeira vez, voce deve substituir os campos (SUA_CHAVE_API) pela respectiva API key gerada pela OMDb API e YouTube Data API v3.
## Documentação da API

### - Endpoint que retorna os dados do filme selecionado

```http
  GET /api/movie
```

| Parâmetro @RequestParam   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `title` | `string` | **Obrigatório**. O titulo do filme a ser pesquisado |
| `year` | `string` | **Opcional**. Ano de lançamento do filme a ser pesquisado|


**Resposta Esperada: (200 OK)** 

**Retorna:	movieDTO (Objeto JSON contendo Title, Year, Plot, ImdbID e Response.).**

### - Endpoint que retorna os dados do filme juntamente do link do Trailer

```http
  GET /api/movie/trailer
```

| Parâmetro @RequestParam   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `title` | `string` | **Obrigatório**. O titulo do filme a ser pesquisado |
| `year` | `string` | **Opcional**. Ano de lançamento do filme a ser pesquisado|

**Resposta Esperada: (200 OK)** 

**Retorna:	MovatarYTResponseDTO (Objeto JSON contendo Title, Year, Plot, ImdbID, trailerUrl e videoId.).**


### - Endpoint que retorna os dados do filme juntamente do link do Avatar gerado

```http
  GET /api/movie/avatar
```

| Parâmetro @RequestParam   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `title` | `string` | **Obrigatório**. O titulo do filme a ser pesquisado |
| `year` | `string` | **Opcional**. Ano de lançamento do filme a ser pesquisado|

**Resposta Esperada: (200 OK)** 

**Retorna:	MovatarResponseDTO (Objeto JSON contendo Title, Year, Plot, ImdbID, avatarUrl e avatarSource.).**
