package org.gym.dao;

import org.gym.model.Plan;

import java.util.List;

public interface PlanDao {
    void addPlan(Plan plan);
    void updatePlan(Plan plan);
    void deletePlanById(int id);
    Plan getPlanById(int id);
    List<Plan> getAllPlans();
}
