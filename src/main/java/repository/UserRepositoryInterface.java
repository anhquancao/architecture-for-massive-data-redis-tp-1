package repository;

import model.User;

public interface UserRepositoryInterface {
    Long save(String username, String password);
    User findUserByUsernameAndPassword(String username, String password);
    boolean userExist(String username);
}
