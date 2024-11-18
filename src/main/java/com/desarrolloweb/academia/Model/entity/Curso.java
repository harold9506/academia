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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "cursos")
public class Curso implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asignatura_id")
    private Asignatura asignatura;

    @ManyToOne(fetch = FetchType.LAZY)
    private Profesor profesor;

    @NotEmpty
	@Column(name = "periodo", length = 10, nullable = false)
	private String periodo;

    @NotEmpty
	@Column(name = "horario", length = 20, nullable = false)
	private String horario;

    @NotNull
	private Integer cupo_maximo;

    @NotEmpty
	@Column(name = "aula", length = 30, nullable = false)
	private String aula;

    @OneToMany(mappedBy = "curso", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Curso_Matriculado> curso_Matriculado;

    public Curso() {

    }

    public Curso (Long id, Asignatura asignatura, Profesor profesor, String periodo, String horario, 
            Integer cupo_maximo, String aula){
        this.id = id;
        this.asignatura = asignatura;
        this.profesor = profesor;
        this.horario = horario;
        this.cupo_maximo = cupo_maximo;
        this.aula = aula;
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

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Integer getCupo_maximo() {
        return cupo_maximo;
    }

    public void setCupo_maximo(Integer cupo_maximo) {
        this.cupo_maximo = cupo_maximo;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public List<Curso_Matriculado> getCurso_Matriculado() {
        return curso_Matriculado;
    }

    public void setCurso_Matriculado(List<Curso_Matriculado> curso_Matriculado) {
        this.curso_Matriculado = curso_Matriculado;
    }

    public Curso orElse(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'orElse'");
    }

    
}
