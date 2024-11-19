package com.desarrolloweb.academia.Model.service;

import java.util.List;

import com.desarrolloweb.academia.Model.entity.Asignatura;
import com.desarrolloweb.academia.Model.entity.Curso;
import com.desarrolloweb.academia.Model.entity.Curso_Matriculado;
import com.desarrolloweb.academia.Model.entity.Estudiante;
import com.desarrolloweb.academia.Model.entity.Profesor;

public interface AcademiaServiceIface {

    // servicios para Estudiante
	public List<Estudiante> buscarEstudiantesTodos();
	public void guardarEstudiante(Estudiante estudiante);
	public Estudiante buscarEstudiantePorId(Long id);
	public void eliminarEstudiantePorId(Long id);
	//public Estudiante buscarEstudianteConDetalles(Long id);

	// servicios para curso Matriculado
	public void guardarCusuoMatriculado(Curso_Matriculado cursos_matriculados);

	//servicio asignatura
	public List<Asignatura>listarAsignaturas();
	public Asignatura buscarAsignaturaPorId(Long id);

	//servicio curso
	public List<Curso>listarCursos();
	//public List<Curso>buscarCursosTodos();
	public Curso buscarCursoPorId(Long id);
	public void guardarCurso(Curso curso);
	public List<Curso>listarCursosPorAsignaturaId(Long asignaturaId);

	//servicio profesor
	public List<Profesor> buscarProfesoresTodos();
}
