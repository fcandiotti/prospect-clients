### Technical Debt: Segurança da Informação

* Swagger: [http://44.211.42.141:8080/v3/api-docs](http://44.211.42.141:8080/v3/api-docs) (Importar no Postman)

---

a) Identifique um débito técnico de Segurança da Informação na aplicação:
- Credenciais do banco de dados e da AWS expostas na aplicação.

---

b) Detalhe o débito técnico identificado, informando a criticidade e possíveis consequências:
- Se alguém obtiver acesso às suas credenciais, poderá acessar, modificar ou até mesmo deletar dados no seu banco de dados. Na AWS, eles poderiam potencialmente controlar todos os recursos associados àquelas credenciais, o que poderia levar a situações catastróficas, como a eliminação de máquinas virtuais, bancos de dados ou outros serviços.

---

c) Planeje as atividades técnicas para o desenvolvimento da solução:
- Embora seja possível ter colocado as credenciais em variáveis de ambiente, nesta situação isso não seria ideal, pois seria necessário informar no README as credenciais para testar a aplicação. Dado isso, as seguintes ações foram realizadas:
    1. Remoção das credenciais do `application.properties`.
    2. Criação do Secrets Key Manager na AWS.
    3. Adição das permissões necessárias.
    4. Hospedagem da aplicação no EC2 da AWS.
    5. Configuração da aplicação para solicitar os dados do banco e da AWS ao Secrets Key Manager.
    6. Com essas medidas, nossas credenciais estão agora 100% protegidas.

---

### Informações:
1. IP para teste: [http://44.211.42.141:8080](http://44.211.42.141:8080) (A collection do Swagger para importar no Postman está na primeira linha deste README)
2. Esta aplicação não tem imagem no Docker, pois já está hospedada em um EC2.
3. As credenciais usadas na AWS são fornecidas pela ADA.
