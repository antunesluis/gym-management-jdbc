package org.gym;

import org.gym.dao.DaoFactory;
import org.gym.dao.StudentDao;
import org.gym.model.Student;
import org.gym.util.DB;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StudentDao student = DaoFactory.createStudentDao();

        System.out.println("Buscando Estudante por id: ");
        Student e1 = student.getStudentById(1);
        System.out.println(e1);
        System.out.println("Buscando Estudante por cpf: ");
        Student e2 = student.getStudentByCpf("021");
        System.out.println(e2);

        System.out.println("Buscando Todos os estudantes: ");
        List<Student> students = student.getAllStudents();
        for (Student s : students) {
            System.out.println(s);
        }
    }
}