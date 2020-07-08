package mx.uam.tsis.ejemplobackend.negocio.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad Usuario
 * @author Jose Luis Salgado M. 
 *
 */

@NoArgsConstructor         //Constructor sin parametros
@AllArgsConstructor        //Constructor con parametros
@Builder
@Data
@Entity //Necesario para indicar a JPA(bd) que esta es una entidad y pueda generar su tabla automaticamente
public class Alumno {
	@NotNull  //Valida al pasar por controllerRest que este campo no sea nulo
	@ApiModelProperty(notes="Matricula del alumno", required=true)  //Aparecen en la descripcion de parametros como requisito para enviarlo por metodos
	@Id //Indica a JPA(bd) que esté sera su id (llave primaria)
	private Integer matricula;
	
	@NotBlank  //Valida al pasar por controllerRest que esté campo no este vacio
	@ApiModelProperty(notes="Nombre del alumno", required=true)
	private String nombre;
	
	@NotBlank
	@ApiModelProperty(notes="Carrera del alumno", required=true)
	private String carrera;
}
