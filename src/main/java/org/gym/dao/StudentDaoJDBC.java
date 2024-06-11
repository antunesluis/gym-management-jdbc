package org.gym.dao;

import org.gym.model.Student;
import org.gym.util.DB;
import org.gym.util.DbException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoJDBC implements StudentDao {

    private Connection conn;

    public StudentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addStudent(Student student) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO students (cpf, name, birth_date) VALUES (?, ?, ?)" ,
                    Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, student.getCpf());
            st.setString(2, student.getName());
            st.setDate(3, Date.valueOf(student.getBirthDate()));

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    student.setId(id);
                    DB.closeResultSet(rs);
                    return;
                }
            }
            throw new DbException("Unexpected error! No rows affected.");
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void updateStudent(Student student) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "UPDATE students SET cpf = ?, name = ?, birth_date = ? WHERE id = ?"
            );
            st.setString(1, student.getCpf());
            st.setString(2, student.getName());
            st.setDate(3, Date.valueOf(student.getBirthDate()));
            st.setInt(4, student.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteStudentById(int id) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "DELETE FROM students WHERE id = ?"
            );
            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Student getStudentById(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT id, cpf, name, birth_date "
                    + "FROM students "
                    + "WHERE id = ?"
            );

            st.setInt(1, id);  // Define o parâmetro como um inteiro
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateStudent(rs);
            }
            return null;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Student getStudentByCpf(String cpf) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT id, cpf, name, birth_date "
                    + "FROM students "
                    + "WHERE cpf = ?"
            );

            st.setString(1, cpf);  // Define o parâmetro como uma string
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateStudent(rs);
            }
            return null;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Student> getAllStudents() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Student> students = new ArrayList<>();

        try {
            st = conn.prepareStatement(
                    "SELECT id, cpf, name, birth_date FROM students"
            );
            rs = st.executeQuery();

            while (rs.next()) {
                Student student = instantiateStudent(rs);
                students.add(student);
            }
            return students;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
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
