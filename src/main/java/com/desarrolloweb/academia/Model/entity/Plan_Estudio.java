package com.desarrolloweb.academia.Model.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "plan_estudio")
public class Plan_Estudio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Long id;

    @NotEmpty
	@Column(name = "descripcion", length = 50, nullable = false)
	private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    private Programa_Academico programa_academico;

    @NotNull
	private Integer anio_vigencia;

    @OneToMany(mappedBy = "plan_estudio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Asignatura_Planes> asignatura_planes;
    
    public Plan_Estudio() {

    }

    public Plan_Estudio(Long id, String descripcion, Programa_Academico programa_academico, Integer anio_vigencia, 
            Asignatura_Planes asignatura_planes){
        this.id = id;
        this.descripcion = descripcion;
        this. programa_academico = programa_academico;
        this. anio_vigencia = anio_vigencia;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Programa_Academico getPrograma_Academico() {
        return programa_academico;
    }

    public void setPrograma_Academico(Programa_Academico programa_academico) {
        this.programa_academico = programa_academico;
    }

    public Integer getAnio_vigencia() {
        return anio_vigencia;
    }

    public void setAnio_vigencia(Integer anio_vigencia) {
        this.anio_vigencia = anio_vigencia;
    }

    public List<Asignatura_Planes> getAsignatura_Planes() {
        return asignatura_planes;
    }

    public void setAsignatura_Planes(List<Asignatura_Planes> asignatura_planes) {
        this.asignatura_planes = asignatura_planes;
    }

    
}
