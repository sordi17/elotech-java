package br.com.elotech.entrevista.service;

import br.com.elotech.entrevista.domain.Contato;
import br.com.elotech.entrevista.domain.Pessoa;
import br.com.elotech.entrevista.domain.dto.PessoaCreateDTO;
import br.com.elotech.entrevista.domain.dto.PessoaDTO;
import br.com.elotech.entrevista.domain.dto.PessoaUpdateDTO;
import br.com.elotech.entrevista.repository.PessoaRepository;
import br.com.elotech.entrevista.util.CpfValido;
import br.com.elotech.entrevista.util.EmailValido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    @Autowired
    private ModelMapper modelMapper;


    public Page<Pessoa> findPessoas(Pageable pageable, String filtro) {
        return repository.findAllWithFilter(pageable, filtro);
    }

    public PessoaDTO findPessoaById(Integer id) {
        Optional<Pessoa> result = repository.findById(id);
        return result.map(pessoa -> modelMapper.map(pessoa, PessoaDTO.class)).orElse(null);
    }

    @Transactional
    public void createPessoa(PessoaCreateDTO dto) throws Exception {
        validaPessoa(modelMapper.map(dto, Pessoa.class));
        repository.save(modelMapper.map(dto, Pessoa.class));
    }

    @Transactional
    public void updatePessoa(PessoaUpdateDTO dto) throws Exception {
        if(repository.findById(dto.getId()).isEmpty()) {
            throw new Exception("Pessoa não encontrada");
        }

        validaPessoa(modelMapper.map(dto, Pessoa.class));
        repository.save(modelMapper.map(dto, Pessoa.class));
    }

    public void deletePessoa(Integer id) {
        repository.deleteById(id);
    }

    private void validaPessoa(Pessoa dto) throws Exception {
        if(!CpfValido.isCpf(dto.getCpf())) {
            throw new Exception("CPF inválido");
        }

        if(new Date().before(dto.getDataNascimento())) {
            throw new Exception("Data de nascimento não pode ser futura");
        }

        if(!validaEmails(dto.getContatos())) {
            throw new Exception("Existem emails invalidos");
        }
    }

    private Boolean validaEmails(List<Contato> contatos) {
        AtomicReference<Boolean> validado = new AtomicReference<>(true);

        contatos.forEach(contato -> {
            if(!EmailValido.isEmail(contato.getEmail())) {
                validado.set(false);
            }
        });

        return validado.get();
    }

}
