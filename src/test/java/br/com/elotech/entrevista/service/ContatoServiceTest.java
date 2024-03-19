package br.com.elotech.entrevista.service;

import br.com.elotech.entrevista.domain.Contato;
import br.com.elotech.entrevista.domain.dto.ContatoCreateDTO;
import br.com.elotech.entrevista.domain.dto.ContatoDTO;
import br.com.elotech.entrevista.domain.dto.ContatoUpdateDTO;
import br.com.elotech.entrevista.repository.ContatoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContatoServiceTest {

    @InjectMocks
    private ContatoService service;

    @Mock
    private ContatoRepository repository;

    @Mock
    ModelMapper modelMapper;

    @Test
    @DisplayName("deve retornar a busca dos contatos pelo id da pessoa")
    public void deveBuscarContatosPorPessoaId() {
        Contato contato = mock(Contato.class);

        List<Contato> contatos = new ArrayList<>();
        contatos.add(contato);

        when(repository.findAllByPessoaId(1)).thenReturn(contatos);

        List<Contato> result = service.findContatosByPessoaId(1);

        verify(repository).findAllByPessoaId(1);
        assertTrue(result.size() > 0);
    }

    @Test
    @DisplayName("deve retornar a busca dos contatos pelo id")
    public void deveBuscarContatosPorId() {
        Contato contato = mock(Contato.class);
        when(repository.findById(1)).thenReturn(Optional.of(contato));

        ContatoDTO dto = mock(ContatoDTO.class);
        when(modelMapper.map(contato, ContatoDTO.class)).thenReturn(dto);

        ContatoDTO result = service.findContatoById(1);

        verify(repository).findById(1);
        assertNotNull(result);
    }

    @Test
    @DisplayName("deve retornar null a busca dos contatos pelo id")
    public void deveBuscarContatosPorIdNull() {
        Contato contato = mock(Contato.class);
        when(repository.findById(1)).thenReturn(Optional.of(contato));

        when(modelMapper.map(contato, ContatoDTO.class)).thenReturn(null);

        ContatoDTO result = service.findContatoById(1);

        verify(repository).findById(1);
        assertNull(result);
    }

    @Test
    @DisplayName("deve criar contato")
    public void deveCriarContato() throws Exception {
        ContatoCreateDTO dto = mock(ContatoCreateDTO.class);

        Contato contato = new Contato(1, 1, "nome contato", "11911111111", "email@email.com.br");
        when(modelMapper.map(dto, Contato.class)).thenReturn(contato);

        service.createContato(dto);

        verify(repository).save(modelMapper.map(dto, Contato.class));
    }

    @Test
    @DisplayName("deve criar contato com email errado")
    public void deveCriarContatoEmailErrado() throws Exception {
        ContatoCreateDTO dto = mock(ContatoCreateDTO.class);

        Contato contato = new Contato(1, 1, "nome contato", "11911111111", "email");
        when(modelMapper.map(dto, Contato.class)).thenReturn(contato);

        Exception result = assertThrows(Exception.class, () -> service.createContato(dto));

        assertEquals("Emails " + contato.getEmail() + " invalido", result.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    @DisplayName("deve atualizar contato")
    public void deveAtualizarContato() throws Exception {
        ContatoUpdateDTO dto = mock(ContatoUpdateDTO.class);
        Contato contato = new Contato(1, 1, "nome contato", "11911111111", "email@email.com.br");

        when(dto.getId()).thenReturn(1);
        when(modelMapper.map(dto, Contato.class)).thenReturn(contato);
        when(repository.findById(dto.getId())).thenReturn(Optional.of(contato));

        service.updateContato(dto);

        verify(repository).findById(dto.getId());
    }

    @Test
    @DisplayName("deve atualizar contato inexistente")
    public void deveAtualizarContatoInxistente() throws Exception {
        ContatoUpdateDTO dto = mock(ContatoUpdateDTO.class);
        Contato contato = new Contato(1, 1, "nome contato", "11911111111", "email@email.com.br");

        when(dto.getId()).thenReturn(1);
        when(repository.findById(dto.getId())).thenReturn(Optional.empty());

        Exception result = assertThrows(Exception.class, () -> service.updateContato(dto));

        assertEquals("Contato não encontrado", result.getMessage());
        verify(repository).findById(dto.getId());
    }

    @Test
    @DisplayName("deve deletar contato")
    public void deveDeletearContato() {
        service.deleteContato(1);

        verify(repository).deleteById(1);
    }

}