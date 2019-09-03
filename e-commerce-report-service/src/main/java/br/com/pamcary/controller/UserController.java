package br.com.pamcary.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.pamcary.controller.util.HeaderUtil;
import br.com.pamcary.dto.UserDTO;
import br.com.pamcary.entity.UserEntity;
import br.com.pamcary.exception.BadRequestAlertException;
import br.com.pamcary.exception.CpfEmUsoException;
import br.com.pamcary.exception.PessoaNotFoundException;
import br.com.pamcary.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "users", description = "Tudo sobre users", produces = "application/json")
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	/**
	 * POST /users : criar uma nova pessoa.
	 * <p>
	 * criar uma nova pessoa caso o cpf não exista na base
	 *
	 * @param pessoaDTO a pessoa a ser criada
	 * @return o ResponseEntity com status 201 (Created) and e com o body da nova
	 *         pessoa , or com status 400 (Bad Request) se o cpf ja estiver em uso
	 * @throws URISyntaxException       se o Location URI syntax estiver incorreto
	 * @throws BadRequestAlertException 400 (Bad Request) se o codigo foi informado
	 * @throws CpfEmUsoException        400 (Bad Request) se o cpf já estiver em uso
	 */
	@ApiOperation(value = "Método Post para cadastro de pessoa com validação de duplicidade para o campo cpf", notes = "criar nova pessoa")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Fields are with validation errors"),
			@ApiResponse(code = 404, message = "CPF já em usi ", response = CpfEmUsoException.class),
			@ApiResponse(code = 201, message = "Recurso criado") })
	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid UserDTO pessoaDTO, BindingResult result)
			throws URISyntaxException {

		logger.debug("REST request para salvar Pessoa : {}", pessoaDTO);

		if (pessoaDTO.getId() != null) {
			throw new BadRequestAlertException("Não informar id para cadastro");
		}

		UserEntity pessoaEntity = userService.save(pessoaDTO);

		BeanUtils.copyProperties(pessoaEntity, pessoaDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(pessoaDTO.getId())
				.toUri();

		return ResponseEntity.created(location)
				.headers(HeaderUtil.createAlert("uma nova pessoa foi criada com o identificador " + pessoaDTO.getId(),
						pessoaDTO.getId().toString()))
				.body(pessoaDTO);

	}

	/**
	 * PUT /users/id : alterar uma pessoa existente.
	 *
	 * @param pessoaDTO a users a ser alterada
	 * @return the ResponseEntity com status 200 (OK) e com o body da pessoa
	 *         alterada
	 * @throws BadRequestAlertException 400 (Bad Request) se o codigo nao foi
	 *                                  informado
	 * @throws CpfEmUsoException        400 (Bad Request) se o cpf já estiver em uso
	 * @throws PessoaNotFoundException  404 (notfound) pessoa não encontrada
	 */
	@ApiOperation(value = "Método PUT para alterar informações da pessoa com validação de duplicidade para o campo cpf", notes = "alterar pessoa")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Fields are with validation errors"),
			@ApiResponse(code = 404, message = "CPF já usado", response = CpfEmUsoException.class),
			@ApiResponse(code = 404, message = "Not Found", response = PessoaNotFoundException.class),
			@ApiResponse(code = 200, message = "Recurso alterado") })
	@PutMapping
	public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO pessoaDTO) {
		logger.debug("REST request para alterar a pessoa : {}", pessoaDTO);

		if (pessoaDTO.getId() == null) {
			throw new BadRequestAlertException("Obrigatório informar o id");
		}

		userService.save(pessoaDTO);

		return ResponseEntity.ok(pessoaDTO);
	}

	/**
	 * GET /pessoa/:id : o código "id" pessoa.
	 *
	 * @param id o código da pessoa para pesquisar
	 * @return the ResponseEntity com status 200 (OK) e com o body do "id" pessoa,
	 *         ou com status 404 (Not Found)
	 * @throws PessoaNotFoundException 404 (notfound) pessoa não encontrada
	 */
	@ApiOperation(value = "Método Get para retornar uma pessoa pelo código", notes = "Retornar pessoa por id")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "código pessoa para retorno", required = true, dataType = "Long", paramType = "path", defaultValue = "1") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso", response = UserEntity.class),
			@ApiResponse(code = 404, message = "Not Found", response = PessoaNotFoundException.class),
			@ApiResponse(code = 500, message = "Falha") })
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {

		logger.debug("REST request para retornar uma Pessoa : {}", id);

		UserEntity pessoaEntity = userService.findById(id);

		UserDTO pessoaDTO = new UserDTO();
		BeanUtils.copyProperties(pessoaEntity, pessoaDTO);

		return new ResponseEntity<UserDTO>(pessoaDTO, HttpStatus.OK);
	}

	/**
	 * DELETE /users/:id : deletar com o código da pessoa.
	 *
	 * @param id o código da pessoa para deletar
	 * @return the ResponseEntity com status 200 (OK)
	 * @throws PessoaNotFoundException 404 (notfound) pessoa não encontrada
	 */
	@ApiOperation(value = "Método Delete para deletar uma pessoa pelo código")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "código pessoa para retorno", required = true, dataType = "Long", paramType = "path", defaultValue = "1") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso", response = Long.class),
			@ApiResponse(code = 404, message = "Not Found", response = PessoaNotFoundException.class),
			@ApiResponse(code = 500, message = "Falha") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		logger.debug("REST request para retornar deletar uma Pessoa : {}", id);

		userService.delete(id);
		return ResponseEntity.noContent().headers(HeaderUtil.createAlert("Uma pessoa foi deletada com o id", "" + id))
				.build();
	}

	@GetMapping
	public ResponseEntity getAll(){
		List<UserEntity> lstEntity = userService.getAll();
		System.out.println(lstEntity.size());
		List<UserDTO> lstDTO = new ArrayList<UserDTO>();
		BeanUtils.copyProperties(lstEntity, lstDTO);
		return new ResponseEntity(lstEntity, HttpStatus.OK);
	}

}
