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
@Table(name = "asignaturas")
public class Asignatura implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name = "nombre", nullable = false, length = 80)
    @NotEmpty
	private String nombre;

    @Column(name = "numero_creditos", nullable = false)
    @NotNull
	private Integer numero_creditos;

    @Column(name = "departamento", nullable = false, length = 50)
    @NotEmpty
	private String departamento;

    @OneToMany(mappedBy = "asignatura", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Asignatura_Planes> asignatura_Planes;

    public Asignatura() {

    }

    public Asignatura(Long id, String nombre, Integer numero_creditos, String departamento, 
            Asignatura_Planes asignatura_Planes){
        this.id = id;
        this.nombre = nombre;
        this.numero_creditos = numero_creditos;
        this.departamento = departamento;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumero_creditos() {
        return numero_creditos;
    }

    public void setNumero_creditos(Integer numero_creditos) {
        this.numero_creditos = numero_creditos;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public List<Asignatura_Planes> getAsignatura_Planes() {
        return asignatura_Planes;
    }

    public void setAsignatura_Planes(List<Asignatura_Planes> asignatura_Planes) {
        this.asignatura_Planes = asignatura_Planes;
    }

    
}
