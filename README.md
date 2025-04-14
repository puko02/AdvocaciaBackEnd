<<<<<<< HEAD
# AdvocaciaBackEnd
=======
📑 Sistema de Advocacia

📌 Sobre o Projeto
Este projeto é um Sistema de Gestão para Escritório de Advocacia, desenvolvido em Java utilizando Hibernate e PostgreSQL para persistência de dados. O sistema foi criado com o objetivo de organizar agendamentos, consultar informações de clientes, controlar a disponibilidade de horários dos advogados e adicionar anotações relacionadas a cada cliente, de forma simples e eficiente.

O projeto conta com menus interativos via console, separados para Clientes e Advogados, além de funcionalidades administrativas.

🖥️ Tecnologias Utilizadas

- Java 17

- Hibernate ORM

- PostgreSQL

- Maven

- IntelliJ IDEA

- JPA (Java Persistence API)

📊 Estrutura de Banco de Dados
O banco de dados utilizado se chama advocacia e contém as seguintes tabelas:

- usuario

- agendamento

- anotacao

- disponibilidade

- documento

- documento_agendamento

📚 Funcionalidades do Sistema

📜 Menu Principal

- Menu para Login de Advogado

- Menu para Agendamento de Clientes

- Menu de Consultas

- Opção para Sair

📅 Menu de Agendamento

- Digitar nome, telefone e e-mail.

- Escolher horário e dia para o agendamento.

- Retornar ao menu inicial.

🔐 Menu de Login (Advogado)

- Digitar e-mail válido.

- Digitar senha válida.

- Acessar o menu de administração do advogado.

🔍 Menu de Consultas

- Consultar andamento da consulta, pesquisando pelo e-mail do cliente.

- Opção para retornar ao menu principal.

⚙️ Menu do Advogado

- Editar disponibilidade de dias da semana e horários.

- Visualizar dados dos clientes e adicionar anotações para cada um.

- Ver agendamentos, pesquisando por e-mail (buscar pela tabela usuario, pegar a ID e consultar na tabela agendamento).

- Realizar consulta de agendamentos para o dia seguinte.

📦 Estrutura e Funções dos Arquivos

- Entities (Modelos de Dados):

- UsuariosEntity

- AgendamentoEntity

- AnotacaoEntity

- DisponibilidadeEntity

- DocumentoEntity

- DocumentoAgendamentoEntity

Cada uma mapeia e representa uma tabela no banco de dados advocacia.

Repositories:

- Contêm os métodos de persistência e consultas de cada entidade.

Exemplo: adicionar notas, buscar agendamentos, adicionar disponibilidade, etc.
Utilizam Hibernate e SessionFactory para operações no banco.

Interfaces:

- Definem o contrato das operações básicas (inserir, buscar, listar).

Functions:

- Classes responsáveis pela interação com o usuário no console, menus e execução das funcionalidades.

👨‍💻 Integrantes do Projeto

Nome	Função

- Alexandro Augusto Ferreira Santos - DEV1

- João Pedro dos Reis Felini - Project Owner

- Moroni de Melo - Scrum Master

- Fabrício Ricardo Castillo Quintana - DEV2

🚀 Como Executar o Projeto

- Clonar o repositório

- Configurar o banco de dados PostgreSQL com o nome advocacia

- Rodar o script de criação das tabelas (baseado nas entidades mapeadas)

- Configurar o hibernate.cfg.xml com os dados de conexão do banco

- Executar a classe MenuFunction ou UsuariosFunction na IDE

📌 Observação

O projeto foi desenvolvido como prática acadêmica para reforçar conceitos de Orientação a Objetos, JPA/Hibernate, e modelagem relacional, com funcionalidades essenciais para gestão de consultórios jurídicos.
>>>>>>> test
