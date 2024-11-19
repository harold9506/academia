package com.desarrolloweb.academia.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.desarrolloweb.academia.Model.entity.*;
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
        List<Asignatura> asignaturas = academiaService.listarAsignaturas();
        System.out.println("Asignaturas cargadas: " + asignaturas.size());
        return asignaturas;
    }

    @ModelAttribute("cursos")
    public List<Curso> listarCursos() {
        return academiaService.listarCursos();
    }

    @ModelAttribute("matricula")
    public Curso_Matriculado cargaMatricula() {
        Curso_Matriculado matricula = new Curso_Matriculado();
        matricula.setEstado_curso("Inscrito"); // Establece un valor por defecto
        // Inicializa 'curso' y 'asignatura' de alguna manera
        return matricula;
    }

    // Método para cargar cursos según la asignatura seleccionada
    @GetMapping("/asignatura/{id}/cursos")
    @ResponseBody
    public List<Curso> obtenerCursosPorAsignatura(@PathVariable Long id) {
        return academiaService.listarCursosPorAsignaturaId(id);
    }

    // Acción para matricular un curso
    @GetMapping("/matricular_curso/{id}")
    public String matriculaNueva(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Estudiante estudiante = academiaService.buscarEstudiantePorId(id);
        if (estudiante == null) {
            flash.addFlashAttribute("warning", "El estudiante con id " + id + " no existe");
            return "redirect:/academia/estudiantelistar";
        }

        // Crear nueva matrícula
        Curso_Matriculado matricula = new Curso_Matriculado();
        matricula.setEstudiante(estudiante);
        matricula.setCurso(academiaService.listarCursosPorAsignaturaId(id).get(0));
        model.addAttribute("matricula", matricula);
        model.addAttribute("titulo", "Nueva matricula");

        // Obtener asignaturas y cursos
        List<Asignatura> asignaturas = academiaService.listarAsignaturas();
        model.addAttribute("asignaturas", asignaturas);

        if (matricula.getCurso() != null) {
            // Si ya se seleccionó un curso, obtener la asignatura asociada
            Asignatura asignatura = matricula.getCurso().getAsignatura();
            model.addAttribute("asignatura", asignatura);
            
            // Obtener los cursos asociados a la asignatura seleccionada
            model.addAttribute("cursos", academiaService.listarCursosPorAsignaturaId(matricula.getCurso().getAsignatura().getId()));
        }

        return "matricular_curso/matricula_nueva";
    }


    // Método para obtener los detalles del curso y asignarlo al modelo
    @GetMapping("/curso/{id}")
    @ResponseBody
    public Curso obtenerDetallesCurso(@PathVariable Long id) {
        return academiaService.buscarCursoPorId(id);
    }


    @GetMapping("/obtenerDetallesAsignatura/{id}")
    public ResponseEntity<Map<String, Object>> obtenerDetallesAsignatura(@PathVariable Long id) {
        // Obtener la asignatura por su ID
        Asignatura asignatura = academiaService.buscarAsignaturaPorId(id);
        
        if (asignatura != null) {
            // Preparar la respuesta con los detalles que necesitas
            Map<String, Object> detalles = new HashMap<>();
            detalles.put("numero_creditos", asignatura.getNumero_creditos());
            detalles.put("departamento", asignatura.getDepartamento());
    
            // Agregar un log para verificar que se están enviando los datos correctamente
            System.out.println("Asignatura: " + asignatura.getNombre() + ", Créditos: " + asignatura.getNumero_creditos() + ", Departamento: " + asignatura.getDepartamento());
    
            return ResponseEntity.ok(detalles); // Retorna los detalles como JSON
        } else {
            return ResponseEntity.notFound().build(); // Si no se encuentra la asignatura, retorna 404
        }
    }

    // Método para obtener los detalles del curso por asignatura
    @GetMapping("/obtenerDetallesCursoPorAsignatura/{asignaturaId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> obtenerDetallesCursoPorAsignatura(@PathVariable Long asignaturaId) {
        // Obtener los cursos asociados a la asignatura
        List<Curso> cursos = academiaService.listarCursosPorAsignaturaId(asignaturaId);

        if (!cursos.isEmpty()) {
            // Usamos el primer curso (o puedes adaptar esto si necesitas seleccionar entre varios)
            Curso curso = cursos.get(0);

            // Preparar los detalles del curso
            Map<String, Object> detallesCurso = new HashMap<>();
            detallesCurso.put("id", curso.getId());
            detallesCurso.put("periodo", curso.getPeriodo());
            detallesCurso.put("horario", curso.getHorario());
            detallesCurso.put("cupo_maximo", curso.getCupo_maximo());
            detallesCurso.put("aula", curso.getAula());

            return ResponseEntity.ok(detallesCurso); // Retorna los detalles del curso como JSON
        } else {
            return ResponseEntity.notFound().build(); // Si no hay cursos, retorna 404
        }
    }


    @PostMapping("/guardar_matricula")
    public String guardarMatricula(@RequestParam("estado_curso") String estadoCurso, @RequestParam("curso.id") Long cursoId, @RequestParam("curso_matriculado.nota_final") Double notaFinal,
        @RequestParam("estudiante.id") Long estudianteId, RedirectAttributes flash, Model model, SessionStatus status) {
        
        // Validar que se haya enviado el ID del estudiante
        if (estudianteId == null) {
            model.addAttribute("titulo", "Nueva matrícula");
            model.addAttribute("error", "Debe seleccionar un estudiante válido.");
            return "matricular_curso/matricula_nueva";
        }
        // Validar que se haya seleccionado un curso
        if (cursoId == null) {
            model.addAttribute("titulo", "Nueva matrícula");
            model.addAttribute("error", "Debe seleccionar un curso válido.");
            return "matricular_curso/matricula_nueva";
        }
    
        // Obtener curso asociado desde la base de datos
        Curso curso = academiaService.buscarCursoPorId(cursoId);
        if (curso == null) {
            model.addAttribute("titulo", "Nueva matrícula");
            model.addAttribute("error", "El curso seleccionado no existe.");
            return "matricular_curso/matricula_nueva";
        }

        // Obtener estudiante asociado desde la base de datos
        Estudiante estudiante = academiaService.buscarEstudiantePorId(estudianteId);
        if (estudiante == null) {
            model.addAttribute("titulo", "Nueva matrícula");
            model.addAttribute("error", "El estudiante seleccionado no existe.");
            return "matricular_curso/matricula_nueva";
        }
    
        // Crear objeto de Curso_Matriculado
        Curso_Matriculado matricula = new Curso_Matriculado();
        matricula.setCurso(curso);
        matricula.setEstudiante(estudiante);
        matricula.setEstado_curso(estadoCurso);
        matricula.setNota_final(notaFinal);
        matricula.setPeriodo(curso.getPeriodo()); // Usar el periodo del curso
    
        // Guardar matrícula en la base de datos
        academiaService.guardarCusuoMatriculado(matricula);
    
        // Limpiar la sesión y agregar mensaje de éxito
        status.setComplete();
        flash.addFlashAttribute("success", "La matrícula fue agregada con éxito.");
        return "redirect:/academia/estudiantelistar";
    }
    


}
