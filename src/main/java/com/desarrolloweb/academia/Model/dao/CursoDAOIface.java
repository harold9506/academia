package com.desarrolloweb.academia.Model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desarrolloweb.academia.Model.entity.Curso;

@Repository
public interface CursoDAOIface extends JpaRepository<Curso, Long> {

    List<Curso> findByAsignaturaId(Long asignaturaId);
    
}
