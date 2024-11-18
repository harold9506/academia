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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "programa_academico")
public class Programa_Academico implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Long id;

    @NotEmpty
	@Column(name = "nombre_programa", length = 80, nullable = false)
	private String nombre_programa;

    @NotEmpty
	@Column(name = "facultad", length = 50, nullable = false)
	private String facultad;

    @NotEmpty
	@Column(name = "titulo_ortorgado", length = 70, nullable = false)
	private String titulo_ortorgado;

    @NotNull
	private Integer duracion;

    @OneToMany(mappedBy = "programa_academico", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Estudiante> estudiante;

    @OneToMany(mappedBy = "programa_academico", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Plan_Estudio> plan_estudio;
    
    public Programa_Academico() {

    }

    public Programa_Academico (Long id, String nombre_programa, String facultad, String titulo_ortorgado,
            Integer duracion){
        this.id = id;
        this.nombre_programa = nombre_programa;
        this.facultad = facultad;
        this.titulo_ortorgado = titulo_ortorgado;
        this.duracion = duracion;
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

    public String getNombre_programa() {
        return nombre_programa;
    }

    public void setNombre_programa(String nombre_programa) {
        this.nombre_programa = nombre_programa;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getTitulo_ortorgado() {
        return titulo_ortorgado;
    }

    public void setTitulo_ortorgado(String titulo_ortorgado) {
        this.titulo_ortorgado = titulo_ortorgado;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public List<Estudiante> getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(List<Estudiante> estudiante) {
        this.estudiante = estudiante;
    }

    public List<Plan_Estudio> getPlan_estudio() {
        return plan_estudio;
    }

    public void setPlan_estudio(List<Plan_Estudio> plan_estudio) {
        this.plan_estudio = plan_estudio;
    }

    
}
