package server;

import service.ArticleService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Server {
    private ArticleService articleService;
    private BufferedReader in;


    private Server() {
        articleService = new ArticleService();
        in = new BufferedReader(new InputStreamReader(System.in));
    }

    private void start(){
        while (true) {
            int choice = 0;
            System.out.print("1. Show all articles\n2. Create a new article\n3. Exit\nPlease choose: ");
            try {
                choice = Integer.parseInt(in.readLine());
                if (choice < 1 || choice > 3) {
                    throw new IOException("Index out of range");
                }
            } catch (Exception e) {
                System.out.println(">>> Please input 1 to 3");
            }
            switch (choice) {
                case 1:
                    articleService.showAllArticle();
                    break;
                case 2:
                    articleService.createArticle();
                    break;
                case 3:
                    System.exit(1);
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
