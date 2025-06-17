package com.bmt.webApp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.bmt.webApp.dto.TarefaDto;
import com.bmt.webApp.model.UserResponsavelDto;
import com.bmt.webApp.repository.ProjectsRepository;
import com.bmt.webApp.repository.UserRepository;
import com.bmt.webApp.service.TarefaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tarefas")
public class TarefasController {

    private static final Logger logger = LoggerFactory.getLogger(TarefasController.class);
    private final UserRepository userRepository;

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private ProjectsRepository pRepository;

    public TarefasController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/create/{id}")
    public String CreateTarefa(Model model, @PathVariable Long id){
        TarefaDto tarefDto = new TarefaDto();
        var projectId = pRepository.findById(id).orElse(null);
        if(projectId == null){
            return "projects/index";
        }
        
        model.addAttribute("projectId", projectId);
        model.addAttribute("newtarefasDto", tarefDto);
        return "projects/newTarefa";
    }

    @PostMapping("/create/{projectId}")
    public String createTarefa(@Valid @ModelAttribute TarefaDto tarefaDto,
                               @PathVariable(required = false) Long projectId, BindingResult result,
                               RedirectAttributes redirect){

        if(projectId == null){
             throw new IllegalArgumentException("ID do projeto é obrigatório.");
        }

        tarefaDto.setProjectId(projectId);

        if(result.hasErrors()){
            return "projects/index";
        }
        
        tarefaService.adicionarTarefa(tarefaDto, projectId);
        redirect.addFlashAttribute("successMessage", "Tarefa criada com sucesso!");
        logger.info("ID do projeto para Redirecionar. {}", projectId);
        return "redirect:/projects/details/" + projectId;
    }

    @GetMapping("/atribuir/{id}")
    public String formAtribuirResponsavel(@PathVariable Long id, Model model){

        UserResponsavelDto dto = new UserResponsavelDto();
        dto.setTarefaId(id);
       
        model.addAttribute("usuarios", userRepository.findAll());
        model.addAttribute("dto", dto);
        return "usuario/atribuir";
    }

    @PostMapping("/atribuir")
    public String atribuirResponsavel(@ModelAttribute("dto") UserResponsavelDto dto,
        RedirectAttributes redirect){

            if(dto.getTarefaId() == null){
                throw new IllegalArgumentException("ID da tarefa não pode ser nulo");
            }

            tarefaService.atribuirResponsavel(dto.getTarefaId(), dto.getUsuarioId());
            redirect.addFlashAttribute("successMessage", "Responsável atribuído com sucesso!");
            return "redirect:/projects/details/" +tarefaService.obterProjetoIdDaTarefa(dto.getTarefaId()); //ou redirect para o projeto relacionado
    }
    /**
     * 
     * @param id da tarefa para remover
     * @param usuarioId id do usuário atrelado a tarefa para remover também
     * @param redirect
     * @return retorna para a pagina inicial de projetos
     */
    @PostMapping("/{id}/delete")
    public String deletarTarefa(@PathVariable Long id,
                                @RequestParam("usuarioId") Long usuarioId,
                                RedirectAttributes redirect){

        try{
            tarefaService.removerTarefa(id, usuarioId);
            redirect.addFlashAttribute("successMessage", "Tarefa removida com sucesso!");
        } catch(IllegalArgumentException ex){
            redirect.addFlashAttribute("errorMessage", ex.getMessage());
        }

        return "redirect:/projects";
    }

    @PostMapping("/update/{id}")
    public String updateTarefa(@PathVariable Long id, TarefaDto dto,
                               RedirectAttributes redirect){
        dto.setId(id);
        tarefaService.atualizarTarefa(dto);

        redirect.addFlashAttribute("successMessage", "Tarefa atualizada com sucesso.");
        return "redirect:/projects/details/" +tarefaService.obterProjetoIdDaTarefa(id);
    }
}
