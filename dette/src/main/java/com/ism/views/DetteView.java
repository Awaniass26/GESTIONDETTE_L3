package com.ism.views;

import com.ism.entity.Dette;
import com.ism.entity.Article;
import com.ism.entity.Client;
import com.ism.entity.Paiement;
import com.ism.repository.ArticleRepository;
import com.ism.repository.DetteRepository;
import com.ism.repository.PaiementRepository;
import com.ism.repository.bd.ClientRepositoryBd;


import java.sql.Date;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class DetteView {
    private Scanner scanner;
    private DetteRepository detteRepository;
    private PaiementRepository paiementRepository;
    private ArticleRepository articleRepository;
    private ClientRepositoryBd clientRepository; 


    public DetteView(Scanner scanner, DetteRepository detteRepository, PaiementRepository paiementRepository, ArticleRepository articleRepository, ClientRepositoryBd clientRepository) {
        this.scanner = scanner;
        this.detteRepository = detteRepository;
        this.paiementRepository = paiementRepository;
        this.articleRepository = articleRepository;
        this.clientRepository = clientRepository;  
    }

    public Dette saisieDette() {
        Scanner scanner = new Scanner(System.in);
        Dette dette = new Dette();
    
        // Saisie des informations de la dette
        System.out.print("Entrez la date de la dette (YYYY-MM-DD) : ");
        String dateString = scanner.nextLine();
        dette.setDate(Date.valueOf(dateString));
    
        System.out.print("Entrez le montant total : ");
        double montant = scanner.nextDouble();
        scanner.nextLine();
    
        System.out.print("Entrez le montant versé initial : ");
        double montantVerse = scanner.nextDouble();
        scanner.nextLine();
    
        dette.setMontant(montant);
        dette.setMontantVerse(montantVerse);
        dette.setMontantRestant(montant - montantVerse);
    
        System.out.println("Montant restant : " + dette.getMontantRestant());
    
        // Liste des articles disponibles
        System.out.println("Liste des articles disponibles :");
        List<Article> articlesDisponibles = articleRepository.findAll();
        for (Article article : articlesDisponibles) {
            System.out.println("ID: " + article.getId() + " - Nom: " + article.getNom());
        }
    
        // Ajout obligatoire d'articles
        System.out.print("Entrez le nombre d'articles à ajouter : ");
        int nombreArticles = scanner.nextInt();
        scanner.nextLine();
        ArrayList<Article> articles = new ArrayList<>();
    
        for (int i = 0; i < nombreArticles; i++) {
            System.out.print("Entrez l'ID de l'article " + (i + 1) + " à ajouter : ");
            int articleId = scanner.nextInt();
            scanner.nextLine();
    
            // Trouver l'article par son ID
            Article article = articleRepository.findById(articleId);
            if (article != null) {
                articles.add(article);
            } else {
                System.out.println("Article avec l'ID " + articleId + " non trouvé.");
            }
        }
        dette.setArticles(articles);
    
        // // Saisie des paiements associés
        // System.out.print("Souhaitez-vous ajouter des paiements pour cette dette ? (O/N) : ");
        // char choixPaiement = scanner.next().charAt(0);
        // scanner.nextLine();
    
        // if (Character.toUpperCase(choixPaiement) == 'O') {
        //     ArrayList<Paiement> paiements = new ArrayList<>();
        //     double montantRestant = dette.getMontantRestant();
    
        //     while (montantRestant > 0) {
        //         System.out.print("Entrez le montant du paiement : ");
        //         double montantPaiement = scanner.nextDouble();
        //         scanner.nextLine();
    
        //         if (montantPaiement > montantRestant) {
        //             System.out.println("Le montant du paiement dépasse le montant restant (" + montantRestant + "). Réessayez.");
        //             continue;
        //         }
    
        //         Paiement paiement = new Paiement();
        //         paiement.setMontant(montantPaiement);
        //         paiement.setDette(dette);
        //         paiements.add(paiement);
    
        //         montantRestant -= montantPaiement;
        //         dette.setMontantRestant(montantRestant);
    
        //         System.out.println("Montant restant après ce paiement : " + montantRestant);
    
        //         if (montantRestant > 0) {
        //             System.out.print("Voulez-vous ajouter un autre paiement ? (O/N) : ");
        //             char autrePaiement = scanner.next().charAt(0);
        //             scanner.nextLine();
        //             if (Character.toUpperCase(autrePaiement) != 'O') {
        //                 break;
        //             }
        //         }
        //     }
        //     dette.setPaiements(paiements);
        // }
    
        return dette;
    }
    
    
    // Méthode pour obtenir un client par ID
    private Client getClientById(int clientId) {
        Client client = clientRepository.findById(clientId);
        if (client == null) {
            System.out.println("Client non trouvé avec l'ID : " + clientId);
        }
        return client;
    }

    public void afficherDettesNonSoldees(List<Dette> dettes) {

        
        if (dettes.isEmpty()) {
            System.out.println("Aucune dette non soldée trouvée.");
            return;
        }

        System.out.println("Liste des dettes non soldées :");
        for (Dette dette : dettes) {
            System.out.println("ID : " + dette.getId());
            System.out.println("Date : " + dette.getDate());
            System.out.println("Montant : " + dette.getMontant());
            System.out.println("Montant versé : " + dette.getMontantVerse());
            System.out.println("Montant restant : " + dette.getMontantRestant());
            System.out.println("-----------------------------------");
        }

        System.out.print("Voulez-vous voir les détails d'une dette spécifique ? (O/N) : ");
        char choix = scanner.next().charAt(0);
        scanner.nextLine();

        if (Character.toUpperCase(choix) == 'O') {
            System.out.print("Entrez l'ID de la dette : ");
            int detteId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Voir les (A)rticles ou les (P)aiements ? : ");
            char option = scanner.next().charAt(0);
            scanner.nextLine();

            if (Character.toUpperCase(option) == 'A') {
                List<Article> articles = detteRepository.findArticlesByDetteId(detteId);
                afficherArticles(articles);
            } else if (Character.toUpperCase(option) == 'P') {
                List<Paiement> paiements = detteRepository.findPaiementsByDetteId(detteId);
                afficherPaiements(paiements);
            } else {
                System.out.println("Option invalide.");
            }
        }
    }

    private void afficherArticles(List<Article> articles) {
        if (articles.isEmpty()) {
            System.out.println("Aucun article trouvé pour cette dette.");
            return;
        }

        System.out.println("Articles liés à la dette :");
        for (Article article : articles) {
            System.out.println("ID : " + article.getId());
            System.out.println("Nom : " + article.getNom());
        }
    }

    private void afficherPaiements(List<Paiement> paiements) {
        if (paiements.isEmpty()) {
            System.out.println("Aucun paiement trouvé pour cette dette.");
            return;
        }

        System.out.println("Paiements liés à la dette :");
        for (Paiement paiement : paiements) {
            System.out.println("ID : " + paiement.getId());
            System.out.println("Montant : " + paiement.getMontant());
        }
    }

    public void afficherDettes(List<Dette> dettes) {
        for (Dette dette : dettes) {
            System.out.println(dette);
        }
    }

}
