package service;

import context.AppContext;
import model.Article;
import publisher.ArticlePublisher;
import publisher.ArticlePublisherInterface;
import repository.ArticleRepository;
import repository.ArticleRepositoryInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class ArticleService {
    private ArticleRepositoryInterface articleRepository;
    private ArticlePublisherInterface articlePublisher;

    private static ArticleService articleService;

    public static ArticleService getInstance() {
        if (articleService == null) {
            articleService = new ArticleService();
        }
        return articleService;
    }

    private ArticleService() {
        articleRepository = ArticleRepository.getInstance();
        articlePublisher = new ArticlePublisher();
    }

    public void showAllArticle() {
        List<Article> articles = articleRepository.findAll();
        for (Article article : articles) {
            System.out.println(article);
        }
    }

    public void createArticle() {
        BufferedReader in = AppContext.getAppContext().getReaderInstance();
        try {
            System.out.println("Please input article body");
            String body = in.readLine();
            System.out.println("Please input article keywords separated by comma:");
            String tags = in.readLine();

            // Save article into database
            Article article = new Article(body, tags);
            Long id = articleRepository.save(article);
            article.setId(id);

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
