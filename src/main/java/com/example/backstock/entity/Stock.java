package com.example.backstock.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Stock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long IdStock;
    private float QuantiteStock;
    private String ArticleDispo;
    private Date DateDebut;
    private Date DateFin;
    private String Emplacement;
    private String HistoriqueStock;
    private float QuantiteMinStock;
    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private UserEntity responsable;
}
