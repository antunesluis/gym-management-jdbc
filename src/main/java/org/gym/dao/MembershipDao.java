package org.gym.dao;

import org.gym.model.Exercise;
import org.gym.model.Membership;

import java.util.List;

public interface MembershipDao {
    void addMembership(Membership membership);
    void updateMembership(Membership membership);
    void deleteMembershipById(int id);
    Membership getMembershipById(int id);
    List<Membership> getAllMemberships();
    boolean existsById(int id);
}
