package org.gym.dao;

import org.gym.model.Plan;
import org.gym.util.DB;
import org.gym.util.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanDaoJDBC implements PlanDao {
    private Connection conn;

    public PlanDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addPlan(Plan plan) {
        String sql = "INSERT INTO plans (name, monthly_fee) VALUES (?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, plan.getName());
            st.setDouble(2, plan.getMonthlyFee());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected.");
            }

            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    plan.setId(id);
                }
            }
        } catch (SQLException e) {
            throw new DbException("Error while adding plan: " + e.getMessage());
        }
    }

    @Override
    public void updatePlan(Plan plan) {
        String sql = "UPDATE plans SET name = ?, monthly_fee = ? WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, plan.getName());
            st.setDouble(2, plan.getMonthlyFee());
            st.setInt(3, plan.getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected.");
            }
        } catch (SQLException e) {
            throw new DbException("Error while updating plan: " + e.getMessage());
        }
    }

    @Override
    public void deletePlanById(int id) {
        String sql = "DELETE FROM plans WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected.");
            }
        } catch (SQLException e) {
            throw new DbException("Error while deleting plan: " + e.getMessage());
        }
    }

    @Override
    public Plan getPlanById(int id) {
        String sql = "SELECT id, name, monthly_fee FROM plans WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiatePlan(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DbException("Error while getting plan by id: " + e.getMessage());
        }
    }

    @Override
    public List<Plan> getAllPlans() {
        String sql = "SELECT id, name, monthly_fee FROM plans";

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            List<Plan> plans = new ArrayList<>();

            while (rs.next()) {
                plans.add(instantiatePlan(rs));
            }

            return plans;
        } catch (SQLException e) {
            throw new DbException("Error while getting all plans: " + e.getMessage());
        }
    }

    @Override
    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM plans WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DbException("Error checking plan ID: " + e.getMessage());
        }
    }

    private Plan instantiatePlan(ResultSet rs) throws SQLException {
        Plan plan = new Plan();
        plan.setId(rs.getInt("id"));
        plan.setName(rs.getString("name"));
        plan.setMonthlyFee(rs.getDouble("monthly_fee"));
        return plan;
    }
}