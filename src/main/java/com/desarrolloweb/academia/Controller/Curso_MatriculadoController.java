package com.desarrolloweb.academia.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.desarrolloweb.academia.Model.entity.Asignatura;
import com.desarrolloweb.academia.Model.entity.Curso;
import com.desarrolloweb.academia.Model.entity.Curso_Matriculado;
import com.desarrolloweb.academia.Model.entity.Estudiante;
import com.desarrolloweb.academia.Model.service.AcademiaServiceIface;


@Controller
@RequestMapping("/academia")
@SessionAttributes("matricula")
public class Curso_MatriculadoController {

    private final AcademiaServiceIface academiaService;

    public Curso_MatriculadoController(AcademiaServiceIface academiaService) {
        this.academiaService = academiaService;
    }

    @ModelAttribute("asignaturas")
    public List<Asignatura> listarAsignaturas() {
        return academiaService.listarAsignaturas(); // Método en el servicio que obtiene todas las asignaturas
    }

    @ModelAttribute("cursos")
    public List<Curso> listarCursos(){
        return academiaService.listarCursos(); 
    }

    // Obtener cursos asociados a una asignatura
    @GetMapping("/asignatura/{id}/cursos")
    @ResponseBody
    public List<Curso> obtenerCursosPorAsignatura(@PathVariable Long id) {
        return academiaService.listarCursosPorAsignaturaId(id); // Método en el servicio
    }


    @GetMapping("/matricular_curso/{id}")
    public String matriculaNueva(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Estudiante estudiante = academiaService.buscarEstudiantePorId(id);

        if (estudiante == null) {
            flash.addFlashAttribute("warning", "El estudiante con id " + id + " no existe");
            return "redirect:/academia/estudiantelistar";
        }

        // Obtener la lista de asignaturas
        List<Asignatura> asignaturas = academiaService.listarAsignaturas();
        List<Curso> cursos = academiaService.listarCursos();

        Curso_Matriculado matricula = new Curso_Matriculado();
        matricula.setEstudiante(estudiante);

        model.addAttribute("titulo", "Matrícula nueva");
        model.addAttribute("btn_accion", "Guardar Matrícula");
        model.addAttribute("matricula", matricula);
        model.addAttribute("asignaturas", asignaturas); // Pasar asignaturas al modelo
        model.addAttribute("cursos", cursos);
        return "matricular_curso/matricula_nueva";
    }


    
    @GetMapping("/curso/{id}")
    @ResponseBody
    public Curso obtenerDetallesCurso(@PathVariable Long id) {
        return academiaService.buscarCursoPorId(id); // Servicio que recupera un curso por ID
    }

    @GetMapping("/asignatura/{id}")
    @ResponseBody
    public Asignatura obtenerDetallesAsignatura(@PathVariable Long id) {
        Asignatura asignatura = academiaService.buscarAsignaturaPorId(id);
    if (asignatura == null) {
        throw new RuntimeException("Asignatura no encontrada");
    }
    return asignatura;
    }



}
