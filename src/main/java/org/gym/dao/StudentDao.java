package org.gym.dao;

import org.gym.model.Student;

import java.util.List;

public interface StudentDao {
    void addStudent(Student student);
    void updateStudent(Student student);
    void deleteStudentById(int id);
    Student getStudentById(int id);
    Student getStudentByCpf(String cpf);
    List<Student> getAllStudents();
}
