package com.ism.repository;

import java.util.List;

import com.ism.core.repository.Repository;
import com.ism.entity.Paiement;

public interface PaiementRepository extends Repository<Paiement>{

    List<Paiement> listerPaiementsParDette(int detteId) ;

}
