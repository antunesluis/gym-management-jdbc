package org.gym;

import org.gym.dao.DaoFactory;
import org.gym.dao.ExerciseDao;
import org.gym.dao.PlanDao;
import org.gym.dao.StudentDao;
import org.gym.model.Exercise;
import org.gym.model.Plan;
import org.gym.model.Student;
import org.gym.util.DB;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StudentDao student = DaoFactory.createStudentDao();
        LocalDate birthday = LocalDate.of(1990, 1, 1);

        System.out.println("\nBuscando Estudante por id: ");
        Student e1 = student.getStudentById(1);
        System.out.println(e1);

        System.out.println("\nBuscando Estudante por cpf: ");
        Student e2 = student.getStudentByCpf("021");
        System.out.println(e2);

        System.out.println("\nBuscando Todos os estudantes: ");
        List<Student> students = student.getAllStudents();
        for (Student s : students) {
            System.out.println(s);
        }

        /*
        System.out.println("\nCriando usuário");
        Student e3 = new Student(null, "423423", "LUISAO", birthday );
        student.addStudent(e3);
         */

        System.out.println("-------------------- Exercicios --------------------- ");
        ExerciseDao exercise = DaoFactory.createExerciseDao();

        System.out.println("\nCriando Exercicio");
        Exercise ex = new Exercise(null, "juaozao", "coraçao e biceps" );
        exercise.addExercise(ex);

        System.out.println("\nBuscando exercicio por id: ");
        Exercise ex1 = exercise.getExerciseById(1);
        System.out.println(ex1);

        System.out.println("\nBuscando Todos os exercicios: ");
        List<Exercise> exercises = exercise.getAllExercises();
        for (Exercise e : exercises) {
            System.out.println(e);
        }

        System.out.println("-------------------- Planos --------------------- ");
        PlanDao plan = DaoFactory.createPlanDao();

        System.out.println("\nCriando Plano");
        Plan p1 = new Plan(null, "caludio", 10.32 );
        plan.addPlan(p1);

        System.out.println("\nAtualizando Plano");
        p1.setName("caludio novao");
        plan.updatePlan(p1);

        System.out.println("\nBuscando Plano por id: ");
        Plan p2 = plan.getPlanById(1);
        System.out.println(p2);

        System.out.println("\nBuscando Todos os Planos: ");
        List<Plan> plans = plan.getAllPlans();
        for (Plan p : plans) {
            System.out.println(p);
        }
    }
}