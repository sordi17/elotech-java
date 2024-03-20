package br.com.elotech.entrevista.controller;

import br.com.elotech.entrevista.domain.dto.PessoaCreateDTO;
import br.com.elotech.entrevista.domain.dto.PessoaDTO;
import br.com.elotech.entrevista.domain.dto.PessoaUpdateDTO;
import br.com.elotech.entrevista.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pessoa")
@Tag(name = "Pessoas")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<?> getPessoas(@PageableDefault(size = 10) Pageable pageable, @RequestParam String filtro) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findPessoas(pageable, filtro));
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    @Operation(summary = "Retorna uma pessoa localizada pelo id.")
    @ApiResponse(responseCode = "200", description = "Resposta com pessoa localizada pelo id")
    public ResponseEntity<PessoaDTO> getPessoa(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findPessoaById(id));
    }

    @CrossOrigin
    @PostMapping
    @Operation(summary = "Cria uma pessoa")
    @ApiResponse(responseCode = "200", description = "Resposta de sucesso ao criar uma pessoa")
    public ResponseEntity<?> postPessoa(@Valid @RequestBody PessoaCreateDTO request) throws Exception {
        service.createPessoa(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pessoa criada com sucesso");
    }

    @CrossOrigin
    @PutMapping
    @Operation(summary = "Altera os dados de uma pessoa")
    @ApiResponse(responseCode = "200", description = "Resposta de sucesso ao alterar uma pessoa")
    public ResponseEntity<?> putPessoa(@Valid @RequestBody PessoaUpdateDTO request) throws Exception {
        service.updatePessoa(request);
        return ResponseEntity.status(HttpStatus.OK).body("Pessoa alterada com sucesso");
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deleta uma s dados de uma pessoa")
    @ApiResponse(responseCode = "200", description = "Resposta de sucesso ao deletar uma pessoa")
    public ResponseEntity<?> deletePessoa(@PathVariable Integer id) {
        service.deletePessoa(id);
        return ResponseEntity.status(HttpStatus.OK).body("Pessoa deletada com sucesso");
    }
}
