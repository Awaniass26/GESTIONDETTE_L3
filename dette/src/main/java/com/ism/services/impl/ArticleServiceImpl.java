package com.ism.services.impl;

import java.util.List;

import com.ism.entity.Article;
import com.ism.repository.bd.ArticleRepositoryBd;
import com.ism.services.ArticleService;

public class ArticleServiceImpl extends ServiceImpl<Article> implements ArticleService{
    
    public ArticleServiceImpl(ArticleRepositoryBd repository) {
        super(repository);
    }

    @Override
    public List<Article> get(Boolean hasAccount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }
}
