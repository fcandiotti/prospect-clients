### User Story: Fila de atendimento

* Swagger: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs) (Importar no Postman)

---

1) Toda vez que um novo cadastro ou uma alteração de cadastro for realizada no sistema, o cliente deverá entrar na última posição da fila de atendimento:
- Concluído. Toda vez que um novo cadastro é realizado, ele é enviado para a fila (implementada com `java.util.Queue`), utilizando o modelo FIFO. Quando um usuário é atualizado, o mesmo é removido da fila e colocado na última posição.

---

2) Possibilitar a retirada do cliente na primeira posição da fila de atendimento:
- Concluído.

---

3) Caso o gestor comercial solicite um prospect da fila para atendimento e não houver nenhum cliente na fila, deverá retornar um status coerente informando que a fila de atendimento está vazia:
- Se a fila estiver vazia, será retornada uma mensagem com o status 404, informando que não há clientes na fila.

---

4) Implementar cobertura de 70% de testes unitários:
- Cobertura de 70% segundo o IntelliJ.

---

### Para execução do projeto:

Subi a imagem do projeto para o Docker Hub para facilitar na hora de executar. No diretório raiz, execute: 
```
docker-compose up
```
Ou, se preferir, pode subir diretamente a imagem:

``` 
docker run -p 8080:8080 fcandiotti/fila-atendimento:1.0.3
```
