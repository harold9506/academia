package com.desarrolloweb.academia.Model.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

@Entity
@Table(name = "asignaturas_cursadas")
public class Asignatura_Cursada implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    private Asignatura asignatura;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    @Column(nullable = false)
    private double nota_final;
    
    public Asignatura_Cursada() {

    }

    public Asignatura_Cursada (Long id, Estudiante estudiante, Asignatura asignatura, 
            double nota_final){
        this.id = id;
        this.estudiante = estudiante;
        this.asignatura = asignatura;
        this.nota_final = nota_final;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public double getNota_final() {
        return nota_final;
    }

    public void setNota_final(double nota_final) {
        this.nota_final = nota_final;
    }

    
}
