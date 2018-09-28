package repository;

import model.Article;

import java.util.List;

public interface ArticleRepositoryInterface {
    Long save(Article article);
    List<Article> findAll();
    Article findArticleById(Long id);
}
