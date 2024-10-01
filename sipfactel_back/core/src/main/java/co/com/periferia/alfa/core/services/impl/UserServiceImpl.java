package co.com.periferia.alfa.core.services.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.OpcRolesDTO;
import co.com.periferia.alfa.core.dto.UserDTO;
import co.com.periferia.alfa.core.excel.CrearExcelUsuarios;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.model.ObjetoHojaUsuarios;
import co.com.periferia.alfa.core.model.OpcRolesModel;
import co.com.periferia.alfa.core.model.admin.Roles;
import co.com.periferia.alfa.core.model.admin.UsaRol;
import co.com.periferia.alfa.core.model.admin.User;
import co.com.periferia.alfa.core.repository.OpcRolesRepository;
import co.com.periferia.alfa.core.repository.RolesRepository;
import co.com.periferia.alfa.core.repository.UserRepository;
import co.com.periferia.alfa.core.repository.UsuaRolRepository;
import co.com.periferia.alfa.core.services.IUserService;
import co.com.periferia.alfa.core.utilitarios.NombreEstado;

@Service(value = "IUserService")
public class UserServiceImpl implements IUserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepositorio;

	@Autowired
	private UsuaRolRepository usuarolRepository;

	@Autowired
	private PasswordEncoder encode;

	@Autowired
	RolesRepository rolesRepository;

	@Autowired
	OpcRolesRepository opcRolesRepository;

	@Autowired
	JwtTokenData jwtTokenData;

	@Autowired
	UtilExcecion utilException;

	@Autowired
	CrearExcelUsuarios crearExcelUsuarios;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	List<String> titulos = Arrays.asList("ID", "USUARIO", "NOMBRES", "APELLIDOS", "ROL", "CORREO", "ESTADO");

	List<IndexedColors> colores = Arrays.asList(IndexedColors.RED, IndexedColors.RED, IndexedColors.RED,
			IndexedColors.RED, IndexedColors.RED, IndexedColors.RED, IndexedColors.RED);

	@Override
	public UserDTO guardar(UserDTO dto) throws ExcepcionSegAlfa {
		LOGGER.info("Se inicia el guardado");
		User resultado = new User();
		UsaRol rol = new UsaRol();
		User resultado1 = userRepositorio.findByName(dto.getUsuaUsuario());
		if (resultado1 == null) {
			try {
				LOG.info("Ejecutando guardar ");
				resultado.setUsuaApellidos(dto.getUsuaApellidos());
				resultado.setUsuaNombres(dto.getUsuaNombres());
				if (dto.getUsuaContrasenia() != null) {
					resultado.setUsuaContrasenia(encode.encode(dto.getUsuaContrasenia()));
				}
				resultado.setUsuaCorreo(dto.getUsuaCorreo());
				resultado.setUsuaUsuario(dto.getUsuaUsuario());
				resultado.setUsuaEstado(dto.getUsuaEstado());
				resultado.setUsuaFactualiza(dto.getUsuaFactualiza());
				resultado.setUsuaUactualiza(dto.getUsuaUactualiza());
				resultado.setUsuaFcreacion(new Date());
				resultado.setUsuaUcreacion(dto.getUsuaUcreacion());
				resultado.setUsuaRol(dto.getUsuaRol());
				resultado = userRepositorio.save(resultado);
				rol.setUsuaUsua(resultado.getUsuaUsua());
				rol.setRolesRoles(resultado.getUsuaRol());
				usuarolRepository.save(rol);

			} catch (PersistenceException | IllegalArgumentException | NullPointerException ex) {
				LOG.error("ERROR al ejecutar guardar ---> {}", ex.getMessage());
				throw utilException.getById(7).crearExcepcion();
			}
		} else {
			throw utilException.getById(11).crearExcepcion();
		}
		return new UserDTO().modeloAdto(resultado);
	}

	@Override
	public UserDTO actualizar(UserDTO dto, Integer id) throws ExcepcionSegAlfa {
		LOGGER.info("Se inicia el actualizar");
		User resultado = new User();
		UsaRol rol = new UsaRol();
		try {
			LOG.info("Ejecutando actualizar - parametros | id: {}", id);
			resultado = userRepositorio.getOne(id);
			resultado.setUsuaApellidos(dto.getUsuaApellidos());
			resultado.setUsuaNombres(dto.getUsuaNombres());
			resultado.setUsuaCorreo(dto.getUsuaCorreo());
			resultado.setUsuaEstado(dto.getUsuaEstado());
			resultado.setUsuaRol(dto.getUsuaRol());
			resultado.setUsuaFactualiza(new Date());
			resultado.setUsuaUactualiza(dto.getUsuaUactualiza());
			resultado = userRepositorio.save(resultado);
			rol = usuarolRepository.getOne(id);
			rol.setRolesRoles(resultado.getUsuaRol());
			usuarolRepository.save(rol);
			dto = dto.modeloAdto(resultado);
			dto.setUsuaEstado(resultado.getUsuaEstado());

		} catch (PersistenceException | IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR al ejecutar actualizar ---> {} ", ex.getMessage());
			throw utilException.getById(8).crearExcepcion();
		}
		return dto;
	}

	@Override
	public List<UserDTO> buscarPorNombre(String nombre) throws ExcepcionSegAlfa {
		LOGGER.info("Se inicia la consulta");
		List<User> resultado = new ArrayList<>();
		List<UserDTO> resp = new ArrayList<>();
		try {
			LOG.info("Ejecutando buscarPorNombre - parametros | nombre: {}", nombre);
			resultado = userRepositorio.findLikeName(nombre);
			for (User user : resultado) {
				UserDTO resp1 = new UserDTO();
				Roles rol = rolesRepository.roleNombre(user.getUsuaRol());
				resp1 = resp1.modeloAdto(user);
				resp1.setNombreRol(rol.getRoleNombre());
				resp.add(resp1);
			}
			if (resultado.isEmpty()) {
				throw utilException.getById(5).crearExcepcion();
			}
		} catch (PersistenceException | IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR al ejecutar buscarPorNombre ---> {}", ex.getMessage());
			throw utilException.getById(5).crearExcepcion();
		}
		return resp;
	}

	@Override
	public List<OpcRolesDTO> rol() throws ExcepcionSegAlfa {
		List<OpcRolesModel> resultado = new ArrayList<>();
		try {
			LOG.info("Ejecutando rol");
			resultado = opcRolesRepository.findAll();
			return resultado.stream().map(e -> new OpcRolesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (PersistenceException | IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR al ejecutar rol ---> {}", ex.getMessage());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public UserDTO eliminar(Integer llave) throws ExcepcionSegAlfa {
		return null;
	}

	@Override
	public List<UserDTO> buscarTodos() throws ExcepcionSegAlfa {
		List<User> resultado = new ArrayList<>();
		List<UserDTO> resp = new ArrayList<>();
		UserDTO usuario = new UserDTO();
		try {
			LOG.info("Ejecutando buscarTodos");
			resultado = userRepositorio.findAll();
			for (User user : resultado) {
				Roles rol = rolesRepository.roleNombre(user.getUsuaRol());
				usuario = usuario.modeloAdto(user);
				usuario.setNombreRol(rol.getRoleNombre());
				resp.add(usuario);
			}
		} catch (PersistenceException | IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR al ejecutar buscarTodos ---> {}",ex.getMessage());
			throw utilException.getById(5).crearExcepcion();
		}

		return resp;
	}

	@Override
	public UserDTO buscarPorID(Integer id) throws ExcepcionSegAlfa {
		User resultado = new User();
		UserDTO resp = new UserDTO();
		try {
			LOG.info("Ejecutando buscarPorID- parametro | id: {}", id);
			resultado = userRepositorio.findByID(id);
			Roles rol = rolesRepository.roleNombre(resultado.getUsuaRol());
			resp = resp.modeloAdto(resultado);
			resp.setNombreRol(rol.getRoleNombre());
			return resp;
		} catch (PersistenceException | IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR al ejecutar buscarPorID ---> {}", ex.getMessage());
			throw utilException.getById(5).crearExcepcion();
		}
	}

	@Override
	public ResponseEntity<InputStreamResource> generarExcelUsuarios() throws ExcepcionSegAlfa {

		MediaType mediaType = MediaType.parseMediaType(NombreEstado.MEDIATYPE);
		try {
			LOG.info("Ejecutando generarExcelUsuarios");
			List<List<User>> datos = userRepositorio.buscarTodos();
			ByteArrayInputStream in = crearExcelUsuarios
					.generar(new ObjetoHojaUsuarios(NombreEstado.USUARIOS, titulos, colores, datos));
			HttpHeaders headers = new HttpHeaders();
			headers.add(NombreEstado.HEADERS_1, NombreEstado.HEADERS_3);
			return ResponseEntity.ok().headers(headers).contentType(mediaType).body(new InputStreamResource(in));
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOG.error("ERROR al ejecutar generarExcelUsuarios --> {}", e.getMessage());
			throw utilException.getById(6).crearExcepcion();
		}
	}

}
