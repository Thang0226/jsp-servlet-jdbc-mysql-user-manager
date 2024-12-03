package DAO;

import model.User;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/demo";
	private String jdbcUsername = "root";
	private String jdbcPassword = "123456";

	private static final String INSERT_USER_SQL = "INSERT INTO users (name, email, country) VALUES (?, ?, ?);";
	private static final String SELECT_USERS_BY_ID = "SELECT id,name,email,country FROM users WHERE id =?";
	private static final String SELECT_ALL_USERS = "select * from users";
	private static final String DELETE_USER_SQL = "DELETE FROM users WHERE id = ?;";
	private static final String UPDATE_USER_SQL = "UPDATE users SET name = ?,email= ?, country =? WHERE id = ?;";
	private static final String SELECT_USERS_BY_COUNTRY = "SELECT id,name,email,country FROM users WHERE country =?";
	private static final String SELECT_USERS_SORTED_BY_NAME = "select * from users order by name;";
	private static final String SQL_INSERT_EMPLOYEE = "INSERT INTO EMPLOYEE (NAME, SALARY, CREATED_DATE) VALUES (?,?,?)";
	private static final String SQL_UPDATE_EMPLOYEE = "UPDATE EMPLOYEE SET SALARY=? WHERE NAME=?";
	private static final String SQL_CREATE_EMPLOYEE_TABLE = "CREATE TABLE EMPLOYEE"
			+ "("
			+ " ID serial,"
			+ " NAME varchar(100) NOT NULL,"
			+ " SALARY numeric(15, 2) NOT NULL,"
			+ " CREATED_DATE timestamp,"
			+ " PRIMARY KEY (ID)"
			+ ")";
	private static final String SQL_DROP_EMPLOYEE_TABLE = "DROP TABLE IF EXISTS EMPLOYEE";

	public UserDAO() {
	}

	protected Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");     // safer than DriverManager.registerDriver()
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
		     PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
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
		     PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USERS_BY_ID)) {
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
		return selectUsers(SELECT_ALL_USERS);
	}

	public List<User> selectUsersSortedByName() {
		return selectUsers(SELECT_USERS_SORTED_BY_NAME);
	}

	private List<User> selectUsers(String select_sql) {
		List<User> result_users = new ArrayList<>();
		try (Connection connection = getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(select_sql)) {
			System.out.println(preparedStatement);
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

	public List<User> selectUsersByCountry(String in_country) {
		List<User> result_users = new ArrayList<>();
		try (Connection connection = getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USERS_BY_COUNTRY)) {
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
		     PreparedStatement statement = connection.prepareStatement(DELETE_USER_SQL)) {
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
		     PreparedStatement statement = connection.prepareStatement(UPDATE_USER_SQL)) {
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

	@Override
	public User getUserById(int id) {
		User user = null;
		String query = "{CALL get_user_by_id(?)}";

		try (Connection connection = getConnection();
		     CallableStatement callableStatement = connection.prepareCall(query)) {
			callableStatement.setInt(1, id);
			ResultSet rs = callableStatement.executeQuery();

			if (rs.next()) {
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
	public void insertUserToStore(User user) throws SQLException {
		String query = "{CALL insert_user(?,?,?)}";
		try (Connection connection = getConnection();
		     CallableStatement callableStatement = connection.prepareCall(query)) {
			callableStatement.setString(1, user.getName());
			callableStatement.setString(2, user.getEmail());
			callableStatement.setString(3, user.getCountry());
			System.out.println(callableStatement);
			callableStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	@Override
	public void addUserTransaction(User user, List<Integer> permissions) {
		Connection conn = null;
		// for insert a new user
		PreparedStatement pstmt = null;
		// for assign permission to user
		PreparedStatement pstmtAssignment = null;
		// for getting user id
		ResultSet rs = null;
		try {
			conn = getConnection();

			// set auto commit to false
			conn.setAutoCommit(false);

			// Insert user
			pstmt = conn.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getCountry());
			int rowAffected = pstmt.executeUpdate();

			// get user id
			rs = pstmt.getGeneratedKeys();
			int userId = 0;
			if (rs.next())
				userId = rs.getInt(1);

			// in case the insert operation successes, assign permission to user
			if (rowAffected == 1) {
				// assign permission to user
				String sqlPivot = "INSERT INTO user_permission VALUES(?,?)";
				pstmtAssignment = conn.prepareStatement(sqlPivot);

				for (int permissionId : permissions) {
					pstmtAssignment.setInt(1, userId);
					pstmtAssignment.setInt(2, permissionId);
					pstmtAssignment.executeUpdate();
				}
				conn.commit();
			} else {
				// roll back the transaction
				conn.rollback();
			}

		} catch (SQLException ex) {
			// roll back the transaction
			try {
				if (conn != null)
					conn.rollback();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			System.out.println(ex.getMessage());
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (pstmtAssignment != null) pstmtAssignment.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				printSQLException(e);
			}
		}
	}

	@Override
	public void insertUpdateEmployeeWithoutTransaction() {
		Connection conn = null;
		try {
			conn = getConnection();
		     Statement statement = conn.createStatement();
		     PreparedStatement psInsert = conn.prepareStatement(SQL_INSERT_EMPLOYEE);
		     PreparedStatement psUpdate = conn.prepareStatement(SQL_UPDATE_EMPLOYEE);
			statement.execute(SQL_DROP_EMPLOYEE_TABLE);
			statement.execute(SQL_CREATE_EMPLOYEE_TABLE);

			// start transaction block
			conn.setAutoCommit(false); // default true

			// Run list of insert commands
			psInsert.setString(1, "Quynh");
			psInsert.setBigDecimal(2, new BigDecimal(10));
			psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			psInsert.execute();

			psInsert.setString(1, "Ngan");
			psInsert.setBigDecimal(2, new BigDecimal(20));
			psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			psInsert.execute();

			// Run list of update commands
			// below line caused error, test transaction
			// Message: No value specified for parameter 1.
			psUpdate.setBigDecimal(2, new BigDecimal(999.99));
			//psUpdate.setBigDecimal(1, new BigDecimal(999.99));
			psUpdate.setString(2, "Quynh");
			psUpdate.execute();

			// end transaction block, commit changes
			conn.commit();
			// good practice to set it back to default true
			conn.setAutoCommit(true);

		} catch (SQLException e) {
			try {
				if (conn != null)
					conn.rollback();
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
			printSQLException(e);
		}
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
