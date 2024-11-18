package com.desarrolloweb.academia.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.desarrolloweb.academia.Model.entity.Profesor;
import com.desarrolloweb.academia.Model.service.AcademiaServiceIface;

@Controller
@RequestMapping("/academia")
@SessionAttributes("profesor")
public class ProfesorController {

    private final AcademiaServiceIface academiaService;

    public ProfesorController(AcademiaServiceIface academiaService) {
        this.academiaService = academiaService;
    }

    //localhost:8080/academia/profesorlistar
    @GetMapping("/profesorlistar")
    public String profesorListar(@RequestParam(value = "pag", defaultValue = "0") int pag, Model model) {
        List<Profesor> profesores = academiaService.buscarProfesoresTodos();
        model.addAttribute("titulo", "Listado de profesores");
        model.addAttribute("profesores", profesores);
        return "profesor/listado_profesores";
    }
    
}
