package com.desarrolloweb.academia.Model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desarrolloweb.academia.Model.entity.Estudiante;

@Repository
public interface EstudianteDAOIface extends JpaRepository<Estudiante, Long>{

    
}
