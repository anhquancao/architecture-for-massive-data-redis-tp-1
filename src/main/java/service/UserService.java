package service;

import context.AppContext;
import model.User;
import repository.UserRepository;
import repository.UserRepositoryInterface;

import java.io.BufferedReader;
import java.io.IOException;

public class UserService {
    private UserRepositoryInterface userRepository;
    private BufferedReader in;

    private static UserService userService;

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    private UserService() {
        userRepository = UserRepository.getInstance();
        in = AppContext.getAppContext().getReaderInstance();
    }

    public User logIn() {
        try {
            System.out.println("Please input your username");
            String username = in.readLine();
            System.out.println("Please input your password");
            String password = in.readLine();
            return userRepository.findUserByUsernameAndPassword(username, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long register() {
        try {
            System.out.println("Please input your username");
            String username = in.readLine();
            if (userRepository.userExist(username)) {
                System.out.println("Username exist, Please another username");
                return 0L;
            }
            System.out.println("Please input your password");
            String password = in.readLine();
            return userRepository.save(username, password);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0L;

    }
}
