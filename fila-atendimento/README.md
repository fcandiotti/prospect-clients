### User Story: Fila de atendimento
* Swagger: http://localhost:8080/v3/api-docs (Importar no postman)
---
1) Toda vez que um novo cadastro ou uma alteração de cadastro for realizada no
   sistema, o cliente deverá entrar na última posição da fila de atendimento. 
- OK, toda vez que um novo cadastro é realizado, ele é enviado para a fila(implementada sem java.util 😡), modelo FIFO, quando um usuario é atualizado, o mesmo é removido da fila e colocado na ultima posição.
---
2) Possibilitar a retirada do cliente na primeira posição da fila de atendimento
- OK
---
3) Caso o gestor comercial solicite um prospect da fila para atendimento e não houver nenhum cliente na fila, deverá retornar um status coerente informando que a fila de atendimento está vazia.
- Caso a fila esteja vazia, sera exibida a mensagem retornando o status 404.
---
4) Implementar cobertura de 70% de testes unitários
- Cobertura de 75% segundo IntelliJ
---
### Subi a imagem do projeto para o docker hub, para facilitar na hora de executar o projeto, basta o diretorio raiz e executar: 
*docker-compose up* 

Ou se preferir, subir direto a imagem:

*docker run -p 8080:8080  fcandiotti/fila-atendimento:1.0.2*

