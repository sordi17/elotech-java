package br.com.elotech.entrevista.service;

import br.com.elotech.entrevista.domain.Contato;
import br.com.elotech.entrevista.domain.Pessoa;
import br.com.elotech.entrevista.domain.dto.PessoaCreateDTO;
import br.com.elotech.entrevista.domain.dto.PessoaDTO;
import br.com.elotech.entrevista.domain.dto.PessoaUpdateDTO;
import br.com.elotech.entrevista.repository.PessoaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @InjectMocks
    PessoaService service;

    @Mock
    PessoaRepository repository;

    @Mock
    ModelMapper modelMapper;

    @Test
    @DisplayName("deve retornar a busca das pessoas paginadas")
    public void deveBuscarTodasPessasPaginada() {
        String filtro = "teste";
        Pageable pageable = carregaPageable();

        Page<Pessoa> page = mock(Page.class);
        when(repository.findAllWithFilter(pageable, filtro)).thenReturn(page);
        when(page.getSize()).thenReturn(5);

        Page<Pessoa> result = service.findPessoas(pageable, filtro);

        verify(repository).findAllWithFilter(pageable, filtro);
        assertNotNull(result);
        assertEquals(5, result.getSize());
    }

    @Test
    @DisplayName("deve retornar a busca da pessoa por id")
    public void deveBuscarPessoaPorId() {
        List<Contato> contatos = new ArrayList<>();
        Pessoa pessoa = carregaPessoa();

        when(repository.findById(1)).thenReturn(Optional.of(pessoa));
        when(modelMapper.map(pessoa, PessoaDTO.class)).thenReturn(carregaPessoaDTO());

        PessoaDTO result = service.findPessoaById(1);

        verify(repository).findById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(2, result.getContatos().size());
    }

    @Test
    @DisplayName("deve retornar null ao busca da pessoa por id")
    public void deveBuscarPessoaPorIdNull() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        PessoaDTO result = service.findPessoaById(1);

        verify(repository).findById(1);
        assertNull(result);
    }

    @Test
    @DisplayName("deve criar a pessoa")
    public void deveCriarPessoa() throws Exception {
        PessoaCreateDTO dto = carregaPessoaCreateDTO();
        Pessoa pessoa = carregaPessoa();

        when(modelMapper.map(dto, Pessoa.class)).thenReturn(pessoa);

        service.createPessoa(dto);

        verify(repository).save(modelMapper.map(dto, Pessoa.class));
    }

    @Test
    @DisplayName("deve criar a pessoa com cpf invalido lançando exessão")
    public void deveCriarPessoaCpfInvalido() throws Exception {
        PessoaCreateDTO dto = carregaPessoaCreateDTO();
        Pessoa pessoa = carregaPessoa();
        pessoa.setCpf("12345678910");

        when(modelMapper.map(dto, Pessoa.class)).thenReturn(pessoa);

        Exception result = assertThrows(Exception.class, () -> service.createPessoa(dto));

        assertEquals("CPF inválido", result.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    @DisplayName("deve criar a pessoa com data de nascimento invalido lançando exessão")
    public void deveCriarPessoaDataNascimentoInvalido() throws Exception {
        PessoaCreateDTO dto = carregaPessoaCreateDTO();
        Pessoa pessoa = carregaPessoa();

        Date hoje = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(hoje);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date amanha = cal.getTime();

        pessoa.setDataNascimento(amanha);

        when(modelMapper.map(dto, Pessoa.class)).thenReturn(pessoa);

        Exception result = assertThrows(Exception.class, () -> service.createPessoa(dto));

        assertEquals("Data de nascimento não pode ser futura", result.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    @DisplayName("deve criar a pessoa com email invalido lançando exessão")
    public void deveCriarPessoaEmailInvalido() throws Exception {
        PessoaCreateDTO dto = carregaPessoaCreateDTO();
        Pessoa pessoa = carregaPessoa();
        pessoa.getContatos().get(0).setEmail("email");

        when(modelMapper.map(dto, Pessoa.class)).thenReturn(pessoa);

        Exception result = assertThrows(Exception.class, () -> service.createPessoa(dto));

        assertEquals("Existem emails invalidos", result.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    @DisplayName("deve atualizar a pessoa")
    public void deveAtualizarPessoa() throws Exception {
        PessoaUpdateDTO dto = carregaPessoaUpdateDTO();
        Pessoa pessoa = carregaPessoa();

        when(repository.findById(dto.getId())).thenReturn(Optional.of(pessoa));
        when(modelMapper.map(dto, Pessoa.class)).thenReturn(pessoa);

        service.updatePessoa(dto);

        verify(repository).save(modelMapper.map(dto, Pessoa.class));
    }

    @Test
    @DisplayName("deve atualizar a pessoa inexistente")
    public void deveAtualizarPessoaInexistente() throws Exception {
        PessoaUpdateDTO dto = carregaPessoaUpdateDTO();

        when(repository.findById(dto.getId())).thenReturn(Optional.empty());

        Exception result = assertThrows(Exception.class, () -> service.updatePessoa(dto));

        assertEquals("Pessoa não encontrada", result.getMessage());
        verify(repository).findById(dto.getId());
    }

    @Test
    @DisplayName("deve deletar a pessoa")
    public void deveDeletarPessoa() throws Exception {

        service.deletePessoa(1);

        verify(repository).deleteById(1);
    }

    private Pessoa carregaPessoa() {
        List<Contato> contatos = new ArrayList<>();
        contatos.add(new Contato(1, 1, "primeiro contato", "11911111111", "primeiroemail@email.com"));
        contatos.add(new Contato(2, 1, "segundo contato", "11911112222", "segundoemail@email.com"));

        return new Pessoa(1, "pessoa teste", "08256850019", new Date(), contatos);
    }

    private PessoaDTO carregaPessoaDTO() {
        List<Contato> contatos = new ArrayList<>();
        contatos.add(new Contato(1, 1, "primeiro contato", "11911111111", "primeiroemail@email.com"));
        contatos.add(new Contato(2, 1, "segundo contato", "11911112222", "segundoemail@email.com"));

        return new PessoaDTO(1, "pessoa teste", "08256850019", new Date(), contatos);
    }

    private PessoaCreateDTO carregaPessoaCreateDTO() {
        List<Contato> contatos = new ArrayList<>();
        contatos.add(new Contato(1, 1, "primeiro contato", "11911111111", "primeiroemail@email.com"));
        contatos.add(new Contato(2, 1, "segundo contato", "11911112222", "segundoemail@email.com"));

        return new PessoaCreateDTO(1, "pessoa teste", "08256850019", new Date(), contatos);
    }

    private PessoaUpdateDTO carregaPessoaUpdateDTO() {
        List<Contato> contatos = new ArrayList<>();
        contatos.add(new Contato(1, 1, "primeiro contato", "11911111111", "primeiroemail@email.com"));
        contatos.add(new Contato(2, 1, "segundo contato", "11911112222", "segundoemail@email.com"));

        return new PessoaUpdateDTO(1, "pessoa teste", "08256850019", new Date(), contatos);
    }

    private Pageable carregaPageable() {
        return new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 10;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int pageNumber) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
    }
}