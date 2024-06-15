package org.gym.handler;

import org.gym.dao.DaoFactory;
import org.gym.dao.MembershipDao;

import java.sql.Connection;
import java.util.Scanner;

public class MembershipHandler implements UserActionHandler {
    private final Scanner scanner;
    private final MembershipDao membershipDao;

    public MembershipHandler(Connection conn, Scanner scanner) {
        this.scanner = scanner;
        this.membershipDao = DaoFactory.createMembershipDao(conn);
    }

    @Override
    public void execute() {

    }
}
