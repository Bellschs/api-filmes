# 🎬 API de Filmes

Bem-vindo à documentação da **API de Filmes**! 🎥  
Esta API permite gerenciar informações sobre filmes, diretores e gêneros de maneira simples e eficiente. Com ela, você pode realizar operações **CRUD** (Create, Read, Update, Delete) para cada uma dessas entidades. 💻✨

---

## 🛠️ Entidades

A API possui três entidades principais:

- **Filmes**: Gerencia os filmes cadastrados na base de dados. 🎬
- **Diretores**: Gerencia os diretores que participaram dos filmes. 🎥
- **Gêneros**: Gerencia os gêneros de filmes. 🎭

---

## 🔧 Funcionalidades

Cada entidade pode ser manipulada por meio de cinco operações básicas (**CRUD**):

- **POST** - Criar (Create) ➕
- **GET** - Ler (Read) 📖
- **PUT** - Atualizar (Update) 🔄
- **DELETE** - Deletar (Delete) ❌

---

## 🌐 Endpoints

A seguir, estão os **endpoints** disponíveis para cada entidade:

### 1. **Filmes** 🎬

- **GET** `/filmes`  
  Retorna todos os filmes.
  
- **POST** `/filmes`  
  Cria um novo filme.
  
- **GET** `/filmes/{id}`  
  Retorna um filme específico pelo ID.
  
- **PUT** `/filmes/{id}`  
  Atualiza os dados de um filme específico pelo ID.
  
- **DELETE** `/filmes/{id}`  
  Deleta um filme específico pelo ID.

#### Campos de um Filme:
- `id`: Identificador único do filme. 🆔
- `titulo`: Título do filme. 🎞️
- `diretorId`: ID do diretor do filme. 🎬
- `generoId`: ID do gênero do filme. 🏷️
- `anoLancamento`: Ano de lançamento do filme. 📅

#### Dados 🎲:

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id` | `int` | **Obrigatório**. id do filme |
| `titulo` | `string` | **Obrigatório**. nome do filme |
| `diretorId` | `int` | **Obrigatório**. id do diretor |
| `generoId` | `int` | **Obrigatório**. id do gênero |
| `anoLancamento` | `int` | **Obrigatório**. ano de lançamento do filme |
---

### 2. **Diretores** 🎥

- **GET** `/diretores`  
  Retorna todos os diretores.
  
- **POST** `/diretores`  
  Cria um novo diretor.
  
- **GET** `/diretores/{id}`  
  Retorna um diretor específico pelo ID.
  
- **PUT** `/diretores/{id}`  
  Atualiza os dados de um diretor específico pelo ID.
  
- **DELETE** `/diretores/{id}`  
  Deleta um diretor específico pelo ID.

#### Campos de um Diretor:
- `id`: Identificador único do diretor. 🆔
- `nome`: Nome do diretor. 👤

#### Dados 🎲:

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id` | `int` | **Obrigatório**. id do diretor |
| `nome` | `string` | **Obrigatório**. nome do diretor |

---

### 3. **Gêneros** 🎭

- **GET** `/genero`  
  Retorna todos os gêneros.
  
- **POST** `/genero`  
  Cria um novo gênero.
  
- **GET** `/genero/{id}`  
  Retorna um gênero específico pelo ID.
  
- **PUT** `/genero/{id}`  
  Atualiza os dados de um gênero específico pelo ID.
  
- **DELETE** `/genero/{id}`  
  Deleta um gênero específico pelo ID.

#### Campos de um Gênero:
- `id`: Identificador único do gênero. 🆔
- `nome`: Nome do gênero. 🏷️

#### Dados 🎲:

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id` | `int` | **Obrigatório**. id do gênero |
| `nome` | `string` | **Obrigatório**. nome do gênero |
---

## 📝 Exemplos de Uso

### 1. Criar um Filme

**Endpoint:** `POST /filmes`

**Exemplo de corpo da requisição:**

```json
{
  "titulo": "Inception",
  "diretorId": 1,
  "generoId": 1,
  "anoLancamento": 2010
}
`````

### 2. Obter Todos os Filmes

**Endpoint:** `GET /filmes`

**Exemplo de corpo da requisição:**

```json
[
  {
    "id": 1,
    "titulo": "Inception",
    "diretorId": 1,
    "generoId": 1,
    "anoLancamento": 2010
  },
  {
    "id": 2,
    "titulo": "Interstellar",
    "diretorId": 1,
    "generoId": 2,
    "anoLancamento": 2014
  }
]

`````
### 3. Obter um Filme Específico

**Endpoint:** `GET /filmes/{id}`

**Exemplo de corpo da requisição:**

```json
 {
    "id": 1,
    "titulo": "Inception",
    "diretorId": 1,
    "generoId": 1,
    "anoLancamento": 2010
  }
`````
### 4. Atualizar um Filme

**Endpoint:** `PUT /filmes/{id}`

**Exemplo de corpo da requisição:**

```json
 {
    "titulo": "Inception 2",
    "diretorId": 1,
    "generoId": 1,
    "anoLancamento": 2022
  }
`````
### 5. Deletar um Filme

**Endpoint:** `DELETE /filmes/{id}`

**Exemplo de corpo da requisição:**
```json
{
  "status": "204 No Content"
}
`````
## ⚠️ Considerações Finais

Obrigado por usar a API de Filmes! Esperamos que essa documentação tenha ajudado a entender como criar, entender e usar a API. Se tiver alguma dúvida ou se deparar com algum problema, é só chamar.

Divirta-se explorando todos os recursos! 🎬
