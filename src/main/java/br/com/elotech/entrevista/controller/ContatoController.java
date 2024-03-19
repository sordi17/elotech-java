package br.com.elotech.entrevista.controller;

import br.com.elotech.entrevista.domain.dto.ContatoCreateDTO;
import br.com.elotech.entrevista.domain.dto.ContatoUpdateDTO;
import br.com.elotech.entrevista.service.ContatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/contato")
@Tag(name = "Contato")
public class ContatoController {

    @Autowired
    private ContatoService service;

    @GetMapping(value = "/pessoa/{pessoaId}")
    @Operation(summary = "Localiza uma lista de contatos a partir do id da pessoa")
    @ApiResponse(responseCode = "200", description = "Resposta com status 200 contendo uma lista de contatos")
    public ResponseEntity<?> getContatosByPessoaId(@PathVariable Integer pessoaId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findContatosByPessoaId(pessoaId));
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Localiza um contato a partir do id")
    @ApiResponse(responseCode = "200", description = "Resposta com status 200 contendo um contato")
    public ResponseEntity<?> getContatoById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findContatoById(id));
    }

    @PostMapping
    @Operation(summary = "Cria um contato")
    @ApiResponse(responseCode = "200", description = "Resposta com status 201")
    public ResponseEntity<?> postContato(@RequestBody ContatoCreateDTO request) throws Exception {
        service.createContato(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Contato criado com sucesso");
    }

    @PutMapping
    @Operation(summary = "Altera um contato")
    @ApiResponse(responseCode = "200", description = "Resposta com status 200")
    public ResponseEntity<?> putContato(@RequestBody ContatoUpdateDTO request) throws Exception {
        service.updateContato(request);
        return ResponseEntity.status(HttpStatus.OK).body("Contato alterado com sucesso");
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deleta um contato")
    @ApiResponse(responseCode = "200", description = "Resposta com status 200")
    public ResponseEntity<?> deleteContato(@PathVariable Integer id) {
        service.deleteContato(id);
        return ResponseEntity.status(HttpStatus.OK).body("Contato deletado com sucesso");
    }

}
