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

# Tabelas gerais que podem ser Criadas

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

# Dados Cadastrados pelos instrutores a partir de alunos inscritos

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

# Dados fornecidos pelos alunos

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

# Associação entre as tabelas

1. students tem várias memberships.
2. plans tem várias memberships.
3. exercises tem várias exercise_sets e várias workout_records.
4. memberships pertence a students e a plans.
5. memberships pode ter um current_workout.
6. workouts pertence a memberships e tem vários exercise_sets.
7. exercise_sets pertence a workouts e contém vários exercises através de uma tabela de junção (exercise_set_exercises).
8. workout_records pertence a workouts e a exercises.

# Explicação das Tabelas e Relacionamentos

- `students` e `plans` são tabelas básicas contendo informações sobre alunos e planos de academia.
- `memberships` conecta alunos e planos, além de registrar informações de pagamento e associar o treino atual.
- `exercises` contém a lista de exercícios disponíveis na academia.
- `exercise_sets` define um conjunto de exercícios, incluindo séries, repetições e tempo de descanso.
- `exercise_set_exercises` é uma tabela de junção que mapeia muitos-para-muitos entre conjuntos de exercícios e exercícios.
- `workouts` define treinos, associando um conjunto de exercícios e uma associação específica.
- `workout_records` registra o progresso dos treinos, associando cada exercício a um treino específico e marcando se foi completado.

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
    student_id integer [not null]
    plan_id integer
    start_date date [not null]
    current_workout_id integer
    card_number varchar(19) [not null]
    card_holder_name varchar(100) [not null]
    card_expiry_date varchar(5) [not null]
    card_cvv varchar(4) [not null]

    Indexes {
        student_id
        plan_id
    }
}

Table workouts {
    id serial [pk, not null]
    exercise_sets_id integer [not null]
    membership_id integer
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
    series_count integer [not null]
    min_reps integer [not null]
    max_reps integer [not null]
    rest_time integer [not null]
}

Table workout_records {
    id serial [pk, not null]
    workout_id integer [not null]
    exercise_id integer [not null]
    exercise_completed boolean [not null]
    load integer [not null]
    completion_date date
}

Table exercise_set_exercises {
    exercise_set_id integer [not null]
    exercise_id integer [not null]

    Indexes {
        (exercise_set_id, exercise_id) [unique]
    }
}

Ref: memberships.student_id - students.id [delete: cascade, update: no action]
Ref: memberships.plan_id > plans.id [delete: set null, update: no action]
Ref: workouts.exercise_sets_id > exercise_sets.id [delete: set null, update: no action]
Ref: workouts.membership_id - memberships.id [delete: cascate, update: no action]
Ref: memberships.current_workout_id - workouts.id [delete: set null, update: no action]

Ref: workout_records.workout_id > workouts.id [delete: cascade, update: no action]
Ref: workout_records.exercise_id > exercises.id [delete: cascade, update: no action]
Ref: exercise_set_exercises.exercise_set_id > exercise_sets.id [delete: cascade, update: no action]
Ref: exercise_set_exercises.exercise_id > exercises.id [delete: cascade, update: no action]
```
