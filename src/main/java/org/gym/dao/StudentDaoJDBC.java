package org.gym.dao;

import org.gym.model.Student;

import java.util.List;

public class StudentDaoJDBC implements StudentDao {
    @Override
    public void addStudent(Student student) {

    }

    @Override
    public void updateStudent(Student student) {

    }

    @Override
    public void deleteStudentById(int id) {

    }

    @Override
    public Student getStudentById(int id) {
        return null;
    }

    @Override
    public Student getStudentByCpf(String cpf) {
        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        return List.of();
    }
}
