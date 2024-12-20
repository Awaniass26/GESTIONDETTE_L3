package com.ism.views;

import com.ism.entity.Paiement;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class PaiementView {
    private Scanner scanner;

    public PaiementView(Scanner scanner) {
        this.scanner = scanner;
    }

    public Paiement saisiePaiement(double montantRestant) {
        System.out.println("Créer un paiement :");

        System.out.print("Entrez la date du paiement (YYYY-MM-DD) : ");
        String paiementDate = scanner.nextLine();
        Date datePaiement = Date.valueOf(paiementDate);

        System.out.print("Entrez le montant du paiement (maximum : " + montantRestant + ") : ");
        double paiementMontant = scanner.nextDouble();
        scanner.nextLine();

        if (paiementMontant > montantRestant) {
            System.out.println("Erreur : Le montant du paiement dépasse le montant restant.");
            return null;
        }else if(paiementMontant == montantRestant){
            System.out.println("Dette soldee");
        }

        return new Paiement(datePaiement, paiementMontant);
    }

    public void afficherPaiements(List<Paiement> paiements) {
        for (Paiement paiement : paiements) {
            System.out.println(paiement);
        }
    }

  

}
