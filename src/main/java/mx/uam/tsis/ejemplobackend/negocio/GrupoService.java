package mx.uam.tsis.ejemplobackend.negocio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.datos.GrupoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

@Service
@Slf4j
public class GrupoService {
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private AlumnoService alumnoService;
	
	
	public Grupo create(Grupo nuevo) {
		log.info("En GrupoService metodo CREATE y recibi Grupo"+ nuevo);
	
		
		// Regla de negocio: No se puede crear más de un alumno con la misma matricula
		Optional <Grupo> grupoOpt = grupoRepository.findById(nuevo.getId());  
		
		if(!grupoOpt.isPresent()) {
			log.info("En GrupoService metodo CREATE, ya guardé el grupo y devuelvo "+ nuevo);
			return grupoRepository.save(nuevo);
			
		} else {
			return null;
		}
	}
	
	public Iterable<Grupo> retrieveAll(){
		return grupoRepository.findAll();
		
	}
	
	 public Grupo retrieve(Integer id) {
		 //Validar si existe el id
		 Optional <Grupo> grupoOpt = grupoRepository.findById(id);  
			
		if(grupoOpt.isPresent()) {
			return grupoOpt.get(); 
		} else {
			return null;
		}
	 }
	 
	 public Grupo update(Grupo grupo) {
		 log.info("En GrupoService en metodo UPDATE, recibi grupo "+ grupo);
		 return grupoRepository.save(grupo);
	 }
	 
	 /**
	  * 
	  * @param matricula
	  * @return objeto alumno eliminado
	  */
	 public Grupo delete(Grupo grupo) {
		Optional <Grupo> grupoOpt = grupoRepository.findById(grupo.getId());  
			
		if(grupoOpt.isPresent()) {
			grupoRepository.delete(grupo);    //Eliminando grupo
			return grupoOpt.get();             //Enviando objeto copia de grupo eliminado
		} else {
			return null;
		}
	 }
	 
	 /**
	  * Metodo que permite agregar un alumno al grupo
	  * 
	  * @param groupId
	  * @param matricula
	  * @return True si se agrega correctamente
	  */
	 public boolean addStudenToGroup(Integer groupId, Integer matricula) {
		 
		 log.info("En metodo Agregando Alumno en Grupo, matricula "+matricula+" Grupo Id "+groupId);
		 
		 Alumno alumno = alumnoService.buscaAlumno(matricula);  //1.- Recuperando alumno por matricula
		 Optional <Grupo> grupoOpt = grupoRepository.findById(groupId);  //2.- Recuperando grupo por id
		 
		 if(!grupoOpt.isPresent() || alumno == null) {         //3.- Si alumno o grupo no existen
			 log.info("No se encontro el grupo o el alumno");
			 return false;
		 }
		 
		 //4.- Agregando Alumno al grupo
		 Grupo grupo = grupoOpt.get();    //Obteniendo grupo buscado
		 grupo.addAlumno(alumno);         //Adjuntando alumno en entidad grupo (Lista del grupo de alumnos)
		 
		 //5.-Persistir el cambio
		 grupoRepository.save(grupo);
		 return true;
	 }
}
