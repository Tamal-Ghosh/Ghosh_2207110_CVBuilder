package com.example.ghosh_2207110_cvbuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper {

    private static final String DB_URL = "jdbc:sqlite:cv.db";
    private static Connection connection;

    public static void initDataBase() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
                String create = "CREATE TABLE IF NOT EXISTS cv (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT, email TEXT, phone TEXT, address TEXT," +
                        "education TEXT, skills TEXT, work TEXT, project TEXT, image_path TEXT" +
                        ")";
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute(create);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static PersonCV insertCV(String name, String email, String phone, String address,
                                    String education, String skills, String work, String project,
                                    String imagePath) {
        String sql = "INSERT INTO cv(name, email, phone, address, education, skills, work, project, image_path) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, address);
            ps.setString(5, education);
            ps.setString(6, skills);
            ps.setString(7, work);
            ps.setString(8, project);
            ps.setString(9, imagePath);
            ps.executeUpdate();

            long id = -1;
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    id = rs.getLong(1);
                }
            }

            return new PersonCV(id, name, email, phone, address, education, skills, work, project, imagePath);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<PersonCV> getAllCV() {
        List<PersonCV> list = new ArrayList<>();
        String sql = "SELECT * FROM cv ORDER BY id DESC";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new PersonCV(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("education"),
                        rs.getString("skills"),
                        rs.getString("work"),
                        rs.getString("project"),
                        rs.getString("image_path")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static void updateCV(PersonCV cv) {
        String sql = "UPDATE cv SET name = ?, email = ?, phone = ?, address = ?, education = ?, skills = ?, work = ?, project = ?, image_path = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cv.getName());
            ps.setString(2, cv.getEmail());
            ps.setString(3, cv.getPhone());
            ps.setString(4, cv.getAddress());
            ps.setString(5, cv.getEducation());
            ps.setString(6, cv.getSkills());
            ps.setString(7, cv.getWork());
            ps.setString(8, cv.getProject());
            ps.setString(9, cv.getImagePath());
            ps.setLong(10, cv.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteCV(long id) {
        String sql = "DELETE FROM cv WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
