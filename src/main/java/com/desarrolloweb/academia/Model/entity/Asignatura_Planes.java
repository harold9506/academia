package com.desarrolloweb.academia.Model.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "asignatura_planes")
public class Asignatura_Planes implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Plan_Estudio plan_estudio;

    @ManyToOne(fetch = FetchType.LAZY)
    private Asignatura asignatura;

    @NotNull
	private Integer semestre_nivel;

	private Long prerrequisito;
    
    public Asignatura_Planes() {

    }

    public Asignatura_Planes (Long id, Plan_Estudio plan_estudio, Asignatura asignatura, 
            Long prerrequisito){
        this.id = id;
        this.plan_estudio = plan_estudio;
        this.asignatura = asignatura;
        this.prerrequisito = prerrequisito;

        
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

    public Plan_Estudio getPlan_Estudio() {
        return plan_estudio;
    }

    public void setPlan_Estudio(Plan_Estudio plan_estudio) {
        this.plan_estudio = plan_estudio;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public Integer getSemestre_nivel() {
        return semestre_nivel;
    }

    public void setSemestre_nivel(Integer semestre_nivel) {
        this.semestre_nivel = semestre_nivel;
    }

    public Long getPrerrequisito() {
        return prerrequisito;
    }

    public void setPrerrequisito(Long prerrequisito) {
        this.prerrequisito = prerrequisito;
    }
}
