### User Story: Pré-cadastro de clientes

a) Modele uma API REST com operações que possibilitem a criação, alteração, exclusão e consulta de pré-cadastros de clientes. O entregável será um documento Swagger.

**Swagger:**  
[http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs) (Importe no Postman)
- Criar cliente Pessoa Física (PF)
- Criar Cliente Pessoa Jurídica (PJ)
- Editar Cliente Pessoa Física (PF)
- Editar Cliente Pessoa Jurídica (PJ)
- Listar todos os clientes
- Deletar cliente por ID

_Respeite as validações propostas._

c) Implemente uma cobertura de 70% de testes unitários.

**Cobertura:**  
_79% de acordo com o IntelliJ._

Subi a imagem do projeto para o Docker Hub para facilitar na hora de executar o projeto. No diretório raiz, execute:
```
docker-compose up
```

Se preferir, você pode subir diretamente a imagem:
```
docker run -p 8080:8080 fcandiotti/precadastro:1.0.3
```
