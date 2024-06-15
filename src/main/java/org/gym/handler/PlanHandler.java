package org.gym.handler;

import org.gym.dao.DaoFactory;
import org.gym.dao.PlanDao;

import java.sql.Connection;
import java.util.Scanner;

public class PlanHandler implements UserActionHandler {
    private final Scanner scanner;
    private final PlanDao planDao;

    public PlanHandler(Connection conn, Scanner scanner) {
        this.scanner = scanner;
        this.planDao = DaoFactory.createPlanDao(conn);
    }

    @Override
    public void execute() {

    }
}
