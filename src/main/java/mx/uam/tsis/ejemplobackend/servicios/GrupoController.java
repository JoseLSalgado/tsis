package mx.uam.tsis.ejemplobackend.servicios;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.negocio.GrupoService;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

@RestController
@Slf4j
public class GrupoController {
	
	@Autowired
	GrupoService grupoService;
	
	/**
	 * 
	 * @param nuevoGrupo
	 * @return id del grupo
	 */
	@ApiOperation(
		value = "Crear grupo",
		notes = "Permite crear un nuevo grupo"		
	)
	@PostMapping(path="/grupos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody @Valid Grupo nuevoGrupo){
		log.info("Recibi llamada a create con --> " + nuevoGrupo);
		
		Grupo grupo = grupoService.create(nuevoGrupo);
		
		if(grupo != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(grupo);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede crear el grupo");
		}
	}
	
	/**
	 * 
	 * @return Todos los grupos 
	 */
	@GetMapping(path = "/grupos", produces =  MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retriveAll(){
		Iterable<Grupo> result = grupoService.retrieveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	
	/**
	 * 
	 * 
	 * @param id de grupo
	 * @return objeto Grupo
	 */
	@ApiOperation( //Describe lo que hace el metodo y es visible en la cabecera de metodos de swagger
			value = "Buscar grupo",
			notes = "Puedes buscar un grupo con su id"
			)
	@GetMapping(path = "/grupos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("id") Integer id) {
		log.info("Buscando al grupo con id "+ id);
		
		Grupo grupo = grupoService.retrieve(id);
		if(grupo != null) {
			return ResponseEntity.status(HttpStatus.OK).body(grupo);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe grupo con esa clave en el sistema");
		}
	}
	
	/**
	 * 
	 * 
	 * @param Recibe id y datos del grupo por Json  
	 * 
	 * @return Regresa objeto del grupo modificado
	 */
	@ApiOperation( //Describe lo que hace el metodo y es visible en la cabecera de metodos de swagger
			value = "Modificar Grupo",
			notes = "Puedes modificar grupo, el id identifica al grupo y los parametros que deseas actualizar"
			)
	@PutMapping(path = "/grupos/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@RequestBody @Valid Grupo nuevoGrupo, @PathVariable("id") Integer id) {
		
		log.info("Metodo PUT, Id es: "+id+" Datos del grupo: "+nuevoGrupo);
		Grupo grupo = grupoService.retrieve(id);
		
		if(grupo != null) {
			grupo.setClave(nuevoGrupo.getClave());
			grupoService.update(grupo);
			return ResponseEntity.status(HttpStatus.OK).body(grupo);
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("No existe grupo con ese id en el sistema");
		}
	}
	
	
	/**
	 * 
	 * 
	 * @param id
	 * @return objeto grupo, en caso contrario null
	 */
	@ApiOperation( //Describe lo que hace el metodo y es visible en la cabecera de metodos de swagger
			value = "Elimina grupo",
			notes = "Puedes eliminar grupo con id, la cual debe estar en sistema"
			)
	@DeleteMapping(path = "/grupos/{id}")
	public ResponseEntity <?> delete(@PathVariable("id") Integer id) {
		log.info("Se invoco el metodo DELETE grupo");
		
		Grupo grupo = grupoService.retrieve(id);
		
		if(grupo != null) {
			grupoService.delete(grupo);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Grupo eliminado es: "+ grupo);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe grupo con esa matricula en el sistema");
		}
	}
	
	/**
	 * POST /grupos/{id}/alumnos?matricula=1234
	 * 
	 * @param id
	 * @param matricula
	 * @return Grupo
	 */
	@ApiOperation( //Describe lo que hace el metodo y es visible en la cabecera de metodos de swagger
			value = "Agrega un Alumno al grupo ",
			notes = "Puedes agregar un alumno por su matricula en el grupo especificado por su id"
			)
	@PostMapping(path="/grupos/{id}/alumnos",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> addStudentToGroup(@PathVariable("id") Integer id, 
												@RequestParam("matricula") Integer matricula){
		
		boolean result = grupoService.addStudenToGroup(id, matricula);
		
		if(result) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
