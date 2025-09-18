package com.bmt.webApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bmt.webApp.dto.ProjectDto;
import com.bmt.webApp.repository.ProjectsRepository;
import com.bmt.webApp.service.ProjectService;
import com.bmt.webApp.service.TarefaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/projects")
public class ProjectsController {

    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TarefaService tarefaService;

    //lista todos os projetos com paginação, filtro por status e busca
    @GetMapping({"", "/"})
    public String getProjects(@RequestParam(defaultValue= "0") int page,
                            @RequestParam(defaultValue= "10") int size,
                            @RequestParam(required = false) String status,
                            @RequestParam(required = false) String search,
                             Model model){

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<ProjectDto> projectPage;

        if (search != null && !search.isEmpty()) {
            projectPage = projectService.searchProjects(search, pageable);
        } else if (status != null && !status.isEmpty()) {
            projectPage = projectService.findByStatus(status, pageable);
        } else {
            projectPage = projectService.findAllProjects(pageable);
        }

        model.addAttribute("projects", projectPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", projectPage.getTotalPages());  
        model.addAttribute("totalItems", projectPage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("status", status);
        model.addAttribute("search", search);   

        return "projects/index";
    }

    //cria um novo projeto
    @GetMapping("/created")
    public String createdProject(@RequestParam(value = "success", required= false) String success,
    Model model){
        
        ProjectDto project = new ProjectDto();
        
        model.addAttribute("projectDto", project);

         if(success != null){//SE CADASTRAR COM SUCESSO APARECE MENSAGEM DE CADASTRO COM SUCESSO
            model.addAttribute("successMessage", "Project created successfully!");
        }

        return "projects/create";
    }

    @PostMapping("/created")
    public String createdProject(@Valid @ModelAttribute("projectDto") ProjectDto projectDto, 
                                 Model model, BindingResult result){

        if(result.hasErrors()){//SE TIVER ERRO INFORMA ERRO
            return "projects/create";
        }
       
        projectService.createProject(projectDto);
        return "redirect:/projects/created?success";
    }

    @GetMapping("/details/{id}")
    public String detailProject(Model model, @PathVariable Long id){

        var project = projectsRepository.findById(id).orElse(null);
        if(project == null){
            return "projects/index";
        }

        projectService.detailProject(id);
        model.addAttribute("project", project);
        model.addAttribute("tarefas", tarefaService.listarTarefasPorProjeto(id));

        return "projects/details";
    }

    @GetMapping("/edit/{id}")
    public String editProject(@PathVariable("id") Long id, Model model){

        ProjectDto projectDto = projectService.getProjectById(id);
        if(projectDto == null){
            return "redirect:/projects";
        }
        model.addAttribute("projectDto", projectDto);
        model.addAttribute("projectId", id);
        return "projects/edit";
    }
 
    @PostMapping("/edit/{id}")
    public String updateProject(@PathVariable("id") Long id, 
                                @Valid @ModelAttribute("projectDto") ProjectDto projectDto,
                                BindingResult result, RedirectAttributes redirectAttributes,
                                Model model){

        if(result.hasErrors()){
            model.addAttribute("projectId", id);
            return "projects/edit";
        }  
        
        projectDto.setId(id);
        projectService.updateProject(id, projectDto);
        redirectAttributes.addFlashAttribute("successMessage", "Projeto atualizado com sucesso!");
        return "redirect:/projects";

    }

    @GetMapping("/delete/{id}")
    public String deletProject(@PathVariable Long id, RedirectAttributes redirect){

        projectService.deletProject(id);
        redirect.addFlashAttribute("successMessage", "Projeto excluído com sucesso!");
        return "redirect:/projects";
    }

    
}
