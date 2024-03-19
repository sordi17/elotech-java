package br.com.elotech.entrevista.service;

import br.com.elotech.entrevista.domain.Contato;
import br.com.elotech.entrevista.domain.dto.ContatoCreateDTO;
import br.com.elotech.entrevista.domain.dto.ContatoDTO;
import br.com.elotech.entrevista.domain.dto.ContatoUpdateDTO;
import br.com.elotech.entrevista.repository.ContatoRepository;
import br.com.elotech.entrevista.util.EmailValido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Contato> findContatosByPessoaId(Integer pessoaId) {
        return repository.findAllByPessoaId(pessoaId);
    }

    public ContatoDTO findContatoById(Integer id) {
        Optional<Contato> result = repository.findById(id);
        return result.map(contato -> modelMapper.map(contato, ContatoDTO.class)).orElse(null);
    }

    public void createContato(ContatoCreateDTO dto) throws Exception {
        validaContato(modelMapper.map(dto, Contato.class));
        repository.save(modelMapper.map(dto, Contato.class));
    }

    public void updateContato(ContatoUpdateDTO dto) throws Exception {
        if(repository.findById(dto.getId()).isEmpty()) {
            throw new Exception("Contato não encontrado");
        }

        validaContato(modelMapper.map(dto, Contato.class));
        repository.save(modelMapper.map(dto, Contato.class));
    }

    public void deleteContato(Integer id) {
        repository.deleteById(id);
    }

    private void validaContato(Contato contato) throws Exception {
        if(!EmailValido.isEmail(contato.getEmail())) {
            throw new Exception("Emails " + contato.getEmail() + " invalido");
        }
    }
}
