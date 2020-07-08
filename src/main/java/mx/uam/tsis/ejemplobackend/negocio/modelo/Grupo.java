package mx.uam.tsis.ejemplobackend.negocio.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Grupo {
	@NotBlank
	private String clave;
	
	@Id
	@GeneratedValue  //Genera el id(llave) automaticamente para ser utilizado en BD
	private Integer id;
	
	@Builder.Default
	@OneToMany(targetEntity = Alumno.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)    //OneToMany indica JPA que es de cero a muchos y FetcType.LAZY es para traer solo al grupo sin sus dependencias (alumnos)
	@JoinColumn(name="id")  //	Para relacion uno a uno y no crea tabla intermedia
	private List <Alumno> alumnos = new ArrayList<>();
	
	public boolean addAlumno(Alumno alumno) {
		return alumnos.add(alumno);
	}

}
