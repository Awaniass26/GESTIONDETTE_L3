package com.ism.repository.list;

import java.util.List;
import java.util.ArrayList;
import com.ism.entity.Article;
import com.ism.repository.ArticleRepository;

public class ArticleRepositoryList extends RepositoryListImpl<Article> implements ArticleRepository {

    private List<Article> articles = new ArrayList<>();

    @Override
    public List<Article> findAll() {
        return articles;
    }

    @Override
    public Article findById(int id) {
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }

   
    public void removeArticle(Article article) {
        articles.remove(article);
    }
}
