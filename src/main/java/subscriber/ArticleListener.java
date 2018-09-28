package subscriber;

import redis.clients.jedis.JedisPubSub;

public class ArticleListener extends JedisPubSub {
    @Override
    public void onMessage(String channel, String article) {
        System.out.println("===============");
        System.out.println(channel);
        System.out.println("Article: " + article);
    }
}
