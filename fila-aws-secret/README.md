### Technical Debt: Segurança da Informação
* Swagger: http://44.211.42.141:8080/v3/api-docs (Importar no postman)
---
a) identifique um débito técnico de Segurança da Informação na aplicação
- Credenciais do banco de dados e da AWS expostas na aplicação.
---
b) detalhe o débito técnico identificado, informando a criticidade e possíveis
consequências
- Se alguém obtiver acesso às suas credenciais, poderá acessar, modificar ou até mesmo deletar dados no seu banco de dados. Na AWS, eles poderiam potencialmente controlar todos os recursos associados àquelas credenciais, o que poderia levar a situações catastróficas, como a eliminação de máquinas virtuais, bancos de dados ou outros serviços.
---
c) planeje as atividades técnicas para o desenvolvimento da solução
- Poderia ter colocado as credenciais em variáveis de ambiente, assim, mesmo compartilhando a API, as credenciais estariam protegidas. Porém, nesse caso em específico, acredito que não seria muito útil, pois teria que informar no README as credenciais para que fosse possível testar a aplicação. Com isso, foi feito o seguinte:
1) Removido as credenciais do application.properties. 
2) Criado o Secrets Key Manager na AWS.
3) Adicionado as permissões para ele.
4) Hospedado a aplicação no EC2 da AWS. 
5) Configurado a aplicação para fazer a requisição dos dados do banco e da AWS para o Secrets Key Manager. 
6) Com isso, nossas credenciais estão 100% protegidas."
---

### Informaçoes: 
1) IP para teste: http://44.211.42.141:8080 (Collection do swagger para importar no postman está na primeira linha deste readme)
2) Esta aplicação não tem imagem no docker pois a mesma ja se encontra hospedada em um EC2.
3) As credenciais usadas na AWS são as fornecidas pela ADA.