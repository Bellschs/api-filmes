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

## 🔐 Recursos Avançados

Esta API inclui recursos modernos de segurança, validação e controle de uso:

- **🔒 Idempotência**: Evita criação duplicada em requisições POST com `x-idempotency-key`.
- **🔐 Autenticação com API Key**: Uso obrigatório do header `x-api-key`.
- **📶 Rate Limiting**: Limite de requisições com resposta 429 em excesso.
- **🌐 CORS Configurado**: Suporte completo a requisições de diferentes origens.
- **🧪 Bean Validation**: Validação de dados com mensagens claras.
- **🔥 Tratamento Global de Erros**: Mensagens estruturadas em JSON com status e descrição.
- **📦 Versionamento**: Endpoints também disponíveis em `/api/v1/...` para evoluções futuras.

---

## 🌐 Endpoints

### 1. **Filmes** 🎬

- **GET** `/filmes`
- **POST** `/filmes` (usa `x-idempotency-key`)
- **GET** `/filmes/{id}`
- **PUT** `/filmes/{id}`
- **DELETE** `/filmes/{id}`

Também disponível como `/api/v1/filmes`

#### Campos:
- `id`: Identificador único do filme. 🆔
- `titulo`: Título do filme. 🎞️
- `diretorId`: ID do diretor. 🎬
- `generoId`: ID do gênero. 🏷️
- `anoLancamento`: Ano de lançamento. 📅

### 2. **Diretores** 🎥

- **GET** `/diretores`
- **POST** `/diretores` (usa `x-idempotency-key`)
- **GET** `/diretores/{id}`
- **PUT** `/diretores/{id}`
- **DELETE** `/diretores/{id}`

Também disponível como `/api/v1/diretores`

#### Campos:
- `id`: Identificador único. 🆔
- `nome`: Nome do diretor. 👤

### 3. **Gêneros** 🎭

- **GET** `/genero`
- **POST** `/genero` (usa `x-idempotency-key`)
- **GET** `/genero/{id}`
- **PUT** `/genero/{id}`
- **DELETE** `/genero/{id}`

Também disponível como `/api/v1/genero`

#### Campos:
- `id`: Identificador único. 🆔
- `nome`: Nome do gênero. 🏷️

---

## 📝 Exemplos de Requisição

### Criar um Diretor (com idempotência e API Key)
```http
POST /diretores
x-api-key: sua-chave
gx-idempotency-key: 123e4567
Content-Type: application/json

{
  "nome": "Christopher Nolan"
}
```

### Criar um Filme
```http
POST /filmes
x-api-key: sua-chave
gx-idempotency-key: filme-123
Content-Type: application/json

{
  "titulo": "Inception",
  "diretorId": 1,
  "generoId": 1,
  "anoLancamento": 2010
}
```

### Erro de validação
```json
{
  "erro": "Erro ao processar a requisição",
  "mensagem": "O nome do diretor é obrigatório",
  "status": 400
}
```

### Erro de autenticação (API Key inválida)
```json
{
  "erro": "Erro ao processar a requisição",
  "mensagem": "Chave de API inválida ou ausente",
  "status": 401
}
```

---

## ⚠️ Considerações Finais

Obrigado por usar a API de Filmes! Se tiver dúvidas ou encontrar problemas, fique à vontade para abrir uma issue ou entrar em contato.

Explore com responsabilidade e divirta-se! 🎬✨
