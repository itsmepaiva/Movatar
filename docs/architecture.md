## Arquitetura

- **Camada de Apresentação**
  - Postman: Entrada de dados para os endpoints.
  - Routes: Define os endpoints da API.
  - Controllers: Gerencia as requisiçoes HTTP e coordena os serviços.

- **Camada de Negocio**
  - Services: Implementa a logica de requisiçoes, autenticacoes e tratamento de Json de cada dominio.
  - Integrador Service: Orquestra a integraçao entre diferentes servicos.

- **Camada de Dados**
  - Models: Define as estruturas de dados utilizadas na aplicação para recebimento e retorno.
  -External APIs: Abstrai a comunicaçao com APIs externas.

<img width="1560" height="330" alt="Diagrama sem nome drawio (1)" src="https://github.com/user-attachments/assets/ecbbdb62-a4e0-4b9f-ae5d-6cc448a52094" />

