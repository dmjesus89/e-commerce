package br.com.pamcary.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.pamcary.entity.UserEntity;

@RunWith(SpringJUnit4ClassRunner.class)
public class PessoaRepositoryTest {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Test
	public void testSave() {
		UserEntity pessoaEntity = new UserEntity();
		pessoaEntity.setCpf("04064842529");
		pessoaEntity.setDataNascimento(LocalDateTime.now());
		pessoaEntity.setNome("Diego");

		// save product, verify has ID value after save
		assertNull(pessoaEntity.getId()); // null before save
		pessoaRepository.save(pessoaEntity);
		assertNotNull(pessoaEntity.getId()); // not null after save

		// fetch from DB
		Optional<UserEntity> pessoa1 = pessoaRepository.findById(pessoaEntity.getId());
		UserEntity fetchedProduct = pessoa1.get();

		// should not be null
		assertNotNull(fetchedProduct);

		// should equal
		assertEquals(pessoaEntity.getId(), fetchedProduct.getId());
		assertEquals(pessoaEntity.getNome(), fetchedProduct.getNome());

		// update description and save
		fetchedProduct.setNome("New Description");
		pessoaRepository.save(fetchedProduct);

		// get from DB, should be updated
		Optional<UserEntity> pessoa2 = pessoaRepository.findById(fetchedProduct.getId());
		UserEntity fetchedUpdatedProduct = pessoa2.get();

		assertEquals(fetchedProduct.getNome(), fetchedUpdatedProduct.getNome());

		// verify count of products in DB
		long productCount = pessoaRepository.count();
		assertEquals(productCount, 1);

		// get all products, list should only have one
		Iterable<UserEntity> pessoas = pessoaRepository.findAll();

		int count = 0;

		for (UserEntity p : pessoas) {
			count++;
		}

		assertEquals(count, 1);
	}

}
