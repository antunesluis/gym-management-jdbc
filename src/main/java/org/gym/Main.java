package org.gym;

import org.gym.util.DB;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = DB.getConnection();
        DB.closeConnection();
    }
}