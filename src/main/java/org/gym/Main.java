package org.gym;
import org.gym.util.DB;
import org.gym.util.DbException;
import org.gym.util.GymManagementConsole;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DB.getConnection()) {
            GymManagementConsole console = new GymManagementConsole(conn);
            console.start();
        } catch (Exception e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}