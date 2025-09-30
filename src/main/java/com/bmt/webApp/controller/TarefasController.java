package com.bmt.webApp.controller;

import java.util.Arrays;

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
import com.bmt.webApp.dto.UserResponsavelDto;
import com.bmt.webApp.repository.UserRepository;
import com.bmt.webApp.service.TarefaService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/tarefas")
public class TarefasController {

    private static final Logger logger = LoggerFactory.getLogger(TarefasController.class);
    private final UserRepository userRepository;

    @Autowired
    private TarefaService tarefaService;

    public TarefasController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/create/{projectId}")
    public String CreateTarefa(Model model, @PathVariable Long projectId){

        TarefaDto tarefaDto = new TarefaDto();

        if(projectId == null){
            return "projects/index";
        }
        
        model.addAttribute("projectId", projectId);
        model.addAttribute("tarefaDto", tarefaDto);
        return "projects/newTarefa";
    }

    @PostMapping("/create/{projectId}")
    public String createTarefa(@Valid @ModelAttribute("tarefaDto") TarefaDto tarefaDto,
                               BindingResult result,
                               @PathVariable Long projectId, 
                               Model model,
                               RedirectAttributes redirect){

    logger.info("Recebendo criação de tarefa para projeto: {}", projectId);
    logger.info("Título: {}", tarefaDto.getTitulo());
    logger.info("Descrição: {}", tarefaDto.getDescricao());
    logger.info("Data Início: {}", tarefaDto.getDataInicio());
    logger.info("Data Fim: {}", tarefaDto.getDataFim());

        if(projectId == null){
             throw new IllegalArgumentException("ID do projeto é obrigatório.");
        }

        if(result.hasErrors()){// Se tiver erros retorna para a página
            logger.error("Erros de validação encontrados: {}", result.getFieldErrors());
            model.addAttribute("newtarefasDto", tarefaDto);
            model.addAttribute("projectId", projectId);
            //mensagem de erro
            model.addAttribute("errorMessage", "Ocorreu um erro ao criar a tarefa. Verifique os campos e tente novamente.");
            return "projects/newTarefa"; //retorna para a view sem reidirecionar
        }
        
        try{

            tarefaService.adicionarTarefa(tarefaDto, projectId);
            redirect.addFlashAttribute("successMessage", "Tarefa salva com sucesso!");
            logger.info("ID do projeto para Redirecionar. {}", projectId);
            return "redirect:/projects/details/" + projectId; // redireciona para a pagina de detalhes do projeto

        }catch(Exception e){
            redirect.addFlashAttribute("errorMessage", "Erro ao criar tarefa: " + e.getMessage());
            return "redirect:/projects/newTarefa?projectId=" + projectId;
        }
    }

    @GetMapping("/atribuir/{id}")
    public String formAtribuirResponsavel(@PathVariable Long id, Model model){

        UserResponsavelDto dto = new UserResponsavelDto();
        dto.setTarefaId(id);

        //Buscar a tarefa pelo ID para garantir que ela existe
        TarefaDto tarefa = tarefaService.buscarPorId(id);
       
        model.addAttribute("usuarios", userRepository.findAll());
        model.addAttribute("dto", dto);
        model.addAttribute("tarefa", tarefa);
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
                                 //torna opcional o ID do employer para excluir tarefa, caso a tarefa não tenha employer atribuído.
                                @RequestParam(value = "usuarioId", required = false) Long usuarioId,
                                HttpServletRequest request,
                                RedirectAttributes redirect){

        // Debug: ver todos os parametros
        logger.info("Recebendo exclusão de tarefa para projeto: {}", id);
        request.getParameterMap().forEach((key, value) -> {
            logger.info("{} = {}", key, Arrays.toString(value));
        });
        logger.info("Tarefa ID: {}, Usuario ID: {}", id, usuarioId);

        // Busca o id do projeto antes de remover a tarefa
        Long projectId = tarefaService.obterProjetoIdDaTarefa(id);

        try{
            
            tarefaService.removerTarefa(id, usuarioId);
            redirect.addFlashAttribute("successMessage", "Tarefa removida com sucesso!");

             // após remover tarefa redireciona para o detalhes do projeto com as tarefas que sobraram
            return "redirect:/projects/details/" + projectId;
        
        } catch(IllegalArgumentException ex){
            redirect.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/projects/details/" + projectId; // Fallback se não encontrar o projectId
        }

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
