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
@Table(name = "estudiantes")
public class Estudiante implements Serializable{

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name = "identificacion", nullable = false, length = 10)
	@NotEmpty
	private String identificacion;

    @Column(name = "apellidos", nullable = false, length = 50)
	@NotEmpty
	private String apellidos;

    @Column(name = "nombres", nullable = false, length = 50)
	@NotEmpty
	private String nombres;

    @NotNull
	private Integer semestre_actual;

    @ManyToOne(fetch = FetchType.LAZY)
    private Programa_Academico programa_academico;

    @OneToMany(mappedBy = "estudiante", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Curso_Matriculado> curso_Matriculado;

    public Estudiante() {

    }

    public Estudiante (Long id, String identificacion, String apellidos, String nombres, 
            Integer semestre_acutual, Programa_Academico programa_academico){
        this.id = id;
        this.identificacion = identificacion;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.semestre_actual = semestre_acutual;
        this.programa_academico = programa_academico;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Integer getSemestre_actual() {
        return semestre_actual;
    }

    public void setSemestre_actual(Integer semestre_actual) {
        this.semestre_actual = semestre_actual;
    }

    public Programa_Academico getPrograma_Academico() {
        return programa_academico;
    }

    public void setPrograma_Academico(Programa_Academico programa_academico) {
        this.programa_academico = programa_academico;
    }

    public List<Curso_Matriculado> getCurso_Matriculado() {
        return curso_Matriculado;
    }

    public void setCurso_Matriculado(List<Curso_Matriculado> curso_Matriculado) {
        this.curso_Matriculado = curso_Matriculado;
    }

    
    
}
