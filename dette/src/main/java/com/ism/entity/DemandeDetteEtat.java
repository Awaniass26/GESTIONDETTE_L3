package com.ism.entity;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString()

public enum DemandeDetteEtat {
    EN_ATTENTE,
    VALIDEE,
    REFUSEE,
    ANNULEE,
    RELANCEE
}

