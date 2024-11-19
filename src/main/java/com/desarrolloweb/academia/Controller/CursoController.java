package com.desarrolloweb.academia.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.desarrolloweb.academia.Model.entity.Curso;
import com.desarrolloweb.academia.Model.service.AcademiaServiceIface;

@Controller
@RequestMapping("/academia")
@SessionAttributes("curso")
public class CursoController {

    private final AcademiaServiceIface academiaService;

    public CursoController(AcademiaServiceIface academiaService) {
        this.academiaService = academiaService;
    }

    //localhost:8080/academia/cursolistar
    @GetMapping("/cursolistar")
    public String cursosListar(@RequestParam(value = "pag", defaultValue = "0") int pag, Model model) {
        List<Curso> cursos = academiaService.listarCursos();
        model.addAttribute("titulo", "Listado de cursos");
        model.addAttribute("cursos", cursos);
        return "curso/listado_cursos";
    }
    
    


}
