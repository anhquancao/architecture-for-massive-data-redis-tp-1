package service;

import context.AppContext;
import subscriber.ArticleListener;
import subscriber.SubClient;

import java.io.BufferedReader;
import java.io.IOException;

public class SubscriberService {
    private BufferedReader in;

    private static SubscriberService subscriberService;

    public static SubscriberService getInstance() {
        if (subscriberService == null) {
            subscriberService = new SubscriberService();
        }
        return subscriberService;
    }

    private SubscriberService() {
        in = AppContext.getAppContext().getReaderInstance();
    }

    private void subscribe(String tag) {
        ArticleListener articleListener = new ArticleListener();
        SubClient subClient = new SubClient(articleListener);
        subClient.setChannel("channel:" + tag);
        subClient.start();
    }

    public void prompt() {
        System.out.print("Please input a tag: ");
        try {
            String tag = in.readLine();
            subscribe(tag);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
