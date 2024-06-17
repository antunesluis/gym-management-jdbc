# Projeto

Projeto dedicado à implementação do segundo trabalho da disciplina de Paradigmas de Programação 2024/1 (elc117), focado na criação de um serviço de gerenciamento para academias em Java.

## Instruções sobre a implementação

1. Os dados de alunos e de exercícios devem ser armazenados em uma base de dados relacional (postgres, mysql, sqlserver etc.). Os dados de outras entidades podem ser armazenados em listas em memória (quem desejar, pode armazená-los no banco também).

2. Deve ser possível cadastrar alunos: incluir, alterar, excluir, listar, buscar pelo CPF, e pelo nome. Cada aluno deve ter: CPF, nome, data de nascimento.

3. Deve ser possível cadastrar planos. Cada plano deve ter: código, nome, valor mensal.

4. Deve ser possível cadastrar exercícios. Cada exercício deve conter: número, nome, músculos ativados.

5. Para alunos cadastrados, deve ser possível ao instrutor:

   - Cadastrar um plano, contendo: data de início do plano, dados do cartão de crédito.
   - Cadastrar uma ou mais opções de treino, onde cada opção de treino contém uma lista de exercícios.
   - Para cada exercício, informar: o número de séries, o número mínimo e máximo de repetições, a carga utilizada (em kgs) e o tempo de descanso (em minutos).
   - Alterar ou excluir opções de treino e os dados dos exercícios cadastrados.

6. Deve ser possível ao aluno, em determinada data, iniciar um treino:
   - Escolher um treino dentre as opções disponíveis.
   - Consultar os exercícios a serem feitos, mostrando os dados cadastrados.
   - Marcar exercícios do treino que foram concluídos.
   - Alterar a carga de um determinado exercício.
   - Encerrar um treino.

# DB schema

<div align="center">
  <img src="https://github.com/antunesluis/gym-management-jdbc/blob/main/db-schema-diagram.png" />
</div>
