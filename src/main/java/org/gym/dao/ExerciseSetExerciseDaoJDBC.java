package org.gym.dao;

import org.gym.model.ExerciseSetExercise;
import org.gym.util.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ExerciseSetExerciseDaoJDBC implements ExerciseSetExerciseDao {

    private Connection conn;

    public ExerciseSetExerciseDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(ExerciseSetExercise exerciseSetExercise) {
        String sql = "INSERT INTO exercise_set_exercises (exercise_set_id, exercise_id) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, exerciseSetExercise.getExerciseSetId());
            ps.setInt(2, exerciseSetExercise.getExerciseId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void deleteByExerciseSetId(int exerciseSetId) {
        String sql = "DELETE FROM exercise_set_exercises WHERE exercise_set_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, exerciseSetId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<ExerciseSetExercise> findByExerciseSetId(int exerciseSetId) {
        String sql = "SELECT * FROM exercise_set_exercises WHERE exercise_set_id = ?";

        List<ExerciseSetExercise> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, exerciseSetId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ExerciseSetExercise ese = new ExerciseSetExercise();
                ese.setExerciseSetId(rs.getInt("exercise_set_id"));
                ese.setExerciseId(rs.getInt("exercise_id"));
                list.add(ese);
            }
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
        return list;
    }
}