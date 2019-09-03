package br.com.pamcary.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pamcary.entity.UserEntity;

@Repository
public interface PessoaRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByCpf(String cpf);

}
