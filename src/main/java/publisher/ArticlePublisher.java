package publisher;

import model.Article;
import redis.clients.jedis.Jedis;

public class ArticlePublisher implements ArticlePublisherInterface {
    private Jedis jedis;

    public ArticlePublisher() {
        this.jedis = new Jedis();
    }

    public void publishArticle(String tag, Article article) {
        String channel = "channel:" + tag;
        jedis.publish(channel, article.getBody());
        System.out.println("PUBLISH to " + channel);
    }
}
