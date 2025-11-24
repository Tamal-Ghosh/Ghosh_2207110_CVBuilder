package com.example.ghosh_2207110_cvbuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper {
    private static final Path APP_DIR = Paths.get(System.getProperty("user.home"), ".cvbuilder");
    private static final Path DB_FILE = APP_DIR.resolve("cv.db");
    private static final String DB_URL;

    static {
        try {
            Files.createDirectories(APP_DIR);
        } catch (Exception e) {
            throw new RuntimeException("Unable to create DB directory", e);
        }
        DB_URL = "jdbc:sqlite:" + DB_FILE.toAbsolutePath().toString();
        System.out.println("Using DB: " + DB_FILE.toAbsolutePath());
    }

    public static void initDataBase() {
        createTable();
    }

    public static void createTable() {
        String create = "CREATE TABLE IF NOT EXISTS cv (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT, email TEXT, phone TEXT, address TEXT," +
                "education TEXT, skills TEXT, work TEXT, project TEXT, image_path TEXT" +
                ")";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(create);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static PersonCV insertCV(String name, String email, String phone, String address,
                                    String education, String skills, String work, String project,
                                    String imagePath) {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO cv(name, email, phone, address, education, skills, work, project, image_path) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
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
            }
            long id = -1;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    id = rs.getLong(1);
                }
            }
            conn.commit();
            return new PersonCV(id, name, email, phone, address, education, skills, work, project, imagePath);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<PersonCV> getAllCV() {
        List<PersonCV> list = new ArrayList<>();
        String sql = "SELECT id, name, email, phone, address, education, skills, work, project, image_path FROM cv ORDER BY id DESC";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String education = rs.getString("education");
                String skills = rs.getString("skills");
                String work = rs.getString("work");
                String project = rs.getString("project");
                String imagePath = rs.getString("image_path");
                list.add(new PersonCV(id, name, email, phone, address, education, skills, work, project, imagePath));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static void updateCV(PersonCV cv) {
        String sql = "UPDATE cv SET name = ?, email = ?, phone = ?, address = ?, education = ?, skills = ?, work = ?, project = ?, image_path = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
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
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}