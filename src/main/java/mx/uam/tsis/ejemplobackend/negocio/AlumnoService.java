package mx.uam.tsis.ejemplobackend.negocio;

import java.util.List;
import java.util.Optional;

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
		Optional <Alumno> alumnoOpt = alumnoRepository.findById(nuevoAlumno.getMatricula());  
		
		if(!alumnoOpt.isPresent()) {
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
	public Iterable <Alumno> retrieveAll () {
		return alumnoRepository.findAll();
	}
	
	/**
	 * @param matricula
	 * 
	 * @return objeto alumno
	 */
	 public Alumno buscaAlumno(Integer matricula) {
		 //Validar si existe la matricula
		 Optional <Alumno> alumnoOpt = alumnoRepository.findById(matricula);  
			
			if(alumnoOpt.isPresent()) {
				return alumnoOpt.get(); 
			} else {
				return null;
			}
	 }
	 
	 
	 /**
	  * @param objeto alumno
	  * 
	  * 
	  * @param objeto alumno
	  */
	 public Alumno actualizaAlumno(Alumno alumno) {
		 return alumnoRepository.save(alumno);
	 }
	 
	 /**
	  * 
	  * @param matricula
	  * @return objeto alumno eliminado
	  */
	 public Alumno eliminaAlumno(Alumno alumno) {
		Optional <Alumno> alumnoOpt = alumnoRepository.findById(alumno.getMatricula());  
			
		if(alumnoOpt.isPresent()) {
			alumnoRepository.delete(alumno);    //Eliminando alumno
			return alumnoOpt.get();             //Enviando objeto copia de alumno eliminado
		} else {
			return null;
		}
	 }
	 
}
