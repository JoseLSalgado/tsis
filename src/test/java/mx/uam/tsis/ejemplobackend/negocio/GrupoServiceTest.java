package mx.uam.tsis.ejemplobackend.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import mx.uam.tsis.ejemplobackend.datos.GrupoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

@ExtendWith(MockitoExtension.class)
public class GrupoServiceTest {
	@Mock
	private GrupoRepository grupoRepositoryMock;
	
	@Mock
	private AlumnoService alumnoServiceMock;
	
	@InjectMocks
	private GrupoService grupoService;
	
	/************* MODULOS TEST PARA AGREGAR ALUMNO A UN GRUPO **************/
	//Modulo que genera test para agregar un alumno en un grupo
	@Test
	public void testSuccesfulAddStudenToGroup () {
		
		//Creando Grupo para hacer las pruebas
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		//Creando Alumno para hacer pruebas
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computacion");
		alumno.setMatricula(123456);
		alumno.setNombre("prueba");
		
		//Stubbing para el alumnoService
		when(alumnoServiceMock.buscaAlumno(123456)).thenReturn(alumno);
		
		//Stubbing para grupo repository
		when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(Optional.of(grupo));
		
		
		boolean result = grupoService.addStudenToGroup(1, 123456);
		
		assertEquals(true, result); //Corroborando si devuelve al agregar alumno al grupo
		
		assertEquals(grupo.getAlumnos().get(0),alumno);   //Corroborando el grupo y buscando en la posicion 0 de su array list si tiene un alumno
	}
	
	//Modulo que verifica agregar alumno a grupo pero cuando el grupo no existe
	@Test
	public void testUnsuccesfulAddStudenToGroup () {
		
		//Creando Alumno para hacer pruebas
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computacion");
		alumno.setMatricula(123456);
		alumno.setNombre("prueba");
		
		//Stubbing para el alumnoService
		when(alumnoServiceMock.buscaAlumno(123456)).thenReturn(alumno);
		
		//Stubbing para grupo repository, se agrega anyInt() para agregar cualquier id del grupo y tambien Optional.ofNullable(null) para simular que el grupo es nulo 
		when(grupoRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(null));
		
		
		boolean result = grupoService.addStudenToGroup(1, 123456);
		
		assertEquals(false, result); //Corroborando si devuelve al agregar alumno al grupo
		
		//assertEquals(grupo.getAlumnos().get(0),alumno);   //Corroborando el grupo y buscando en la posicion 0 de su array list si tiene un alumno
	}
		
	/************* MODULOS TEST PARA CREAR UN GRUPO **************/
	//Modulo que verifica CREAR un grupo exitosamente
	@Test
	public void testSuccesfulCreate() {
		
		//Creando Grupo para hacer las pruebas
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		//Validando si grupo existe en BD
		when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(Optional.ofNullable(null));
		
		//Simulando respuesta de Repositorio de mockito
		when(grupoRepositoryMock.save(grupo)).thenReturn(grupo);
		
		//Creando Grupo y guardando en variable grupo
		grupo = grupoService.create(grupo);
		
		//Validando grupo que no sea null
		assertNotNull(grupo);
	}
	
	//Modulo que verifica CREAR un grupo sin exito
	@Test
	public void testUnsuccesfulCreate() {
		
		//Creando Grupo para hacer las pruebas
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		//Simulando respuesta de Repositorio de mockito
		when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(Optional.ofNullable(grupo));
		
		//Creando Grupo y guardando en variable grupo
		grupo = grupoService.create(grupo);
		
		//Validando grupo que no sea null
		assertNull(grupo);
	}
	
	/************* MODULOS TEST PARA ACTUALIZAR UN GRUPO **************/
	//Modulo que verifica ACTUALIZACION de un grupo EXITOSAMENTE
	@Test
	public void testSuccesfulUpdate() {
		
		//Creando Grupo para hacer las pruebas
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		//Simulando respuesta de Repositorio de mockito
		when(grupoRepositoryMock.save(grupo)).thenReturn(grupo);
		
		//Pasando Grupo para actualizar y guardando en variable grupo
		grupo = grupoService.update(grupo);
		
		//Validando grupo que no sea null
		assertNotNull(grupo);
	}
	
	//Modulo que verifica ACTUALIZACION de un grupo NO EXITOSO
	@Test
	public void testUnsuccesfulUpdate() {
		
		//Creando Grupo para hacer las pruebas
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		//Simulando respuesta de Repositorio de mockito
		when(grupoRepositoryMock.save(grupo)).thenReturn(null);
				
		//Pasando Grupo para actualizar y guardando en variable grupo
		grupo = grupoService.update(grupo);
		
		//Validando grupo que sea null
		assertNull(grupo);
	}
	
	/************* MODULOS TEST PARA RECUPERAR UN GRUPO **************/
	//Modulo que verifica RECUPERACION de un grupo EXITOSAMENTE
	@Test
	public void testSuccesfulRetrieve() {
		
		//Creando Grupo para hacer las pruebas
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		//Simulando respuesta de Repositorio de mockito
		when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(Optional.ofNullable(grupo));
		
		//Pasando Grupo para recuperar y guardando en variable grupo
		grupo = grupoService.retrieve(grupo.getId());
		
		//Validando grupo que no sea null
		assertNotNull(grupo);
	}
	
	//Modulo que verifica RECUPERACION de un grupo NO EXITOSA
	@Test
	public void testUnsuccesfulRetrieve() {
		
		//Creando Grupo para hacer las pruebas
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		//Simulando respuesta de Repositorio de mockito
		when(grupoRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(null));
		
		//Pasando Grupo para recuperar y guardando en variable grupo
		grupo = grupoService.retrieve(grupo.getId());
		
		//Validando grupo que sea null
		assertNull(grupo);
	}
	
	/************* MODULOS TEST PARA ELIMINAR UN GRUPO **************/
	//Modulo que verifica ELIMINAR un grupo EXITOSAMENTE
	@Test
	public void testSuccesfulDelete() {
		
		//Creando Grupo para hacer las pruebas
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		//Simulando respuesta de Repositorio de mockito
		when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(Optional.ofNullable(grupo));
		
		//Pasando Grupo para recuperar y guardando en variable grupo
		grupo = grupoService.delete(grupo);
		
		//Validando grupo que no sea null
		assertNotNull(grupo);
	}
	
	//Modulo que verifica ELIMINAR un grupo NO EXITOSAMENTE
	@Test
	public void testUnsuccesfulDelete() {
		
		//Creando Grupo para hacer las pruebas
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		//Simulando respuesta de Repositorio de mockito
		when(grupoRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(null));
		
		//Pasando Grupo para recuperar y guardando en variable grupo
		grupo = grupoService.delete(grupo);
		
		//Validando grupo que sea null
		assertNull(grupo);
	}
}
