package client;


import context.AppContext;
import model.User;
import service.SubscriberService;
import service.SubscriberServiceInterface;
import service.UserService;
import service.UserServiceInterface;

import java.io.BufferedReader;
import java.io.IOException;

public class Client {

    private BufferedReader in;
    private SubscriberServiceInterface subscriberService;
    private UserServiceInterface userService;

    private Client() {
        in = AppContext.getAppContext().getReaderInstance();
        subscriberService = SubscriberService.getInstance();
        userService = UserService.getInstance();
    }

    private void dashboard() {
        while (true) {
            int choice = 0;
            System.out.print("1. Subscribe to channel\n2. Exit\nPlease choose: ");
            try {
                choice = Integer.parseInt(in.readLine());
                if (choice < 1 || choice > 4) {
                    throw new IOException("Index out of range");
                }
            } catch (Exception e) {
                System.out.println(">>> Please input 1 or 2");
            }
            switch (choice) {
                case 1:
                    subscriberService.prompt();
                    break;
                case 2:
                    System.exit(1);
            }
        }
    }

    public boolean authenticate() {
        while (true) {
            int choice = 0;
            System.out.print("1. Log in\n2. Register\n3. Exit\nPlease choose: ");
            try {
                choice = Integer.parseInt(in.readLine());
                if (choice < 1 || choice > 3) {
                    throw new IOException("Your choice is out of range");
                }
            } catch (Exception e) {
                System.out.println(">>> Please input 1 to 3");
            }
            switch (choice) {
                case 1:
                    User user = userService.logIn();
                    if (user == null) {
                        System.out.println("Wrong username or password");
                        break;
                    }
                    return true;
                case 2:
                    Long userId = userService.register();
                    if (userId == 0) break;
                    return true;
                case 3:
                    System.exit(1);
            }
        }
    }

    public void start() {
        if (authenticate()) {
            System.out.println("Log in successfully");
            dashboard();
        }
    }


    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
}
