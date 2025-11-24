package com.example.ghosh_2207110_cvbuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper {
    private static final String DB_URL="jdbc:sqlite.cv_data.db";
    private static Connection connection;

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS cv_info (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "email TEXT," +
                    "phone TEXT," +
                    "address TEXT," +
                    "education TEXT," +
                    "skills TEXT," +
                    "work TEXT," +
                    "project TEXT," +
                    "image_path TEXT" +
                    ");";
    public static void initDataBase(){
        try {
            if(connection==null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
                createTable();
            }
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static void createTable()
    {
        try (PreparedStatement statement=connection.prepareStatement(CREATE_TABLE_SQL)) {
            statement.execute();

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static PersonCV insertCV(
            String name, String email, String phone, String address,
            String education, String skills, String work,
            String project, String imagePath
    ) throws SQLException {
        String sql = "INSERT INTO cv_info " +
                "(name, email, phone, address, education, skills, work, project, image_path) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = DriverManager.getConnection(DB_URL);
        PreparedStatement statement=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.setString(4, address);
            statement.setString(5, education);
            statement.setString(6, skills);
            statement.setString(7, work);
            statement.setString(8, project);
            statement.setString(9, imagePath);
            statement.executeUpdate();

            try(ResultSet resultSet=statement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    int id=resultSet.getInt(1);
                    return new PersonCV(id, name, email, phone, address, education, skills, work, project, imagePath);

                }

            }
        }
        throw new SQLException("Failed to insert cv_info");

    }
    public static List<PersonCV> getAllCV() throws SQLException {
        List<PersonCV> list = new ArrayList<>();
        String sql = "SELECT * FROM cv_info ORDER BY id ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new PersonCV(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("phone"), rs.getString("address"), rs.getString("education"), rs.getString("skills"), rs.getString("work"), rs.getString("project"), rs.getString("image_path")));
            }
        }
        return list;
    }

    public static void updateCV(PersonCV cv) throws SQLException {
        String sql = "UPDATE cv_info SET name=?, email=?, phone=?, address=?, education=?, skills=?, work=?, project=?, image_path=? WHERE id=?";
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
            ps.setInt(10, cv.getId());
            ps.executeUpdate();
        }
    }

    public static void deleteCV(int id) throws SQLException {
        String sql = "DELETE FROM cv_info WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

}
