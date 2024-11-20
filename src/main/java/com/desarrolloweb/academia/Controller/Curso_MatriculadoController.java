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
        matricula.setEstado_curso("Inscrito"); 
        return matricula;
    }

    @GetMapping("/asignatura/{id}/cursos")
    @ResponseBody
    public List<Curso> obtenerCursosPorAsignatura(@PathVariable Long id) {
        return academiaService.listarCursosPorAsignaturaId(id);
    }

    @GetMapping("/matricular_curso/{id}")
    public String matriculaNueva(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Estudiante estudiante = academiaService.buscarEstudiantePorId(id);
        if (estudiante == null) {
            flash.addFlashAttribute("warning", "El estudiante con id " + id + " no existe");
            return "redirect:/academia/estudiantelistar";
        }
    
        Curso_Matriculado matricula = new Curso_Matriculado();
        matricula.setEstudiante(estudiante);
        List<Curso> cursos = academiaService.listarCursosPorAsignaturaId(id);
        if (!cursos.isEmpty()) {
            matricula.setCurso(cursos.get(0));
        }
    
        model.addAttribute("matricula", matricula);
        model.addAttribute("titulo", "Nueva matricula");
        List<Asignatura> asignaturas = academiaService.listarAsignaturas();
        model.addAttribute("asignaturas", asignaturas);
    
        if (matricula.getCurso() != null) {
            Asignatura asignatura = matricula.getCurso().getAsignatura();
            model.addAttribute("asignatura", asignatura);
            model.addAttribute("cursos", cursos);
        }
    
        return "matricular_curso/matricula_nueva";
    }


    @GetMapping("/curso/{id}")
    @ResponseBody
    public Curso obtenerDetallesCurso(@PathVariable Long id) {
        return academiaService.buscarCursoPorId(id);
    }


    @GetMapping("/obtenerDetallesAsignatura/{id}")
    public ResponseEntity<Map<String, Object>> obtenerDetallesAsignatura(@PathVariable Long id) {
        Asignatura asignatura = academiaService.buscarAsignaturaPorId(id);
        
        if (asignatura != null) {
            Map<String, Object> detalles = new HashMap<>();
            detalles.put("numero_creditos", asignatura.getNumero_creditos());
            detalles.put("departamento", asignatura.getDepartamento());
    
            System.out.println("Asignatura: " + asignatura.getNombre() + ", Créditos: " + asignatura.getNumero_creditos() + ", Departamento: " + asignatura.getDepartamento());
    
            return ResponseEntity.ok(detalles); 
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/obtenerDetallesCursoPorAsignatura/{asignaturaId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> obtenerDetallesCursoPorAsignatura(@PathVariable Long asignaturaId) {
        List<Curso> cursos = academiaService.listarCursosPorAsignaturaId(asignaturaId);

        if (!cursos.isEmpty()) {
            Curso curso = cursos.get(0);

            Map<String, Object> detallesCurso = new HashMap<>();
            detallesCurso.put("id", curso.getId());
            detallesCurso.put("periodo", curso.getPeriodo());
            detallesCurso.put("horario", curso.getHorario());
            detallesCurso.put("cupo_maximo", curso.getCupo_maximo());
            detallesCurso.put("aula", curso.getAula());

            return ResponseEntity.ok(detallesCurso); 
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }


    @PostMapping("/guardar_matricula")
    public String guardarMatricula(@RequestParam("estado_curso") String estadoCurso, @RequestParam("curso.id") Long cursoId, @RequestParam("curso_matriculado.nota_final") Double notaFinal,
        @RequestParam("estudiante.id") Long estudianteId, RedirectAttributes flash, Model model, SessionStatus status) {
        
        if (estudianteId == null) {
            model.addAttribute("titulo", "Nueva matrícula");
            model.addAttribute("error", "Debe seleccionar un estudiante válido.");
            return "matricular_curso/matricula_nueva";
        }

        if (cursoId == null) {
            model.addAttribute("titulo", "Nueva matrícula");
            model.addAttribute("error", "Debe seleccionar un curso válido.");
            return "matricular_curso/matricula_nueva";
        }

        if (notaFinal == null || notaFinal < 0 || notaFinal > 5) {
            model.addAttribute("error", "Debe ingresar una nota válida entre 0 y 5.");
            model.addAttribute("titulo", "Nueva matrícula");
            return "matricular_curso/matricula_nueva";
        }

        Curso curso = academiaService.buscarCursoPorId(cursoId);
        if (curso == null) {
            model.addAttribute("titulo", "Nueva matrícula");
            model.addAttribute("error", "El curso seleccionado no existe.");
            return "matricular_curso/matricula_nueva";
        }

        Estudiante estudiante = academiaService.buscarEstudiantePorId(estudianteId);
        if (estudiante == null) {
            model.addAttribute("titulo", "Nueva matrícula");
            model.addAttribute("error", "El estudiante seleccionado no existe.");
            return "matricular_curso/matricula_nueva";
        }

        Asignatura asignatura = curso.getAsignatura();
        // 1. Validar que la asignatura no haya sido cursada previamente
        boolean asignaturaCursada = estudiante.getCurso_Matriculado().stream()
        .anyMatch(m -> m.getCurso().getAsignatura().getId().equals(asignatura.getId()) 
                    && List.of("inscrito", "en curso", "completado")
                        .contains(m.getEstado_curso().toLowerCase()));
    

        if (asignaturaCursada) {
            model.addAttribute("titulo", "Nueva matrícula");
            model.addAttribute("error", "Ya esta inscrita la asignatura " + asignatura.getNombre() + ".");
            return "matricular_curso/matricula_nueva";
        }

        List<String> ESTADOS_VALIDOS_PRERREQUISITO = List.of("Inscrito", "En curso", "Completado");
        // 2. Validar prerrequisitos
        if (asignatura.getAsignatura_Planes() != null) {
            Long idPrerrequisito = asignatura.getAsignatura_Planes().get(0).getPrerrequisito();
            if (idPrerrequisito != null) {
                // Validar que el prerrequisito esté cumplido
                boolean prerrequisitoCumplido = estudiante.getCurso_Matriculado().stream()
                    .anyMatch(m -> m.getCurso().getAsignatura().getId().equals(idPrerrequisito)
                                && ESTADOS_VALIDOS_PRERREQUISITO.contains(m.getEstado_curso()));

                if (!prerrequisitoCumplido) {
                    model.addAttribute("titulo", "Nueva matrícula");
                    model.addAttribute("error", "Debes aprobar el prerrequisito antes de matricular esta asignatura.");
                    return "matricular_curso/matricula_nueva";
                }
            }
        }

        // 3. Validar que no exceda 22 créditos
        int totalCreditos = estudiante.getCurso_Matriculado().stream()
            .mapToInt(m -> m.getCurso().getAsignatura().getNumero_creditos())
            .sum();

        if (totalCreditos + asignatura.getNumero_creditos() > 22) {
            model.addAttribute("titulo", "Nueva matrícula");
            model.addAttribute("error", "No puedes exceder el límite de 22 créditos por semestre.");
            return "matricular_curso/matricula_nueva";
        }
    
        Curso_Matriculado matricula = new Curso_Matriculado();
        matricula.setCurso(curso);
        matricula.setEstudiante(estudiante);
        matricula.setEstado_curso(estadoCurso);
        matricula.setNota_final(notaFinal);
        matricula.setPeriodo(curso.getPeriodo());
    
        academiaService.guardarCusuoMatriculado(matricula);
        status.setComplete();
        flash.addFlashAttribute("success", "La matrícula fue agregada con éxito.");
        return "redirect:/academia/estudiantelistar";
    }
    


}
