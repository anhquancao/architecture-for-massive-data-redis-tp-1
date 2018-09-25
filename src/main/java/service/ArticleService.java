package service;

import model.Article;
import redis.clients.jedis.Jedis;
import repository.ArticleRepository;
import repository.ArticleRepositoryInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ArticleService {
    private ArticleRepositoryInterface articleRepository;
    private Jedis jedis;

    public ArticleService() {
        articleRepository = new ArticleRepository();
        jedis = new Jedis();
    }

    public void showAllArticle() {
        List<Article> articles = articleRepository.findAll();
        for (Article article : articles) {
            System.out.println(article);
        }
    }

    public void createArticle() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Please input article body");
            String body = in.readLine();
            System.out.println("Please input article keywords separated by comma:");
            String tags = in.readLine();

            // Save article into database
            Article article = new Article(body, tags);
            articleRepository.save(article);
            String[] tagList = tags.split(",");

            // publish the body of article to all keyword channel
            for (String rawTag : tagList) {
                String tag = rawTag.trim();
                String channel = "channel:" + tag;
                jedis.publish(channel, article.getBody());
                System.out.println("publish to " + channel);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
