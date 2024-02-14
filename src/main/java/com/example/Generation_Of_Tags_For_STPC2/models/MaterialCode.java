package com.example.Generation_Of_Tags_For_STPC2.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "V_MATERIAL_CODE", schema = "PRB")
public class MaterialCode {
    @Id
    @Column(name = "ID_CODE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "DIAMETER")
    private String diameter;

    @Column(name="STEEL_GRADE")
    private String steelGrade;

    @Column(name="CALCGROUP")
    private String calcGroup;

    @Column(name="ID_STAGE")
    private Long idStage;
}