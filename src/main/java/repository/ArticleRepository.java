package repository;

import model.Article;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ArticleRepository implements ArticleRepositoryInterface {
    private Jedis jedis;

    private static ArticleRepository articleRepository;

    public static ArticleRepository getInstance(){
        if (articleRepository == null) {
            articleRepository = new ArticleRepository();
        }
        return articleRepository;
    }

    private ArticleRepository() {
        jedis = new Jedis();
    }

    public Long save(Article article) {
        Long id = jedis.incr("next_article_id");
        String key = "article:" + id;

        HashMap<String, String> values = new HashMap<String, String>();
        values.put("body", article.getBody());
        values.put("tags", article.getTags());

        jedis.hmset(key, values);

        // add article id to articles
        jedis.rpush("articles", id + "");
        return id;
    }

    public List<Article> findAll() {
        List<Article> articles = new LinkedList<Article>();
        List<String> ids = jedis.lrange("articles", 0, -1);
        for (String id : ids) {

            String key = "article:" + id;
            Map<String, String> values = jedis.hgetAll(key);
            String body = values.get("body");
            String tags = values.get("tags");

            Article article = new Article(Long.parseLong(id), body, tags);
            articles.add(article);
        }
        return articles;
    }
}
