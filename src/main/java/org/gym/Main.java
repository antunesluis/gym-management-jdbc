package org.gym;

import org.gym.util.ConnectionDb;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = ConnectionDb.getConnection();
        ConnectionDb.closeConnection();
    }
}