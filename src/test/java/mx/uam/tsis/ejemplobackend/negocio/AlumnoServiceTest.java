package mx.uam.tsis.ejemplobackend.negocio;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class AlumnoServiceTest {
	
	@Mock
	private AlumnoRepository alumnoRepositoryMock;   //Mock generado por mokito
	
	@InjectMocks
	private AlumnoService alumnoService; //Unidad a probar, y se inyecta el modulo AlumnoRepository con @InjectMocks
	
	//Modulo para identificar cuando un alumno no lo han guardado en sistema (no está dado de alta)
	@Test                       //Modulo para hacer pruebas test
	public void testSuccesfulCreate() {
		
		//Creando Alumno para hacer pruebas
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computacion");
		alumno.setMatricula(123456);
		alumno.setNombre("prueba");
		 
		//Simula lo que hay en el AlumnoRepository real pero con mockito, este caso se realiza cuando el alumno no esta guardado y devuelve null
		//Cuando llamen al mock (alumnoRespository) en su metodo "findById" y con matricula(123456), entonces regresa al objeto Nulo
		when(alumnoRepositoryMock.findById(123456)).thenReturn(Optional.ofNullable(null));
		
		//Simula que en alumnoRepository real pero con mockito, este caso el alumno no está guardado
		//Cuando llamen al mock (alumnoRepository) en su metodo "save()" pasando argumento "alumno", entonces lo guardará y devolver el objeto alumno
		when(alumnoRepositoryMock.save(alumno)).thenReturn(alumno);
		
		//LLamando Funcion create() para probar el modulo OJO: el metodo está en Mokito y no en AlumnoService
		alumno = alumnoService.create(alumno);
		
		assertNotNull(alumno); //Probar que la referencia al Alumno no sea nula
	}
	
	
	//Modulo para identificar cuando un alumno SI lo han guardado en sistema (SI está dado de alta)
	@Test                       //Modulo para hacer pruebas test
	public void testUnsuccesfulCreate() {
		
		//Creando Alumno para hacer pruebas
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computacion");
		alumno.setMatricula(123456);
		alumno.setNombre("prueba");
		 
		//Simula lo que hay en el AlumnoRepository real pero con mockito, este caso se realiza cuando el alumno no esta guardado y devuelve null
		//Cuando llamen al mock (alumnoRespository) en su metodo "findById" y con matricula(123456), entonces regresa al objeto Nulo
		when(alumnoRepositoryMock.findById(123456)).thenReturn(Optional.ofNullable(alumno));
		
		//LLamando Funcion create() para probar el modulo OJO: el metodo está en Mokito y no en AlumnoService
		alumno = alumnoService.create(alumno);
		
		assertNull(alumno); //Probar que la referencia al Alumno sea nula
	}
	
}

