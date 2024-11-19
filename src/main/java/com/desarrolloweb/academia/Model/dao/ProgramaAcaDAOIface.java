package com.desarrolloweb.academia.Model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desarrolloweb.academia.Model.entity.Programa_Academico;

@Repository
public interface ProgramaAcaDAOIface extends JpaRepository<Programa_Academico, Long>{
    
}
