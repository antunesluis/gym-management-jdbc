package org.gym.dao;

import org.gym.model.Student;
import org.gym.util.DB;
import org.gym.util.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoJDBC implements StudentDao {

    private Connection conn;

    public StudentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addStudent(Student student) {
        String sql = "INSERT INTO students (cpf, name, birth_date) VALUES (?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, student.getCpf());
            st.setString(2, student.getName());
            st.setDate(3, Date.valueOf(student.getBirthDate()));

            int rowsAffected = st.executeUpdate();

            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected.");
            }

            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    student.setId(id);
                }
            }
        } catch (SQLException e) {
            throw new DbException("Error while adding student: " + e.getMessage());
        }
    }

    @Override
    public void updateStudent(Student student) {
        String sql = "UPDATE students SET cpf = ?, name = ?, birth_date = ? WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, student.getCpf());
            st.setString(2, student.getName());
            st.setDate(3, Date.valueOf(student.getBirthDate()));
            st.setInt(4, student.getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected.");
            }
        } catch (SQLException e) {
            throw new DbException("Error while updating student: " + e.getMessage());
        }
    }

    @Override
    public void deleteStudentById(int id) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected.");
            }
        } catch (SQLException e) {
            throw new DbException("Error while deleting student: " + e.getMessage());
        }
    }

    @Override
    public Student getStudentById(int id) {
        String sql = "SELECT id, cpf, name, birth_date FROM students WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateStudent(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DbException("Error while getting student by id: " + e.getMessage());
        }
    }

    @Override
    public Student getStudentByCpf(String cpf) {
        String sql = "SELECT id, cpf, name, birth_date FROM students WHERE cpf = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, cpf);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateStudent(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DbException("Error while getting student by cpf: " + e.getMessage());
        }
    }

    @Override
    public List<Student> getAllStudents() {
        String sql = "SELECT id, cpf, name, birth_date FROM students";

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            List<Student> students = new ArrayList<>();

            while (rs.next()) {
                students.add(instantiateStudent(rs));
            }

            return students;
        } catch (SQLException e) {
            throw new DbException("Error while getting all students: " + e.getMessage());
        }
    }

    private Student instantiateStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setCpf(rs.getString("cpf"));
        student.setName(rs.getString("name"));
        student.setBirthDate(rs.getDate("birth_date").toLocalDate());
        return student;
    }
}