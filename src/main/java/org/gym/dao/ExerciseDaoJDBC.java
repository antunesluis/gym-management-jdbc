package org.gym.dao;

import org.gym.model.Exercise;
import org.gym.model.Student;
import org.gym.util.DB;
import org.gym.util.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDaoJDBC implements ExerciseDao {
    private Connection conn;

    public ExerciseDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addExercise(Exercise exercise) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO exercises (name, muscles_activated) VALUES (?, ?)" ,
                    Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, exercise.getName());
            st.setString(2, exercise.getMusclesActivates());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    exercise.setId(id);
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
    public void updateExercise(Exercise exercise) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "UPDATE exercises SET name = ?, muscles_activated = ? WHERE id = ?"
            );
            st.setString(1, exercise.getName());
            st.setString(2, exercise.getMusclesActivates());
            st.setInt(3, exercise.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteExerciseById(int id) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "DELETE FROM exercises WHERE id = ?"
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
    public Exercise getExerciseById(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT id, name, muscles_activated "
                            + "FROM exercises "
                            + "WHERE id = ?"
            );

            st.setInt(1, id);  // Define o parâmetro como um inteiro
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateExercise(rs);
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
    public List<Exercise> getAllExercises() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Exercise> exercises = new ArrayList<>();

        try {
            st = conn.prepareStatement(
                    "SELECT id, name, muscles_activated FROM exercises"
            );
            rs = st.executeQuery();

            while (rs.next()) {
                Exercise exercise = instantiateExercise(rs);
                exercises.add(exercise);
            }
            return exercises;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Exercise instantiateExercise(ResultSet rs) throws SQLException {
        Exercise exercise =  new Exercise();
        exercise.setId(rs.getInt("id"));
        exercise.setName(rs.getString("name"));
        exercise.setMusclesActivates(rs.getString("muscles_activated"));
        return exercise;
    }
}
