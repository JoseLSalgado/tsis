package mx.uam.tsis.ejemplobackend.datos;

import org.springframework.data.repository.CrudRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

public interface GrupoRepository extends CrudRepository <Grupo, Integer>  {
	

}
