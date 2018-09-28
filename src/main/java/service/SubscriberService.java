package service;

import subscriber.ArticleListener;
import subscriber.SubClient;

public class SubscriberService {
    public void subscribe(String tag) {
        ArticleListener articleListener = new ArticleListener();
        SubClient subClient = new SubClient(articleListener);
        subClient.setChannel("channel:" + tag);
        subClient.start();
    }
}
