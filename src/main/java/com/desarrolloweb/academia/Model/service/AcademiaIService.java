package com.desarrolloweb.academia.Model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desarrolloweb.academia.Model.dao.AsignaturaDAOIface;
import com.desarrolloweb.academia.Model.dao.CursoDAOIface;
import com.desarrolloweb.academia.Model.dao.Curso_MatriculadoDAOIface;
import com.desarrolloweb.academia.Model.dao.EstudianteDAOIface;
import com.desarrolloweb.academia.Model.dao.ProfesorDAOIface;
import com.desarrolloweb.academia.Model.entity.Asignatura;
import com.desarrolloweb.academia.Model.entity.Curso;
import com.desarrolloweb.academia.Model.entity.Curso_Matriculado;
import com.desarrolloweb.academia.Model.entity.Estudiante;
import com.desarrolloweb.academia.Model.entity.Profesor;

@Service
public class AcademiaIService implements AcademiaServiceIface{

    private final EstudianteDAOIface estudianteDAO;
	private final Curso_MatriculadoDAOIface cursomatriculadoDAO;
	private final AsignaturaDAOIface asignaturaDAO;
	private final CursoDAOIface cursoDAO;
	private final ProfesorDAOIface profesorDAO;

    public AcademiaIService(EstudianteDAOIface estudianteDAO, Curso_MatriculadoDAOIface cursomatriculadoDAO, AsignaturaDAOIface asignaturaDAO,
		CursoDAOIface cursoDAO, ProfesorDAOIface profesorDAO ) {
        this.estudianteDAO = estudianteDAO;
		this.cursomatriculadoDAO = cursomatriculadoDAO;
		this.asignaturaDAO = asignaturaDAO;
		this.cursoDAO = cursoDAO;
		this.profesorDAO = profesorDAO;
    }

    // servicios para Estudiante
    @Override
	@Transactional(readOnly = true)
	public List<Estudiante> buscarEstudiantesTodos() {
		return estudianteDAO.findAll();
	}

	@Override
	@Transactional
	public void guardarEstudiante(Estudiante estudiante) {
		estudianteDAO.save(estudiante);
	}

	@Override
	@Transactional(readOnly = true)
	public Estudiante buscarEstudiantePorId(Long id) {
		return estudianteDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void eliminarEstudiantePorId(Long id) {
		estudianteDAO.deleteById(id);
	}	

	// servicios para Curso Matriculado
	@Override
	@Transactional
	public void guardarCusuoMatriculado(Curso_Matriculado cursos_matriculados) {
		cursomatriculadoDAO.save(cursos_matriculados);

	}


	//servicio asignatura
	@Override
	@Transactional(readOnly = true)
	public List<Asignatura>listarAsignaturas(){
		return asignaturaDAO.findAll();
	}


	@Override
	@Transactional(readOnly = true)
	public Asignatura buscarAsignaturaPorId(Long id) {
		return asignaturaDAO.findById(id).orElse(null);
	}

	//servicio curso

	@Override
	@Transactional(readOnly = true)
	public List<Curso>listarCursos(){
		return cursoDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Curso>buscarCursosTodos(){
		return cursoDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Curso buscarCursoPorId(Long id) {
		return cursoDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void guardarCurso(Curso curso) {
		cursoDAO.save(curso);
	}

	@Override
	public List<Curso> listarCursosPorAsignaturaId(Long asignaturaId) {
    	return cursoDAO.findByAsignaturaId(asignaturaId); // Método en el repositorio de Curso
	}

	//servicios profesor
	@Override
	@Transactional(readOnly = true)
	public List<Profesor> buscarProfesoresTodos() {
		return profesorDAO.findAll();
	}
    
    
}
