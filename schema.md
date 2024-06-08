# Instruções

- Deve ser possivel:

  - Cadastrar alunos: incluir, alterar, excluir, listar, buscar pelo CPF, e pelo nome.
  - Cadastrar planos: Cada plano deve ter: código, nome, valor mensal.
  - Cadastrar exercícios. Cada exercício deve conter: número, nome, músculos ativados.

- Para alunos cadastrados, deve ser possível ao instrutor:

  - Cadastrar um plano, contendo: data de início do plano, dados do cartão de crédito
  - Cadastrar uma ou mais opções de treino, onde cada opção de treino contém uma lista de exercícios.
  - Para cada exercício, informar: o número de séries, o número mínimo e máximo de repetições, a carga utilizada (em kgs) e o tempo de descanso (em minutos)
  - Alterar ou excluir opções de treino e os dados dos exercícios cadastrados.

- Deve ser possível ao aluno, em determinada data, iniciar um treino:
  - Escolher um treino dentre as opções disponíveis.
  - Consultar os exercícios a serem feitos, mostrando os dados cadastrados.
  - Marcar exercícios do treino que foram concluídos.
  - Alterar a carga de um determinado exercício.
  - Encerrar um treino.
  - Relatórios
  - Mostrar as datas em que o aluno compareceu à academia em um intervalo de datas.
  - Para um determinado exercício, mostrar a evolução de carga ao longo do tempo.

# Tabelas

- Aluno:

  - CPF;
  - Nome;
  - Data de nascimento;

- Plano:

  - Código;
  - nome;
  - valor mensal;

- exercício:
  - Número;
  - Nome;
  - músculos ativados;
