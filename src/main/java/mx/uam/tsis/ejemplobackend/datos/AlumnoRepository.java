package mx.uam.tsis.ejemplobackend.datos;

import org.springframework.data.repository.CrudRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

public interface AlumnoRepository extends CrudRepository<Alumno, Integer>{
	
	
}
//@Component
//@Slf4j
/*public class AlumnoRepository {
	// Mapeo para simular la "base de datos", recibe matricula(Integer) y objeto alumno 
	private Map <Integer, Alumno> alumnoRepository = new HashMap <>();
	
	
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
*/