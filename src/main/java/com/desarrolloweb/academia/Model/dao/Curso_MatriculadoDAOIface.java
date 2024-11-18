package com.desarrolloweb.academia.Model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desarrolloweb.academia.Model.entity.Curso_Matriculado;

@Repository
public interface Curso_MatriculadoDAOIface extends JpaRepository<Curso_Matriculado, Long>{
    
}
