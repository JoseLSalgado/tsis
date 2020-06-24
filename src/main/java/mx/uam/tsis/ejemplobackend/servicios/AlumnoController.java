package mx.uam.tsis.ejemplobackend.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * Controlador para el API rest
 * 
 * @author humbertocervantes
 *
 */
@RestController
@Slf4j
public class AlumnoController {
	
	// Mapeo para simular la "base de datos", recibe matricula(Integer) y objeto alumno 
	private Map <Integer, Alumno> alumnoRepository = new HashMap <>();
	
	@PostMapping(path = "/alumnos", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody Alumno nuevoAlumno) {
		
		// No se deben agregar dos alumnos con la misma matricula
		Alumno alumno = alumnoRepository.get(nuevoAlumno.getMatricula()); 
		
		
		if(alumno != null) {
			log.info("La matricula: "+alumno.getMatricula()+ " ya existe y no es posible crear alumno");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(alumno);
		}else {
			log.info("Recibí llamada a create con "+nuevoAlumno);
			
			alumnoRepository.put(nuevoAlumno.getMatricula(), nuevoAlumno);
			
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		
		
	}
	
	@GetMapping(path = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		log.info("Se invoco el metodo GET alummnos");
		return ResponseEntity.status(HttpStatus.OK).body(alumnoRepository.values());
		
	}

	@GetMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("matricula") Integer matricula) {
		log.info("Buscando al alumno con matricula "+matricula);
		
		Alumno alumno = alumnoRepository.get(matricula);
		
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.OK).body(alumno);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		
	}
	@PutMapping(path = "/alumno/{matricula}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@RequestBody Alumno nuevoAlumno, @PathVariable("matricula") Integer matricula) {
		log.info("Metodo PUT, Matricula es: "+matricula+" Datos alumno"+nuevoAlumno.getNombre());
		Alumno alumno = alumnoRepository.get(matricula);
		
		if(alumno != null) {
			alumno.setNombre(nuevoAlumno.getNombre());
			alumno.setCarrera(nuevoAlumno.getCarrera());
			return ResponseEntity.status(HttpStatus.OK).body(nuevoAlumno);
		}else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}
	
	@DeleteMapping(path = "/alumno/{matricula}")
	public ResponseEntity <?> delete(@PathVariable("matricula") Integer matricula) {
		log.info("Se invoco el metodo DELETE alummnos");
		Alumno alumno = alumnoRepository.get(matricula);
		
		if(alumno != null) {
			alumnoRepository.remove(matricula, alumno);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
	}
 
}
