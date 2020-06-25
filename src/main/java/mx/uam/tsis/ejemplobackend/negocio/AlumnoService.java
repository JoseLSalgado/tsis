package mx.uam.tsis.ejemplobackend.negocio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

@Service
public class AlumnoService {
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	/**
	 * 
	 * @param nuevoAlumno
	 * @return el alumno que se acaba de crear si la creacion es exitosa, null de lo contrario
	 */
	public Alumno create(Alumno nuevoAlumno) {
		
		// Regla de negocio: No se puede crear más de un alumno con la misma matricula
		Alumno alumno = alumnoRepository.findByMatricula(nuevoAlumno.getMatricula());
		
		if(alumno == null) {
			
			return alumnoRepository.save(nuevoAlumno);
			
		} else {
			
			return null;
			
		}
		
	}
	/**
	 * @param ninguno
	 * 
	 * @return list de objetos de todos los alumnos que existen en BD
	 */
	public List <Alumno> retrieveAll () {
		return alumnoRepository.find();
	}
	
	/**
	 * @param matricula
	 * 
	 * @return objeto alumno
	 */
	 public Alumno buscaAlumno(Integer matricula) {
		 //Validar si existe la matricula
		 return alumnoRepository.findByMatricula(matricula);
	 }
	 
	 /**
	  * @param objeto alumno
	  * 
	  * 
	  * @param objeto alumno
	  */
	 
	 public Alumno actualizaAlumno(Alumno alumno) {
		 return alumnoRepository.update(alumno);
	 }
	 
	 /**
	  * 
	  * @param matricula
	  * @return objeto alumno eliminado
	  */
	 public Alumno eliminaAlumno(Alumno alumno) {
		 return alumnoRepository.delete(alumno);
	 }
}
