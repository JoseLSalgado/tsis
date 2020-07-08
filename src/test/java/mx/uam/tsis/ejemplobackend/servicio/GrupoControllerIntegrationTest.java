package mx.uam.tsis.ejemplobackend.servicio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;
import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.datos.GrupoRepository;

/**
 * 
 * Prueba de integración para el endpoint Grupo (agrega alumno a un grupo)
 * 
 * @author Jose Luis Salgado M.
 *
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GrupoControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;  //Permite crear comunicacion con el ENDPOINT
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@BeforeEach
	public void prepare() {
		
		// Creando el alumno que será utilizado para el test
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computacion");
		alumno.setMatricula(12345);
		alumno.setNombre("Jose");
		
		// Creando el grupo que será utilizado en test
		Grupo grupo = new Grupo();
		grupo.setClave("123");
		
		//Persistiendo los objetos Alumno y Grupo en BD
		alumnoRepository.save(alumno);
		grupoRepository.save(grupo);		
	}
	
	/********* MODULO TEST QUE AGREGA ALUMNO A UN GRUPO *********/
	//Modulo test (EXITOSO) que agrega al alumno a un grupo de manera correcta
	@Test
	public void testCreate200() {
		
		// Creo el encabezado
		HttpHeaders headers = new HttpHeaders();
		headers.set("accept",MediaType.APPLICATION_JSON.toString());
		
		// Creo la petición con el encabezado
		HttpEntity <Grupo> request = new HttpEntity <> (headers);
		
		ResponseEntity <Grupo> responseEntity = restTemplate.exchange("/grupos/1/alumnos?matricula=12345", HttpMethod.POST, request, Grupo.class);

		log.info("Me regresó:"+responseEntity.getBody());
		
		// Corroboro que el endpoint me regresa el estatus esperado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	//Modulo test (NO EXITOSA) que intenta agregar al alumno a un grupo que no existe 
	@Test
	public void testCreate400() {
		
		// Creo el encabezado
		HttpHeaders headers = new HttpHeaders();
		headers.set("accept",MediaType.APPLICATION_JSON.toString());
		
		// Creo la petición con el encabezado
		HttpEntity <Grupo> request = new HttpEntity <> (headers);
		
		ResponseEntity <Grupo> responseEntity = restTemplate.exchange("/grupos/5/alumnos?matricula=12345", HttpMethod.POST, request, Grupo.class);

		log.info("Me regresó:"+responseEntity.getBody());
		
		// Corroboro que el endpoint me regresa el estatus esperado
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
}
