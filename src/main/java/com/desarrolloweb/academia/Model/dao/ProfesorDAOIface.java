package com.desarrolloweb.academia.Model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desarrolloweb.academia.Model.entity.Profesor;

@Repository
public interface ProfesorDAOIface extends JpaRepository<Profesor, Long>{
    
}
