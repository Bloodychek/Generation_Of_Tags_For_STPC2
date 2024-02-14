package com.example.Generation_Of_Tags_For_STPC2.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name = "STATUS_MACHINE_GSV", schema = "mk_plus")
public class StatusMachine {
    @EmbeddedId
    private StatusMachineGSVPK statusMachineGSVPK;

    @Column(name = "TABNOM_IN")
    private Long TabNum;

    @Embeddable
    @NoArgsConstructor
    @Data
    public static class StatusMachineGSVPK implements Serializable {
        @Column(name="ID_MACHINE")
        private Long IdMachine;

        @Column(name = "UCHACTOK")
        private String area;
    }

    @Override
    public String toString() {
        return "StatusMachine{" +
                statusMachineGSVPK +
                ", TabNum=" + TabNum +
                '}';
    }
}

