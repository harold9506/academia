package com.desarrolloweb.academia.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.desarrolloweb.academia.Model.entity.Asignatura;
import com.desarrolloweb.academia.Model.entity.Curso_Matriculado;
import com.desarrolloweb.academia.Model.entity.Estudiante;
import com.desarrolloweb.academia.Model.entity.Programa_Academico;
import com.desarrolloweb.academia.Model.service.AcademiaServiceIface;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/academia")
@SessionAttributes("estudiante")
public class EstudianteController {

    private final AcademiaServiceIface academiaService;

    public EstudianteController(AcademiaServiceIface academiaService) {
        this.academiaService = academiaService;
    }

    //localhost:8080/academia/inicio
    @GetMapping("/inicio")
    public String inicio() {
        return "principal/inicio"; 
    }

    //localhost:8080/academia/estudiantelistar
    @GetMapping("/estudiantelistar")
    public String estudianteListar(@RequestParam(value = "pag", defaultValue = "0") int pag, Model model) {
        List<Estudiante> estudiantes = academiaService.buscarEstudiantesTodos();
        model.addAttribute("titulo", "Listado de estudiantes");
        model.addAttribute("estudiantes", estudiantes);
        return "estudiante/listado_estudiantes";
    }

    //nuevo
    @GetMapping("/estudiantenuevo")
    public String estudianteNuevo(Model model) {
        //mostrar los programas academicos
        List<Programa_Academico> programa_academico = academiaService.listarProgramasAca();

        Estudiante estudiante = new Estudiante();
        model.addAttribute("programasaca", programa_academico);
        model.addAttribute("titulo", "Nuevo estudiante");
        model.addAttribute("accion", "Agregar");
        model.addAttribute("estudiante", estudiante);
        return "estudiante/formulario_estudiante";
    }

    @PostMapping("/estudianteguardar")
    public String estudianteGuardar(@Valid @ModelAttribute Estudiante estudiante, BindingResult result, RedirectAttributes flash,
            @RequestParam("programa_academico.id") Long programa_academicoId, Model model, SessionStatus status) {
    
        String accion = (estudiante.getId() == null) ? "agregado" : "modificado";
    
        // Si hay errores en la validación
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Nuevo estudiante");
            model.addAttribute("accion", accion);
            model.addAttribute("info", "Complete o corrija los campos del formulario.");
            return "estudiante/formulario_estudiante";
        }
    
        // Buscar el Programa Académico por ID
        Programa_Academico programa_academico = academiaService.buscarPromAcaPorId(programa_academicoId);
        if (programa_academico == null) {
            flash.addFlashAttribute("error", "El programa académico seleccionado no existe.");
            return "redirect:/academia/estudiantenuevo";
        }
    
        // Asociar el Programa Académico al Estudiante
        estudiante.setgetPrograma_academico(programa_academico);
    
        // Guardar el estudiante
        academiaService.guardarEstudiante(estudiante);
        status.setComplete();
    
        // Añadir mensaje de éxito y redirigir
        flash.addFlashAttribute("success", "El estudiante fue " + accion + " con éxito.");
        return "redirect:/academia/estudiantelistar";
    }
    

    @GetMapping("/estudianteconsultar/{id}")
    public String estudianteConsultar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Estudiante estudiante = academiaService.buscarEstudiantePorId(id);
        if (estudiante == null) {
            flash.addFlashAttribute("warning", "El estudiante con ID " + id + " no está en la base de datos");
            return "redirect:/academia/estudiantelistar";
        }
        model.addAttribute("titulo", "Consulta del estudiante: " + estudiante.getNombres() + " " + estudiante.getApellidos());
        model.addAttribute("estudiante", estudiante);
        return "estudiante/consulta_estudiante";
    }


    @GetMapping("/estudianteeliminar/{id}")
    public String estudianteEliminar(@PathVariable Long id, RedirectAttributes flash) {
        if (id > 0) {
            Estudiante estudiante = academiaService.buscarEstudiantePorId(id);
            if (estudiante != null) {
                academiaService.eliminarEstudiantePorId(id);;
                flash.addFlashAttribute("success", "El estudiante fue eliminado");
            }
            else {
                flash.addFlashAttribute("warning", "El estudiante con ID " + id + " no está en la base de datos");
            }
        }
        else {
            flash.addFlashAttribute("error", "El ID debería ser un valor positivo");
        }
        return "redirect:/academia/estudiantelistar";
    }

    @GetMapping("/estudiantemodificar/{id}")
    public String estudianteFormModificar(@PathVariable(value = "id") Long id, Model model) {
        Estudiante estudiante = null;
        if (id > 0) {
            estudiante = academiaService.buscarEstudiantePorId(id);
            if (estudiante == null) {
                return "redirect:/academia/estudiantelistar";
            }
        }
        model.addAttribute("accion", "Modificar");
        model.addAttribute("titulo", "Modificar estudiante");
        model.addAttribute("estudiante", estudiante);
        return "estudiante/formulario_estudiante";
    }

}
