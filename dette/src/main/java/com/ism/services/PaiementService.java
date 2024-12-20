package com.ism.services;

import java.util.List;

import com.ism.entity.Paiement;

public interface PaiementService extends Service<Paiement>{

    List<Paiement> getPaiementsParDette(int detteId);

}
