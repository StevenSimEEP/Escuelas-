package com.example.universidad;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class UniversidadApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;
	
	@Test
	void devuelveUnaUnoversidadCuandoSeGuardenLosDatos() {
		ResponseEntity<String> response = restTemplate.getForEntity("/universidades/1", String.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number id = documentContext.read("$.id");
		assertThat(id).isEqualTo(1);
		
		String nombre = documentContext.read("$.nombre");
		assertThat(nombre).isEqualTo("Universidad Autónoma de Madrid");
		
		String ubicacion = documentContext.read("$.ubicacion");
		assertThat(ubicacion).isEqualTo("Ciudad Universitaria de Cantoblanco, 28049 Madrid");
		
		String estado = documentContext.read("$.estado");
		assertThat(estado).isEqualTo("Pública");
		
		String photo = documentContext.read("$.photo");
		assertThat(photo).isEqualTo(
				"https://www.comunidad.madrid/sites/default/files/styles/imagen_enlace_opcional/public/aud/educacion/uam_4.jpg?itok=T4uXwmfB");
	
		String disponibilidad = documentContext.read("$.disponibilidad");
		assertThat(disponibilidad).isEqualTo("Abierta");
	}
	
	@Test
	void noDevuelveUniversidadCuandoLaIdEsDesconocida() {
	  ResponseEntity<String> response = restTemplate.getForEntity("/universidades/1000", String.class);

	  assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	  assertThat(response.getBody()).isBlank();
	}
	
	@Test
	@DirtiesContext
	void debeCrearNuevaUniversidad() {
	   Universidad nuevaUniversidad = new Universidad(
			   null, 
			   "Universidad Carlos III de Madrid", 
			   "CALLE MADRID, 126", 
			   "Pública", 
			   "https://www.comunidad.madrid/sites/default/files/styles/imagen_enlace_opcional/public/aud/educacion/rectorado_uc3m.jpg?itok=CqDwgmkZ", 
			   "Cerrada"
			   );
	   ResponseEntity<Void> createResponse = restTemplate.postForEntity("/universidades", nuevaUniversidad, Void.class);
	   assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	
	   URI locationOfNewCashCard = createResponse.getHeaders().getLocation();
	   ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewCashCard, String.class);
	   assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	
	   DocumentContext documentContext  = JsonPath.parse(getResponse.getBody());
	   Number id = documentContext.read("$.id");
	   String nombre = documentContext.read("$.nombre") ;
	   String ubicacion = documentContext.read("$.ubicacion");
	   String estado = documentContext.read("$.estado");
	   String photo = documentContext.read("$.photo");
	   String disponibilidad = documentContext.read("$.disponibilidad") ;
	   
	   assertThat(id).isNotNull();
	   assertThat(nombre).isEqualTo("Universidad Carlos III de Madrid");
	   assertThat(ubicacion).isEqualTo("CALLE MADRID, 126");
	   assertThat(estado).isEqualTo("Pública");
	   assertThat(photo).isEqualTo("https://www.comunidad.madrid/sites/default/files/styles/imagen_enlace_opcional/public/aud/educacion/rectorado_uc3m.jpg?itok=CqDwgmkZ");
	   assertThat(disponibilidad).isEqualTo("Cerrada");
	}
	
	 @Test
	 void devuelveUniversidadesCuandoSonRequeridas() {
	     ResponseEntity<String> response = restTemplate.getForEntity("/universidades", String.class);
	     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	     DocumentContext documentContext = JsonPath.parse(response.getBody());
	     int cashCardCount = documentContext.read("$.length()");
	     assertThat(cashCardCount).isEqualTo(3);

	     JSONArray ids = documentContext.read("$..id");
	     assertThat(ids).containsExactlyInAnyOrder(1, 2, 3);

	     JSONArray nombres = documentContext.read("$..nombre");
	     assertThat(nombres).containsExactlyInAnyOrder("Universidad Autónoma de Madrid", "Universidad Carlos III de Madrid", "Universidad Politécnica de Madrid");
	     
	     JSONArray ubicaciones = documentContext.read("$..ubicacion");
	     assertThat(ubicaciones).containsExactlyInAnyOrder("Ciudad Universitaria de Cantoblanco, 28049 Madrid", "CALLE MADRID, 126", "CALLE RAMIRO DE MAEZTU, 7");
	 
	     JSONArray estados = documentContext.read("$..estado");
	     assertThat(estados).containsExactlyInAnyOrder("Pública", "Pública", "Pública");
	     
	     JSONArray photos = documentContext.read("$..photo");
	     assertThat(photos).containsExactlyInAnyOrder(
	    		 "https://www.comunidad.madrid/sites/default/files/styles/imagen_enlace_opcional/public/aud/educacion/uam_4.jpg?itok=T4uXwmfB"
	    		 , "https://www.comunidad.madrid/sites/default/files/styles/imagen_enlace_opcional/public/aud/educacion/rectorado_uc3m.jpg?itok=CqDwgmkZ"
	     		 , "https://www.comunidad.madrid/sites/default/files/styles/imagen_enlace_opcional/public/aud/educacion/upm_2.jpg?itok=BiaVDFnT");
	     
	     JSONArray disponibilidades = documentContext.read("$..disponibilidad");
	     assertThat(disponibilidades).containsExactlyInAnyOrder("Abierta", "Cerrada", "Abierta");
	 }
	 
	 @Test
	 void devuelveUnaPaginaDeUniversidades() {
	     ResponseEntity<String> response = restTemplate.getForEntity("/universidades?page=0&size=1", String.class);
	     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	     DocumentContext documentContext = JsonPath.parse(response.getBody());
	     JSONArray page = documentContext.read("$[*]");
	     assertThat(page.size()).isEqualTo(1);
	 }
	 
	 @Test
	 void devuelveUnaPaginaOrdenadaDeUniversidades() {
	     ResponseEntity<String> response = restTemplate.getForEntity("/universidades?page=0&size=1&sort=nombre,asc", String.class);
	     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	     DocumentContext documentContext = JsonPath.parse(response.getBody());
	     JSONArray read = documentContext.read("$[*]");
	     assertThat(read.size()).isEqualTo(1);

	     String nombre = documentContext.read("$[0].nombre");
	     assertThat(nombre).isEqualTo("Universidad Autónoma de Madrid");
	 }
	 
	 @Test
	 void devuelveUnaPaginaOrdenaDeUniversidadesSinParametrosYUtilizarValoresPredeterminados() {
	     ResponseEntity<String> response = restTemplate.getForEntity("/universidades", String.class);
	     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	     DocumentContext documentContext = JsonPath.parse(response.getBody());
	     JSONArray page = documentContext.read("$[*]");
	     assertThat(page.size()).isEqualTo(4);

	     JSONArray nombres = documentContext.read("$..nombre");
	     assertThat(nombres).containsExactly("Universidad Autónoma de Madrid", "Universidad Carlos III de Madrid", "Universidad Carlos III de Madrid", "Universidad Politécnica de Madrid");
	 }
	 
	 @Test
	 @DirtiesContext
	 void actualizaUnaUniversidadExistente() {
	     Universidad universidadActualizada = new Universidad(3L, "Universidad Politécnica de Madrid", "CALLE RAMIRO DE MAEZTU, 7"
	    		 	, "Pública", "https://www.comunidad.madrid/sites/default/files/styles/imagen_enlace_opcional/public/aud/educacion/upm_2.jpg?itok=BiaVDFnT"
	    		 	, "Abierta");
	     HttpEntity<Universidad> request = new HttpEntity<>(universidadActualizada);
	     ResponseEntity<Void> response = restTemplate
	             .exchange("/universidades/3", HttpMethod.PUT, request, Void.class);
	     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	     ResponseEntity<String> getResponse = restTemplate
	             .getForEntity("/universidades/3", String.class);
	     assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	     DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
	     Number id = documentContext.read("$.id");
	     String nombre = documentContext.read("$.nombre");
	     String ubicacion = documentContext.read("$.ubicacion");
	     String estado = documentContext.read("$.estado");
	     String photo = documentContext.read("$.photo");
	     String disponibilidad = documentContext.read("$.disponibilidad");
	     assertThat(id).isEqualTo(3);
	     assertThat(nombre).isEqualTo("Universidad Politécnica de Madrid");
	     assertThat(ubicacion).isEqualTo("CALLE RAMIRO DE MAEZTU, 7");
	     assertThat(estado).isEqualTo("Pública");
	     assertThat(photo).isEqualTo("https://www.comunidad.madrid/sites/default/files/styles/imagen_enlace_opcional/public/aud/educacion/upm_2.jpg?itok=BiaVDFnT");
	     assertThat(disponibilidad).isEqualTo("Abierta");
	 }
	 
	 @Test
	 void noActualizaUnaUniversidadQueNoExiste() {
	     Universidad universidadDesconocida = new Universidad(4L, "Universidad Complutense de Madrid", "Av. Complutense, s/n, Moncloa - Aravaca, 28040 Madrid"
	    		 	, "Pública", "https://www.comunidad.madrid/sites/default/files/styles/imagen_enlace_opcional/public/aud/educacion/rectorado_ucm.jpg?itok=Ap-Zuu6t"
	    		 	, "Cerrada");
	     HttpEntity<Universidad> request = new HttpEntity<>(universidadDesconocida);
	     ResponseEntity<Void> response = restTemplate
	             .exchange("/universidades/4", HttpMethod.PUT, request, Void.class);
	     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	 }
	 
	 @Test
	 @DirtiesContext
	 void eliminaUnaUniversidadExistente() {
	     ResponseEntity<Void> response = restTemplate
	             .exchange("/universidades/1754", HttpMethod.DELETE, null, Void.class);
	     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	     
	     ResponseEntity<String> getResponse = restTemplate
	             .getForEntity("/universidades/1754", String.class);
	     assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND); 
	 }

}
