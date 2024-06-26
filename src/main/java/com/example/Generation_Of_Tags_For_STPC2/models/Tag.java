package com.example.Generation_Of_Tags_For_STPC2.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "GSV_BIRKA_PRN", schema = "mk_plus")
public class Tag {
    @Id
    @Column(name = "ID_LOG")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "N_STAN")
    private int millNumber;

    @Column(name = "CHANGE_OUT")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTime;

    @Column(name = "N_SMEN")
    private int shift;

    @Column(name = "N_BRIG")
    private int brigade;

    @Column(name = "KOD")
    private int materialCode;

    @Column(name = "WES")
    @Nullable
    private Integer weight;

    @Column(name = "PLAVKA")
    @Nullable
    private Integer melt;

    @Column(name = "LENGTH_B")
    @Nullable
    private Integer length;

    @Column(name = "ZONA")
    private String area;

    @Column(name = "JOB_DIE")
    @Nullable
    private Integer jobDie;

    @Column(name = "PRINT_BIRKA")
    @Nullable
    private Integer printBirka;
}