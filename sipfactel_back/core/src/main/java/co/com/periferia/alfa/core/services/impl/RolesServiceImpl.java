package co.com.periferia.alfa.core.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.RoleDTO;
import co.com.periferia.alfa.core.dto.RonuNuevoDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.model.OpcMenuModel;
import co.com.periferia.alfa.core.model.admin.Roles;
import co.com.periferia.alfa.core.model.admin.Ronu;
import co.com.periferia.alfa.core.repository.MenuRepository;
import co.com.periferia.alfa.core.repository.OpcMenuRepository;
import co.com.periferia.alfa.core.repository.RolesRepository;
import co.com.periferia.alfa.core.repository.RonuRepository;
import co.com.periferia.alfa.core.services.IRoleService;

@Service(value = "IRoleService")
public class RolesServiceImpl implements IRoleService {

	private static final Logger LOG = LoggerFactory.getLogger(RolesServiceImpl.class);

	@Autowired
	RolesRepository rolesrepository;

	@Autowired
	public JwtTokenData jtd;

	@Autowired
	OpcMenuRepository opcMenuRepository;

	@Autowired
	RonuRepository ronuRepository;

	@Autowired
	MenuRepository menuRepository;

	@Autowired
	UtilExcecion utilException;

	@Override
	public RoleDTO guardar(RoleDTO dto) throws ExcepcionSegAlfa {
		LOG.info("Se inicia el guardado- parametros| dto");
		Roles resultado = new Roles();
		Roles resultado1 = rolesrepository.findrole(dto.getRoleNombre());
		if (resultado1 == null) {
			try {
				resultado.setRoleNombre(dto.getRoleNombre());
				resultado.setRoleDescripcion(dto.getRoleDescripcion());
				resultado.setRoleEsta(dto.getRoleEsta());
				resultado.setRoleUcreacion(dto.getRoleUcreacion());
				resultado.setRoleFcreacion(new Date());
				resultado.setCategoria(dto.getCategoria());
				resultado = rolesrepository.save(resultado);
				for (RonuNuevoDTO menu : dto.getModulos()) {
					Ronu ronu = new Ronu();
					ronu.setMenuMenu(menu.getIdMenu());
					ronu.setCrear(menu.getCrear());
					ronu.setConsultar(menu.getConsultar());
					ronu.setModificar(menu.getModificar());
					ronu.setRolesRoles(resultado.getRoleRole());
					ronuRepository.save(ronu);
					if (menu.getHijos() != null) {
						for (RonuNuevoDTO menuh : menu.getHijos()) {
							Ronu ronu1 = new Ronu();
							ronu1.setMenuMenu(menuh.getIdMenu());
							ronu1.setCrear(menuh.getCrear());
							ronu1.setConsultar(menuh.getConsultar());
							ronu1.setModificar(menuh.getModificar());
							ronu1.setRolesRoles(resultado.getRoleRole());
							ronu1.setPadre(menu.getIdMenu());
							ronuRepository.save(ronu1);
						}
					}

				}
			} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
				LOG.error("ERROR al ejecutar guardar, error: {} ", e.getMessage());
				throw utilException.getById(7).crearExcepcion();
			}
		} else {
			throw utilException.getById(10).crearExcepcion();
		}

		return new RoleDTO().modeloAdto(resultado);
	}

	public List<RoleDTO> buscarPorNombre(String nombre) throws ExcepcionSegAlfa {
		LOG.info("se realiza busqueda por nombre en la tabla");
		List<RoleDTO> roleresponse = new ArrayList<>();
		List<RonuNuevoDTO> menuresponse = new ArrayList<>();
		try {
			LOG.info("Ejecutando buscarPorNombre- parametros| nombre: {} ", nombre);
			List<Roles> roles = rolesrepository.findByRoleNombre(nombre);
			for (Roles rol : roles) {
				List<Ronu> ronu = ronuRepository.findMenuByRol(rol.getRoleRole());
				for (Ronu menu : ronu) {
					List<Ronu> ronuh = ronuRepository.findMenuByhijo(menu.getMenuMenu(), menu.getRolesRoles());
					if (!ronuh.isEmpty()) {
						for (Ronu ronuh1 : ronuh) {
							OpcMenuModel menuh = opcMenuRepository.findNombre(ronuh1.getMenuMenu());
							menuresponse.add(new RonuNuevoDTO().dto(menuh.getMenuMenu(), menuh.getMenuTitulo(),
									ronuh1.getCrear(), ronuh1.getConsultar(), ronuh1.getModificar(),
									new ArrayList<>()));
						}
					} else {
						OpcMenuModel menup = opcMenuRepository.findNombre(menu.getMenuMenu());
						menuresponse.add(new RonuNuevoDTO().dto(menup.getMenuMenu(), menup.getMenuTitulo(),
								menu.getCrear(), menu.getConsultar(), menu.getModificar(), new ArrayList<>()));
					}
				}
				roleresponse.add(new RoleDTO().modeloAdtomenu(rol, menuresponse));
			}
			return roleresponse;
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOG.error("ERROR al ejecutar buscarPorNombre, error: {} ", e.getMessage());
			throw utilException.getById(5).crearExcepcion();
		}
	}

	@Override
	public RoleDTO actualizar(RoleDTO dto, Integer id) throws ExcepcionSegAlfa {
		LOG.info("Se inicia la actualizaci�n de los datos de la tabla");
		try {
			LOG.info("Ejecutando actualizar- parametros| dto |id: {} ", id);
			Roles resultado = rolesrepository.getOne(id);
			resultado.setRoleNombre(dto.getRoleNombre());
			resultado.setRoleDescripcion(dto.getRoleDescripcion());
			resultado.setRoleEsta(dto.getRoleEsta());
			resultado.setRoleUactualizacion(dto.getRoleUactualizacion());
			resultado.setRoleFactualizacion(new Date());
			resultado.setCategoria(dto.getCategoria());
			resultado = rolesrepository.save(resultado);
			ronuRepository.deletemenu(resultado.getRoleRole());
			for (RonuNuevoDTO menu : dto.getModulos()) {
				Ronu ronu = new Ronu();
				ronu.setMenuMenu(menu.getIdMenu());
				ronu.setRolesRoles(id);
				ronu.setCrear(menu.getCrear());
				ronu.setConsultar(menu.getConsultar());
				ronu.setModificar(menu.getModificar());
				ronuRepository.save(ronu);
				if (menu.getHijos() != null) {
					for (RonuNuevoDTO menuh : menu.getHijos()) {
						Ronu ronu1 = new Ronu();
						ronu1.setMenuMenu(menuh.getIdMenu());
						ronu1.setRolesRoles(id);
						ronu1.setCrear(menuh.getCrear());
						ronu1.setConsultar(menuh.getConsultar());
						ronu1.setModificar(menuh.getModificar());
						ronu1.setPadre(menu.getIdMenu());
						ronuRepository.save(ronu1);
					}
				}
			}
			dto = dto.modeloAdto(resultado);
			dto.setRoleEsta(resultado.getRoleEsta());
			return dto;
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOG.error("ERROR al ejecutar actualizar, error: {} ", e.getMessage());
			throw utilException.getById(8).crearExcepcion();
		}
	}

	@Override
	public List<RonuNuevoDTO> buscarTodosMenu() throws ExcepcionSegAlfa {
		List<OpcMenuModel> resultado = new ArrayList<>();
		List<RonuNuevoDTO> result = new ArrayList<>();
		List<RonuNuevoDTO> resp = new ArrayList<>();
		try {
			LOG.info("Ejecutando buscarTodosMenu");
			resultado = opcMenuRepository.findMenu();
			for (OpcMenuModel opcMenuModel : resultado) {
				result = new ArrayList<>();
				List<OpcMenuModel> hijo = opcMenuRepository.findhijo(opcMenuModel.getMenuMenu());
				for (OpcMenuModel ronuNuevoDTO : hijo) {
					result.add(new RonuNuevoDTO().modeloAdto(ronuNuevoDTO));
				}
				resp.add(new RonuNuevoDTO().modeloAdtomenu(opcMenuModel, result));
			}

		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOG.error("ERROR al ejecutar buscarTodosMenu, error: {} ", e.getMessage());
			throw utilException.getById(6).crearExcepcion();
		}
		return resp;
	}

	@Override
	public RoleDTO eliminar(Integer llave) throws ExcepcionSegAlfa {
		return null;
	}

	@Override
	public List<RoleDTO> buscarTodos() throws ExcepcionSegAlfa {
		List<RoleDTO> resultado = new ArrayList<>();
		List<RonuNuevoDTO> menuresponse = new ArrayList<>();
		try {
			LOG.info("Ejecutando buscarTodos");
			List<Roles> rol = rolesrepository.findAll();
			for (Roles roles : rol) {
				menuresponse = new ArrayList<>();
				List<Ronu> ronu = ronuRepository.findMenuByRol(roles.getRoleRole());
				for (Ronu menu : ronu) {
					List<Ronu> ronuh = ronuRepository.findMenuByhijo(menu.getMenuMenu(), menu.getRolesRoles());
					if (!ronuh.isEmpty()) {
						for (Ronu ronuh1 : ronuh) {
							OpcMenuModel menuh = opcMenuRepository.findNombre(ronuh1.getMenuMenu());
							menuresponse.add(new RonuNuevoDTO().dto(menuh.getMenuMenu(), menuh.getMenuTitulo(),
									ronuh1.getCrear(), ronuh1.getConsultar(), ronuh1.getModificar(),
									new ArrayList<>()));
						}
					} else {
						OpcMenuModel menup = opcMenuRepository.findNombre(menu.getMenuMenu());
						menuresponse.add(new RonuNuevoDTO().dto(menup.getMenuMenu(), menup.getMenuTitulo(),
								menu.getCrear(), menu.getConsultar(), menu.getModificar(), new ArrayList<>()));
					}
				}
				resultado.add(new RoleDTO().modeloAdtomenu(roles, menuresponse));
			}
			return resultado;
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOG.error("ERROR al ejecutar buscarTodos, error: {} ", e.getMessage());
			throw utilException.getById(5).crearExcepcion();
		}

	}

	@Override
	public RoleDTO buscarPorID(Integer id) throws ExcepcionSegAlfa {
		RoleDTO resultado = new RoleDTO();
		List<RonuNuevoDTO> menuresponse = new ArrayList<>();
		try {
			LOG.info("Ejecutando buscarPorID- parametros | id: {} ", id);
			Roles rol = rolesrepository.getOne(id);
			List<Ronu> ronu = ronuRepository.findMenuByRol(rol.getRoleRole());
			for (Ronu menu : ronu) {
				List<Ronu> ronuh = ronuRepository.findMenuByhijo(menu.getMenuMenu(), menu.getRolesRoles());
				if (!ronuh.isEmpty()) {
					for (Ronu ronuh1 : ronuh) {
						OpcMenuModel menuh = opcMenuRepository.findNombre(ronuh1.getMenuMenu());
						menuresponse.add(new RonuNuevoDTO().dto(menuh.getMenuMenu(), menuh.getMenuTitulo(),
								ronuh1.getCrear(), ronuh1.getModificar(), ronuh1.getConsultar(), new ArrayList<>()));
					}
				} else {
					OpcMenuModel menup = opcMenuRepository.findNombre(menu.getMenuMenu());
					menuresponse.add(new RonuNuevoDTO().dto(menup.getMenuMenu(), menup.getMenuTitulo(), menu.getCrear(),
							menu.getModificar(), menu.getConsultar(), new ArrayList<>()));
				}
			}

			resultado = new RoleDTO().modeloAdtomenu(rol, menuresponse);
			return resultado;
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOG.error("ERROR al ejecutar buscarPorID, error: {} ", e.getMessage());
			throw utilException.getById(5).crearExcepcion();
		}
	}
}
