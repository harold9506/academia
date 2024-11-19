package com.desarrolloweb.academia.Model.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "cursos_matriculados")
public class Curso_Matriculado implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @NotEmpty
	@Column(name = "periodo", length = 50, nullable = false)
	private String periodo;

    @NotEmpty(message = "El estado del curso no puede estar vacío")
	@Column(name = "estado_curso", length = 20, nullable = false)
	private String estado_curso;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    @Column(nullable = false)
    private Double nota_final;

    // Constructor y Getters/Setters
    public Curso_Matriculado() {
    }

    public Curso_Matriculado(Long id, Estudiante estudiante, Curso curso, String periodo, String estado_curso, double nota_final) {
        this.id = id;
        this.estudiante = estudiante;
        this.curso = curso;
        this.periodo = periodo;
        this.estado_curso = estado_curso;
        this.nota_final = nota_final;
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

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getEstado_curso() {
        return estado_curso;
    }

    public void setEstado_curso(String estado_curso) {
        this.estado_curso = estado_curso;
    }

    public Double getNota_final() {
        return nota_final;
    }

    public void setNota_final(double nota_final) {
        this.nota_final = nota_final;
    }

    
}
