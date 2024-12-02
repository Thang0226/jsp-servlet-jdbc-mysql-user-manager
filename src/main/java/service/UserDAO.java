package service;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/demo";
	private String jdbcUsername = "root";
	private String jdbcPassword = "123456";

	private static final String INSERT_USER_SQL = "INSERT INTO users (name, email, country) VALUES (?, ?, ?);";
	private static final String SELECT_USERS_BY_ID = "SELECT id,name,email,country FROM users WHERE id =?";
	private static final String SELECT_ALL_USERS = "select * from users";
	private static final String DELETE_USER_SQL = "delete from users where id = ?;";
	private static final String UPDATE_USER_SQL = "update users set name = ?,email= ?, country =? where id = ?;";
	private static final String SELECT_USERS_BY_COUNTRY = "SELECT id,name,email,country FROM users WHERE country =?";

	public UserDAO() {
	}

	protected Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		return connection;
	}

	@Override
	public void insertUser(User user) throws SQLException {
		System.out.println(INSERT_USER_SQL);
		try (Connection connection = getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL))
		{
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	@Override
	public User selectUser(int id) {
		User user = null;
		try (Connection connection = getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USERS_BY_ID))
		{
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				user = new User(id, name, email, country);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return user;
	}

	@Override
	public List<User> selectAllUsers() {
		List<User> users = new ArrayList<>();
		try (Connection connection = getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS))
		{
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				users.add(new User(id, name, email, country));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return users;
	}

	public List<User> selectUsersByCountry(String in_country) {
		List<User> result_users = new ArrayList<>();
		try (Connection connection = getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USERS_BY_COUNTRY))
		{
			System.out.println(preparedStatement);
			preparedStatement.setString(1, in_country);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				result_users.add(new User(id, name, email, country));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return result_users;
	}

	@Override
	public boolean deleteUser(int id) throws SQLException {
		boolean rowDeleted = false;
		try (Connection connection = getConnection();
		     PreparedStatement statement = connection.prepareStatement(DELETE_USER_SQL))
		{
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		} catch (SQLException e) {
			printSQLException(e);
		}
		return rowDeleted;
	}

	@Override
	public boolean updateUser(User user) throws SQLException {
		boolean rowUpdated = false;
		try (Connection connection = getConnection();
		     PreparedStatement statement = connection.prepareStatement(UPDATE_USER_SQL))
		{
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getCountry());
			statement.setInt(4, user.getId());
			rowUpdated = statement.executeUpdate() > 0;
		} catch (SQLException e) {
			printSQLException(e);
		}
		return rowUpdated;
	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
}
