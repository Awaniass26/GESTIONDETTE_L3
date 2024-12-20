package com.ism.repository;

import java.util.List;

import com.ism.core.repository.Repository;
import com.ism.entity.Article;

public interface ArticleRepository extends Repository<Article>{
     List<Article> findAll();  
    Article findById(int id);
    
}
