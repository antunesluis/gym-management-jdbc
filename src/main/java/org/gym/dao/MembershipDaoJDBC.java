package org.gym.dao;

import org.gym.model.Membership;
import org.gym.util.DB;
import org.gym.util.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembershipDaoJDBC implements MembershipDao {
    private Connection conn;

    public MembershipDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addMembership(Membership membership) {
        String sql = "INSERT INTO memberships (student_id, plan_id, start_date, current_workout_id, card_number, card_holder_name, card_expiry_date, card_cvv) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, membership.getStudentId());
            st.setInt(2, membership.getPlanId());
            st.setDate(3, Date.valueOf(membership.getStartDate()));
            if (membership.getCurrentWorkoutId() != null) {
                st.setInt(4, membership.getCurrentWorkoutId());
            } else {
                st.setNull(4, Types.INTEGER);
            }
            st.setString(5, membership.getCardNumber());
            st.setString(6, membership.getCardHolderName());
            st.setString(7, membership.getCardExpiryDate());
            st.setString(8, membership.getCardCvv());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected.");
            }

            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    membership.setId(id);
                }
            }
        } catch (SQLException e) {
            throw new DbException("Error while adding membership: " + e.getMessage());
        }
    }

    @Override
    public void updateMembership(Membership membership) {
        String sql = "UPDATE memberships "
                + "SET student_id = ?, plan_id = ?, start_date = ?, current_workout_id = ?, card_number = ?, card_holder_name = ?, card_expiry_date = ?, card_cvv = ? "
                + "WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, membership.getStudentId());
            st.setInt(2, membership.getPlanId());
            st.setDate(3, Date.valueOf(membership.getStartDate()));
            if (membership.getCurrentWorkoutId() != null) {
                st.setInt(4, membership.getCurrentWorkoutId());
            } else {
                st.setNull(4, Types.INTEGER);
            }
            st.setString(5, membership.getCardNumber());
            st.setString(6, membership.getCardHolderName());
            st.setString(7, membership.getCardExpiryDate());
            st.setString(8, membership.getCardCvv());
            st.setInt(9, membership.getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected.");
            }
        } catch (SQLException e) {
            throw new DbException("Error while updating membership: " + e.getMessage());
        }
    }

    @Override
    public void deleteMembershipById(int id) {
        String sql = "DELETE FROM memberships WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException("Error while deleting membership: " + e.getMessage());
        }
    }

    @Override
    public Membership getMembershipById(int id) {
        String sql = "SELECT id, student_id, plan_id, start_date, current_workout_id, card_number, card_holder_name, card_expiry_date, card_cvv "
                + "FROM memberships WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateMembership(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DbException("Error while getting membership by id: " + e.getMessage());
        }
    }

    @Override
    public List<Membership> getAllMemberships() {
        String sql = "SELECT id, student_id, plan_id, start_date, current_workout_id, card_number, card_holder_name, card_expiry_date, card_cvv "
                + "FROM memberships";

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            List<Membership> memberships = new ArrayList<>();

            while (rs.next()) {
                memberships.add(instantiateMembership(rs));
            }

            return memberships;
        } catch (SQLException e) {
            throw new DbException("Error while getting all memberships: " + e.getMessage());
        }
    }

    @Override
    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM memberships WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DbException("Error checking membership ID: " + e.getMessage());
        }
    }

    private Membership instantiateMembership(ResultSet rs) throws SQLException {
        Membership membership = new Membership();
        membership.setId(rs.getInt("id"));
        membership.setStudentId(rs.getInt("student_id"));
        membership.setPlanId(rs.getInt("plan_id"));
        membership.setStartDate(rs.getDate("start_date").toLocalDate());
        membership.setCurrentWorkoutId(rs.getObject("current_workout_id", Integer.class));
        membership.setCardNumber(rs.getString("card_number"));
        membership.setCardHolderName(rs.getString("card_holder_name"));
        membership.setCardExpiryDate(rs.getString("card_expiry_date"));
        membership.setCardCvv(rs.getString("card_cvv"));
        return membership;
    }
}
