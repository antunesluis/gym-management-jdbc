package org.gym.dao;

import org.gym.model.Exercise;
import org.gym.model.Plan;
import org.gym.model.Student;
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
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO plans (name, monthly_fee) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, plan.getName());
            st.setDouble(2, plan.getMonthlyFee());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    plan.setId(id);
                    DB.closeResultSet(rs);
                    return;
                }
            }
            throw new DbException("Unexpected error! No rows affected.");
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void updatePlan(Plan plan) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "UPDATE plans SET name = ?, monthly_fee = ? WHERE id = ?"
            );
            st.setString(1, plan.getName());
            st.setDouble(2, plan.getMonthlyFee());
            st.setInt(3, plan.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deletePlanById ( int id){
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "DELETE FROM plans WHERE id = ?"
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
    public Plan getPlanById ( int id){
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT id, name, monthly_fee "
                            + "FROM plans "
                            + "WHERE id = ?"
            );

            st.setInt(1, id);  // Define o parâmetro como um inteiro
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiatPlan(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Plan> getAllPlans () {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Plan> plans = new ArrayList<>();

        try {
            st = conn.prepareStatement(
                    "SELECT id, name, monthly_fee FROM plans"
            );
            rs = st.executeQuery();

            while (rs.next()) {
                Plan plan = instantiatPlan(rs);
                plans.add(plan);
            }
            return plans;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Plan instantiatPlan (ResultSet rs) throws SQLException {
        Plan plan = new Plan();
        plan.setId(rs.getInt("id"));
        plan.setName(rs.getString("name"));
        plan.setMonthlyFee(rs.getDouble("monthly_fee"));
        return plan;
    }
}
