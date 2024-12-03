package DAO;

import model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
	void insertUser(User user) throws SQLException;

	User selectUser(int id);

	List<User> selectAllUsers();

	boolean deleteUser(int id) throws SQLException;

	boolean updateUser(User user) throws SQLException;

	User getUserById(int id);

	void insertUserToStore(User user) throws SQLException;

	void addUserTransaction(User user, List<Integer> permission);

	void insertUpdateEmployeeWithoutTransaction();
}
