package com.ism.repository.bd;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ism.core.bd.DatabaseImpl;
import com.ism.entity.Article;
import com.ism.repository.ArticleRepository;


public class ArticleRepositoryBd extends RepositoryBdImpl<Article> implements ArticleRepository {
    
     public ArticleRepositoryBd() {
        super(Article.class);
    }


    public List<Article> getArticlesForDemande(int demandeId) {
    String sql = "SELECT a.id, a.nom, a.prix, dda.quantite_demandee " +
                 "FROM article a " +
                 "INNER JOIN \"demandeDetteArticle\" dda ON a.id = dda.article_id " +
                 "WHERE dda.demande_dette_id = ?";
    List<Article> articles = new ArrayList<>();

    try {
        initPreparedStatement(sql);
        statement.setInt(1, demandeId);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Article article = new Article();
            article.setId(rs.getInt("id"));
            article.setNom(rs.getString("nom"));
            article.setPrix(rs.getDouble("prix"));
            article.setQuantiteDemandee(rs.getInt("quantite_demandee"));
            article.setQuantiteStock(rs.getInt("quantite_stock"));
            articles.add(article);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        CloseConnection();
    }

    return articles;
}


public void updateQuantiteStock(int articleId, int nouvelleQuantite) {
    String sql = "UPDATE article SET quantitestock = ? WHERE id = ?";
    DatabaseImpl database = new DatabaseImpl();
    database.OpenConnection();
    try {
        database.initPreparedStatement(sql);
        database.statement.setInt(1, nouvelleQuantite);
        database.statement.setInt(2, articleId);
        int rows = database.statement.executeUpdate();

        if (rows > 0) {
            System.out.println("Quantité en stock mise à jour avec succès !");
        } else {
            System.out.println("Article introuvable ou mise à jour échouée.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        database.CloseConnection();
    }
}



public List<Article> listerArticlesParDette(int detteId) {
    String sql = "SELECT * FROM article WHERE id IN (SELECT article_id FROM \"detteArticle\" WHERE dette_id = ?)";
    List<Article> articles = new ArrayList<>();
    DatabaseImpl database = new DatabaseImpl();
    database.OpenConnection();

    try {
        database.initPreparedStatement(sql);
        database.statement.setInt(1, detteId);
        ResultSet rs = database.statement.executeQuery();
        while (rs.next()) {
            Article article = new Article();
            article.setId(rs.getInt("id"));
            article.setNom(rs.getString("nom"));
            article.setPrix(rs.getDouble("prix"));
            article.setQuantiteStock(rs.getInt("quantitestock"));
            articles.add(article);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        database.CloseConnection();
    }

    return articles;
}


public List<Article> findByDemandeId(int demandeId) {
    List<Article> articles = new ArrayList<>();
    String query = "SELECT a.id, a.nom, a.prix, a.quantitestock, dda.quantite_demandee " +
               "FROM article a " +
               "JOIN \"demandeDetteArticle\" dda ON dda.article_id = a.id " +
               "WHERE dda.demande_dette_id = ?";


    DatabaseImpl database = new DatabaseImpl();
    database.OpenConnection();

    try {
        database.initPreparedStatement(query); 
        database.statement.setInt(1, demandeId); 

        ResultSet resultSet = database.statement.executeQuery(); 
        while (resultSet.next()) {
            Article article = new Article();
            article.setId(resultSet.getInt("id"));
            article.setNom(resultSet.getString("nom"));
            article.setQuantiteDemandee(resultSet.getInt("quantite_demandee"));
            article.setQuantiteStock(resultSet.getInt("quantitestock"));
            articles.add(article);
        }
    } catch (SQLException e) {
        e.printStackTrace(); 
    } finally {
        database.CloseConnection();
    }

    return articles; 
}



@Override
public List<Article> findAll() {
    String sql = "SELECT * FROM article";
    List<Article> articles = new ArrayList<>();

    try {
        initPreparedStatement(sql);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Article article = new Article();
            article.setId(rs.getInt("id"));
            article.setNom(rs.getString("nom"));
            article.setPrix(rs.getDouble("prix"));
            article.setQuantiteStock(rs.getInt("quantitestock"));
            articles.add(article);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        CloseConnection();
    }

    return articles;
}


@Override
public Article findById(int id) {
    String sql = "SELECT * FROM article WHERE id = ?";
    Article article = null;

    try {
        initPreparedStatement(sql);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            article = new Article();
            article.setId(rs.getInt("id"));
            article.setNom(rs.getString("nom"));
            article.setPrix(rs.getDouble("prix"));
            article.setQuantiteStock(rs.getInt("quantitestock"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        CloseConnection();
    }

    return article;
}



// public void save(Article article) {
//     String sql = "INSERT INTO \"article\"(nom, prix, quantitestock) VALUES (?, ?, ?)";
//     int generatedId = -1;
    
//     try {
//         initPreparedStatement(sql);
//         statement.setString(1, article.getNom());
//         statement.setDouble(2, article.getPrix());
//         statement.setInt(3, article.getQuantiteStock());
//         statement.executeUpdate();
        
//         ResultSet rs = statement.getGeneratedKeys();
//         if (rs.next()) {
//             generatedId = rs.getInt(1);
//             article.setId(generatedId);
//         }
//     } catch (SQLException e) {
//         e.printStackTrace();
//     } finally {
//         CloseConnection();
//     }
// }




}
