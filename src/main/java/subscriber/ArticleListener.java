package subscriber;

import model.Article;
import redis.clients.jedis.JedisPubSub;
import repository.ArticleRepository;

public class ArticleListener extends JedisPubSub {
    @Override
    public void onMessage(String channel, String id) {
        Article article = ArticleRepository.getInstance().findArticleById(Long.valueOf(id));
        System.out.println("===============");
        System.out.println(channel);
        System.out.println("Article id: " + id);
        System.out.println("Article body: " + article.getBody());
    }
}
