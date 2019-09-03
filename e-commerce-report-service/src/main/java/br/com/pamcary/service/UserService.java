package br.com.pamcary.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pamcary.dto.UserDTO;
import br.com.pamcary.entity.UserEntity;
import br.com.pamcary.exception.CpfEmUsoException;
import br.com.pamcary.exception.PessoaNotFoundException;
import br.com.pamcary.repository.PessoaRepository;

@Service
public class UserService {

	@Autowired
	private PessoaRepository pessoaRepository;

	private final Logger log = LoggerFactory.getLogger(UserService.class);

	public UserEntity save(UserDTO pessoaDTO) throws CpfEmUsoException {

		Optional<UserEntity> isPessoaExiste = pessoaRepository.findByCpf(pessoaDTO.getCpf());
		if (isPessoaExiste.isPresent() && (!isPessoaExiste.get().getId().equals(pessoaDTO.getId()))) {
			throw new CpfEmUsoException("Pessoa cpf: '" + pessoaDTO.getCpf() + "' já existe");
		}

		if (pessoaDTO.getId() != null) {
			UserEntity pessoaSalva = findById(pessoaDTO.getId());
			BeanUtils.copyProperties(pessoaDTO, pessoaSalva, "codigo");
			pessoaRepository.save(pessoaSalva);
			log.debug("Informação alterada para pessoa: {}", pessoaSalva);
			return pessoaSalva;
		} else {
			UserEntity pessoaEntity = new UserEntity();
			BeanUtils.copyProperties(pessoaDTO, pessoaEntity, "codigo");
			pessoaRepository.save(pessoaEntity);
			log.debug("Informação criada para pessoa: {}", pessoaEntity);
			return pessoaEntity;
		}

	}

	public UserEntity findById(Long id) {

		Optional<UserEntity> pessoa = pessoaRepository.findById(id);

		if (pessoa == null || !pessoa.isPresent()) {
			throw new PessoaNotFoundException("Pessoa código: '" + id + "' Não encontrado");
		}

		log.debug("Informação encontrada para pessoa: {}", id);

		return pessoa.get();
	}

	public void delete(Long id) {
		UserEntity pessoaEntity = findById(id);
		pessoaRepository.delete(pessoaEntity);
		log.debug("Informação deletada para pessoa: {}", id);
	}

	public List<UserEntity> getAll() {
		List<UserEntity> lstEntity = pessoaRepository.findAll();
		return lstEntity;
	}

}
