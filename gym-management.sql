CREATE TABLE "students" (
  "id" serial PRIMARY KEY NOT NULL,
  "cpf" varchar(14) UNIQUE NOT NULL,
  "name" varchar(100) NOT NULL,
  "birth_date" date NOT NULL
);

CREATE TABLE "plans" (
  "id" serial PRIMARY KEY NOT NULL,
  "name" varchar(50) NOT NULL,
  "monthly_fee" decimal(10,2) NOT NULL
);

CREATE TABLE "exercises" (
  "id" serial PRIMARY KEY NOT NULL,
  "name" varchar(100) NOT NULL,
  "muscles_activated" varchar(255) NOT NULL
);

CREATE TABLE "memberships" (
  "id" serial PRIMARY KEY NOT NULL,
  "student_id" integer NOT NULL,
  "plan_id" integer,
  "start_date" date NOT NULL,
  "current_workout_id" integer,
  "card_number" varchar(19) NOT NULL,
  "card_holder_name" varchar(100) NOT NULL,
  "card_expiry_date" varchar(5) NOT NULL,
  "card_cvv" varchar(4) NOT NULL
);

CREATE TABLE "workouts" (
  "id" serial PRIMARY KEY NOT NULL,
  "exercise_sets_id" integer NOT NULL,
  "membership_id" integer,
  "name" varchar(100) NOT NULL,
  "start_date" date NOT NULL,
  "end_date" date
);

CREATE TABLE "exercise_sets" (
  "id" serial PRIMARY KEY NOT NULL,
  "series_count" integer NOT NULL,
  "min_reps" integer NOT NULL,
  "max_reps" integer NOT NULL,
  "rest_time" integer NOT NULL
);

CREATE TABLE "workout_records" (
  "id" serial PRIMARY KEY NOT NULL,
  "workout_id" integer NOT NULL,
  "exercise_id" integer NOT NULL,
  "exercise_completed" boolean NOT NULL,
  "load" integer NOT NULL,
  "completion_date" date
);

CREATE TABLE "exercise_set_exercises" (
  "exercise_set_id" integer NOT NULL,
  "exercise_id" integer NOT NULL
);

CREATE INDEX ON "memberships" ("student_id");

CREATE INDEX ON "memberships" ("plan_id");

CREATE INDEX ON "workouts" ("membership_id");

CREATE INDEX ON "workouts" ("exercise_sets_id");

CREATE UNIQUE INDEX ON "exercise_set_exercises" ("exercise_set_id", "exercise_id");

ALTER TABLE "students" ADD FOREIGN KEY ("id") REFERENCES "memberships" ("student_id") ON DELETE CASCADE ON UPDATE NO ACTION;

ALTER TABLE "memberships" ADD FOREIGN KEY ("plan_id") REFERENCES "plans" ("id") ON DELETE SET NULL ON UPDATE NO ACTION;

ALTER TABLE "workouts" ADD FOREIGN KEY ("exercise_sets_id") REFERENCES "exercise_sets" ("id") ON DELETE SET NULL ON UPDATE NO ACTION;

ALTER TABLE "memberships" ADD FOREIGN KEY ("id") REFERENCES "workouts" ("membership_id") ON DELETE CASCATE ON UPDATE NO ACTION;

ALTER TABLE "workouts" ADD FOREIGN KEY ("id") REFERENCES "memberships" ("current_workout_id") ON DELETE SET NULL ON UPDATE NO ACTION;

ALTER TABLE "workout_records" ADD FOREIGN KEY ("workout_id") REFERENCES "workouts" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

ALTER TABLE "workout_records" ADD FOREIGN KEY ("exercise_id") REFERENCES "exercises" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

ALTER TABLE "exercise_set_exercises" ADD FOREIGN KEY ("exercise_set_id") REFERENCES "exercise_sets" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

ALTER TABLE "exercise_set_exercises" ADD FOREIGN KEY ("exercise_id") REFERENCES "exercises" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

ALTER TABLE "workouts" ADD FOREIGN KEY ("start_date") REFERENCES "workouts" ("membership_id");
