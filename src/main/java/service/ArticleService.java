package service;

import model.Article;
import publisher.ArticlePublisher;
import publisher.ArticlePublisherInterface;
import redis.clients.jedis.Jedis;
import repository.ArticleRepository;
import repository.ArticleRepositoryInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ArticleService {
    private ArticleRepositoryInterface articleRepository;
    private ArticlePublisherInterface articlePublisher;

    public ArticleService() {
        articleRepository = new ArticleRepository();
        articlePublisher = new ArticlePublisher();
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
                articlePublisher.publishArticle(tag, article);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
