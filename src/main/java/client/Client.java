package client;


import model.User;
import repository.UserRepository;
import repository.UserRepositoryInterface;
import service.SubscriberService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {

    private BufferedReader in;
    private SubscriberService subscriberService;
    private UserRepositoryInterface userRepository;

    private Client() {
        in = new BufferedReader(new InputStreamReader(System.in));
        subscriberService = new SubscriberService();
        userRepository = new UserRepository();
    }

    public void dashboard() {
        while (true) {
            int choice = 0;
            System.out.print("1. Subscribe to channel\n2. Create a new article\n3. Exit\nPlease choose: ");
            try {
                choice = Integer.parseInt(in.readLine());
                if (choice < 1 || choice > 4) {
                    throw new IOException("Index out of range");
                }
            } catch (Exception e) {
                System.out.println(">>> Please input 1 to 4");
            }
            switch (choice) {
                case 1:
                    System.out.print("Please input a tag: ");
                    try {
                        String tag = in.readLine();
                        subscriberService.subscribe(tag);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                case 2:
//                    articleService.createArticle();
                    break;
                case 3:
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
                    try {
                        System.out.println("Please input your username");
                        String username = in.readLine();
                        System.out.println("Please input your password");
                        String password = in.readLine();
                        User user = userRepository.findUserByUsernameAndPassword(username, password);
                        if (user == null) return false;
                        return true;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                case 2:
                    try {
                        System.out.println("Please input your username");
                        String username = in.readLine();
                        if (userRepository.userExist(username)) {
                            System.out.println("Username exist, Please another username");
                            break;
                        }
                        System.out.println("Please input your password");
                        String password = in.readLine();
                        userRepository.save(username, password);
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                case 3:
                    System.exit(1);
            }
        }
    }

    public void start() {
        authenticate();

    }


    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
}
