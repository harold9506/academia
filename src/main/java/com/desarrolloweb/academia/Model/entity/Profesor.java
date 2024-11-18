package com.desarrolloweb.academia.Model.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "profesores")
public class Profesor implements Serializable{
    
    @Id
	private Long id;

    @Column(name = "apellidos", nullable = false, length = 50)
	@NotEmpty
	private String apellidos;

    @Column(name = "nombres", nullable = false, length = 50)
	@NotEmpty
	private String nombres;

    @Column(name = "especializacion", nullable = false, length = 80)
	@NotEmpty
	private String especializacion;

    @Column(name = "departamento", nullable = false, length = 20)
	@NotEmpty
	private String departamento;

    @OneToMany(mappedBy = "profesor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Curso> curso;

    public Profesor() {

    }

    public Profesor (Long id, String apellidos, String nombres, String especializacion, String departamento){
        this.id = id;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.especializacion = especializacion;
        this.departamento = departamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEspecializacion() {
        return especializacion;
    }

    public void setEspecializacion(String especializacion) {
        this.especializacion = especializacion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public List<Curso> getCurso() {
        return curso;
    }

    public void setCurso(List<Curso> curso) {
        this.curso = curso;
    }

    
}
