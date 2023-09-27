###Technical Debt: Escalabilidade da Fila de atendimento
* Swagger: http://localhost:8080/v3/api-docs (Importar no postman)
---
1) Desenhe e implemente uma nova solução para a fila de atendimento, utilizando a
solução de mensageria SQS da AWS.
- Foi implementada uma fila SQS da AWS, usando o login fornecido pela ADA, ao cadastrar um cliente PF/PJ, o mesmo é enviado para fila, sendo consumido pelo endpoint cadastrado na aplicação.
---
### Subi a imagem do projeto para o docker hub, para facilitar na hora de executar o projeto, basta o diretorio raiz e executar: 
*docker-compose up* 

Ou se preferir, subir direto a imagem:

*docker run -p 8080:8080  fcandiotti/fila-atendimento:1.0.0*

