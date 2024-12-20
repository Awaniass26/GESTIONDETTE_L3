package com.ism.services.impl;

import com.ism.entity.Dette;
import com.ism.entity.Paiement;
import com.ism.repository.DetteRepository;
import com.ism.repository.bd.DetteRepositoryBd;
import com.ism.services.DetteService;

public class DetteServiceImpl extends ServiceImpl<Dette> implements DetteService {

    private final DetteRepository detteRepository;

    public DetteServiceImpl(DetteRepositoryBd repository) {
        super(repository);
        this.detteRepository = repository;
    }

    @Override
    public Dette findById(int id) {
        return detteRepository.findById(id);
    }

    @Override
    public void updateCumulMontantDus(Dette selectedDette) {
        if (selectedDette == null) {
            throw new IllegalArgumentException("La dette sélectionnée ne peut pas être nulle.");
        }

        double totalPaiements = selectedDette.getPaiements().stream()
                .mapToDouble(Paiement::getMontant)
                .sum();

        double montantRestant = selectedDette.getMontant() - totalPaiements;

        selectedDette.setMontantRestant(montantRestant);

        detteRepository.update(selectedDette);

        System.out.println("Cumul des montants dus mis à jour. Montant restant : " + montantRestant);
    }

}
