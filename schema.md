# Instruções

- Deve ser possível:

  - Cadastrar alunos: incluir, alterar, excluir, listar, **buscar pelo CPF, e pelo nome**.
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

- Cada aluno contêm um instrutor.
- Um instrutor contem vários alunos.

## Tabelas gerais que podem ser Criadas

- Aluno:

  - CPF;
  - Nome;
  - Data de nascimento;

- Plano:

  - Código;
  - nome;
  - valor mensal;

- Exercício:

  - Número;
  - Nome;
  - músculos ativados;

## Dados Cadastrados pelos instrutores a partir de alunos inscritos

Instrutores cadastram alunos a plano específicos (tornando-os membros) e opções de treinos que contem listas de exercícios, podendo apagar os mesmos;

- Membros:

  - Código;
  - Código de aluno;
  - Código da opção de treino (escolhida pelo aluno);
  - Data de inscrição;
  - Código Cartão de cŕedito;
  - Código de Relatório de treino;

- Opção de treino (várias opções podem ser criadas pelo instrutor, uma deve ser escolhida pelo aluno):

  - Código;
  - Código de membro;
  - Código de uma lista de exercício;
  - Nome;
  - Data de início do treino;
  - Data de término do treino;

- Lista de Exercícios:

  - Código;
  - Lista de códigos de exercícios;
  - Numero de séries;
  - Minimo de repetições;
  - Máximo de repetições;
  - Carga (pode ser alterada pelo aluno);
  - Tempo de descanso;

## Dados fornecidos pelos alunos

Os Alunos devem poder escolher treinos, consultar os exercícios deles, marcar quais exercícios foram concluídos, encerrar treinos e alterar a carga dos exercícios.

- Relatórios de Treino:
  - Código;
  - Código de opção de treino;
  - Lista de Códigos de exerícios;
  - Exercícios completos;
  - Verificação de Exercício completo (bool).
  - Preferencia de carga do aluno.
  - Data de Conclusão.

---

```DBML
Table students {
    id serial [pk, not null]
    cpf varchar(14) [unique, not null]
    name varchar(100) [not null]
    birth_date date [not null]
}

Table plans {
    id serial [pk, not null]
    name varchar(50) [not null]
    monthly_fee decimal(10, 2) [not null]
}

Table exercises {
    id serial [pk, not null]
    name varchar(100) [not null]
    muscles_activated varchar(255) [not null]
}

Table memberships {
    id serial [pk, not null]
    student_id integer [ref: > students.id, not null]
    plan_id integer [ref: > plans.id, not null]
    start_date date [not null]
    current_workout_id integer [ref: > workouts.id]
    card_number varchar(19) [not null]
    card_holder_name varchar(100) [not null]
    card_expiry_date varchar(5) [not null]
    card_cvv varchar(4) [notnull]

    Indexes {
        student_id
        plan_id
    }
}

Table workouts {
    id serial [pk, not null]
    exercise_sets_id integer [ref: > exercise_sets.id, not null]
    membership_id integer [ref: > memberships.id, not null]
    name varchar(100) [not null]
    start_date date [not null]
    end_date date

    Indexes {
        membership_id
        exercise_sets_id
    }
}

Table exercise_sets {
    id serial [pk, not null]
    exercise_id integer [ref: > exercises.id, not null]
    series_count integer [not null]
    min_reps integer [not null]
    max_reps integer [not null]
    rest_time integer [not null]

    Indexes {
        exercise_id
    }
}

Table workout_records {
    id serial [pk, not null]
    workout_id integer [ref: > workouts.id, not null]
    exercise_id integer [ref: > exercises.id, not null]
    exercise_completed boolean [not null]
    load integer [not null]
    completion_date date
}
```
