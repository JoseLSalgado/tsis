package mx.uam.tsis.ejemplobackend.datos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.servicios.AlumnoController;

@Component
@Slf4j
public class AlumnoRepository {
	// Mapeo para simular la "base de datos", recibe matricula(Integer) y objeto alumno 
	private Map <Integer, Alumno> alumnoRepository = new HashMap <>();
	
	/**
	 * Guarda en la BD
	 * 
	 * @param alumno
	 */
	public Alumno save(Alumno nuevoAlumno) {
		alumnoRepository.put(nuevoAlumno.getMatricula(), nuevoAlumno);
		return nuevoAlumno;
	}
	
	
	public Alumno findByMatricula(Integer matricula) {
		return alumnoRepository.get(matricula);
	}
	
	public List <Alumno> find() {
		return new ArrayList <> (alumnoRepository.values());
	}
	
	public Alumno update(Alumno alumno) {
		return alumnoRepository.replace(alumno.getMatricula(), alumno);
	}

	public Alumno delete(Alumno alumno) {
		return alumnoRepository.remove(alumno.getMatricula());

	}
}
