### Technical Debt: Escalabilidade da Fila de atendimento

* Swagger: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs) (Importar no Postman)

---

1) Desenhe e implemente uma nova solução para a fila de atendimento, utilizando a solução de mensageria SQS da AWS:
- Foi implementada uma fila SQS da AWS utilizando o login fornecido pela ADA. Ao cadastrar um cliente PF/PJ, este é enviado para a fila e consumido pelo endpoint cadastrado na aplicação. Também foi adicionada uma lógica para remover o cliente da fila após ser consumido.

---

### Para execução do projeto:

Subi a imagem do projeto para o Docker Hub para facilitar na hora de executar. No diretório raiz, execute: 
```
docker-compose up
```

Ou, se preferir, pode subir diretamente a imagem:
```
docker run -p 8080:8080 fcandiotti/fila-aws:1.0.1
```