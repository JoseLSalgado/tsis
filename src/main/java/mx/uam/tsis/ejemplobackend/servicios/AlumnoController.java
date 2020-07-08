package mx.uam.tsis.ejemplobackend.servicios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.negocio.AlumnoService;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * Controlador para el API rest
 * 
 * @author humbertocervantes
 *
 */
@RestController
//@RequestMapping("/v1") //Es el versionamiento de la API
@Slf4j                 //Loggin, sirve para poner los mensajes de log.info() y poner las trazas
public class AlumnoController {
	
	@Autowired
	private AlumnoService alumnoService;

	/**
	 * 
	 * @param nuevoAlumno
	 * @return objeto nuevoAlumno, en caso contrario cadena String
	 */
	@ApiOperation( //Describe lo que hace el metodo y es visible en la cabecera de metodos de swagger
			value = "Crear Alumno.",
			notes = "Puedes crear alumno, la matricula debe ser unica."
			)
	@PostMapping(path = "/alumnos", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody @Valid Alumno nuevoAlumno) {
		
		log.info("Recibí llamada a create con "+nuevoAlumno);
		
		Alumno alumno = alumnoService.create(nuevoAlumno);
		//Validando para que no se agregen matriculas repetidas
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAlumno);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La matricula ya existe en sistema");
		}
	}
	
	/**
	 * 
	 * @param ninguno
	 * @return Alumno
	 */
	@ApiOperation( //Describe lo que hace el metodo y es visible en la cabecera de metodos de swagger
			value = "Devuelve todos los alumno",
			notes = "Muestra a todos los alumnos del sistema"
			)
	@GetMapping(path = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		
		Iterable <Alumno> result = alumnoService.retrieveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result); 
		
	}
	
	/**
	 * 
	 * 
	 * @param matricula
	 * @return objeto Alumno, en caso contrario cadena String
	 */
	@ApiOperation( //Describe lo que hace el metodo y es visible en la cabecera de metodos de swagger
			value = "Buscar alumno",
			notes = "Puedes buscar un alumno con la matricula"
			)
	@GetMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("matricula") Integer matricula) {
		log.info("Buscando al alumno con matricula "+matricula);
		
		Alumno alumno = alumnoService.buscaAlumno(matricula);
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.OK).body(alumno);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe alumno con esa matricula en el sistema");
		}
	}
	

	/**
	 * 
	 * 
	 * @param Recibe matricula y datos de alumno por Json  
	 * 
	 * @return Regresa objeto del alumno modificado
	 */
	@ApiOperation( //Describe lo que hace el metodo y es visible en la cabecera de metodos de swagger
			value = "Modificar Alumno",
			notes = "Puedes modificar alumno, la matricula se identifica al alumno y los parametros que deseas actualizar"
			)
	@PutMapping(path = "/alumno/{matricula}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@RequestBody @Valid Alumno nuevoAlumno, @PathVariable("matricula") Integer matricula) {
		
		log.info("Metodo PUT, Matricula es: "+matricula+" Datos alumno"+nuevoAlumno);
		Alumno alumno = alumnoService.buscaAlumno(matricula);
		
		if(alumno != null) {
			alumno.setNombre(nuevoAlumno.getNombre());
			alumno.setCarrera(nuevoAlumno.getCarrera());
			alumnoService.actualizaAlumno(alumno);
			return ResponseEntity.status(HttpStatus.OK).body(alumno);
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("No existe alumno con esa matricula en el sistema");
		}
	}
	
	
	/**
	 * 
	 * 
	 * @param matricula
	 * @return objeto alumno, en caso contrario null
	 */
	@ApiOperation( //Describe lo que hace el metodo y es visible en la cabecera de metodos de swagger
			value = "Elimina Alumno.",
			notes = "Puedes eliminar alumno con la matricula, la cual debe estar en sistema"
			)
	@DeleteMapping(path = "/alumno/{matricula}")
	public ResponseEntity <?> delete(@PathVariable("matricula") Integer matricula) {
		log.info("Se invoco el metodo DELETE alummnos");
		
		Alumno alumno = alumnoService.buscaAlumno(matricula);
		
		if(alumno != null) {
			alumnoService.eliminaAlumno(alumno);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Alumno eliminado es: "+alumno);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe alumno con esa matricula en el sistema");
		}
	}
	
}
