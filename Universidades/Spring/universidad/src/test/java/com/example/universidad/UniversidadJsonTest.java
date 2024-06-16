package com.example.universidad;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.util.Arrays;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.ObjectContent;

@JsonTest
public class UniversidadJsonTest {

	@Autowired
	private JacksonTester<Universidad> json;

	@Autowired
	private JacksonTester<Universidad[]> jsonList;

	private Universidad[] universidades;

	@BeforeEach
	void setUp() {
		universidades = Arrays.array(
				new Universidad(1L, "Universidad Autónoma de Madrid",
						"Ciudad Universitaria de Cantoblanco, 28049 Madrid", "Pública",
						"https://www.comunidad.madrid/sites/default/files/styles/imagen_enlace_opcional/public/aud/educacion/uam_4.jpg?itok=T4uXwmfB",
						"Abierta"),
				new Universidad(2L, "Universidad Carlos III de Madrid", "CALLE MADRID, 126", "Pública",
						"https://www.comunidad.madrid/sites/default/files/styles/imagen_enlace_opcional/public/aud/educacion/rectorado_uc3m.jpg?itok=CqDwgmkZ",
						"Cerrada"),
				new Universidad(3L, "Universidad Politécnica de Madrid",  "CALLE RAMIRO DE MAEZTU, 7", "Pública",
						"https://www.comunidad.madrid/sites/default/files/styles/imagen_enlace_opcional/public/aud/educacion/upm_2.jpg?itok=BiaVDFnT",
						"Abierta"));
	}

	@Test
	void universidadSerializationTest() throws IOException {
		Universidad universidad = universidades[0];
		assertThat(json.write(universidad)).isStrictlyEqualToJson("single.json");
		assertThat(json.write(universidad)).hasJsonPathNumberValue("@.id");
		assertThat(json.write(universidad)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
		assertThat(json.write(universidad)).hasJsonPathStringValue("@.nombre");
		assertThat(json.write(universidad)).extractingJsonPathStringValue("@.nombre")
				.isEqualTo("Universidad Autónoma de Madrid");
		assertThat(json.write(universidad)).hasJsonPathStringValue("@.ubicacion");
		assertThat(json.write(universidad)).extractingJsonPathStringValue("@.ubicacion")
				.isEqualTo("Ciudad Universitaria de Cantoblanco, 28049 Madrid");
		assertThat(json.write(universidad)).hasJsonPathStringValue("@.estado");
		assertThat(json.write(universidad)).extractingJsonPathStringValue("@.estado").isEqualTo("Pública");
		assertThat(json.write(universidad)).hasJsonPathStringValue("@.photo");
		assertThat(json.write(universidad)).extractingJsonPathStringValue("@.photo").isEqualTo(
				"https://www.comunidad.madrid/sites/default/files/styles/imagen_enlace_opcional/public/aud/educacion/uam_4.jpg?itok=T4uXwmfB");
		assertThat(json.write(universidad)).hasJsonPathStringValue("@.disponibilidad");
		assertThat(json.write(universidad)).extractingJsonPathStringValue("@.disponibilidad").isEqualTo("Abierta");

	}

	@Test
	void universidadDeserializationTest() throws IOException {
		String expected = """
				{
				    "id": "2",
				    "nombre": "Universidad Politécnica de Madrid",
				    "ubicacion": "CALLE RAMIRO DE MAEZTU, 7",
				    "estado": "Pública",
				    "photo": "https://www.comunidad.madrid/sites/default/files/styles/imagen_enlace_opcional/public/aud/educacion/upm_2.jpg?itok=BiaVDFnT",
				    "disponibilidad": "Abierta"
				}
				""";
//       assertThat(json.parse(expected)).isEqualTo(new Universidad(99L, 123.45));
		assertThat(json.parseObject(expected).getId()).isEqualTo(2);
		assertThat(json.parseObject(expected).getNombre()).isEqualTo("Universidad Politécnica de Madrid");
		assertThat(json.parseObject(expected).getUbicacion()).isEqualTo("CALLE RAMIRO DE MAEZTU, 7");
		assertThat(json.parseObject(expected).getEstado()).isEqualTo("Pública");
		assertThat(json.parseObject(expected).getPhoto()).isEqualTo(
				"https://www.comunidad.madrid/sites/default/files/styles/imagen_enlace_opcional/public/aud/educacion/upm_2.jpg?itok=BiaVDFnT");
		assertThat(json.parseObject(expected).getDisponibilidad()).isEqualTo("Abierta");
	}

	@Test
	void universidadesSerializationTest() throws IOException {
		assertThat(jsonList.write(universidades)).isStrictlyEqualToJson("list.json");
	}

	@Test
	void universidadesDeserializationTest() throws IOException {
		String expected = """
					         [
				{
				  "id": 1,
				  "nombre": "Universidad Autónoma de Madrid",
				  "ubicacion": "Ciudad Universitaria de Cantoblanco, 28049 Madrid",
				  "estado": "Pública",
				  "photo": "https://www.comunidad.madrid/sites/default/files/styles/imagen_enlace_opcional/public/aud/educacion/uam_4.jpg?itok=T4uXwmfB",
				  "disponibilidad": "Abierta"
				},
				{
				   "id": 2,
				   "nombre": "Universidad Carlos III de Madrid",
				   "ubicacion": "CALLE MADRID, 126",
				   "estado": "Pública",
				   "photo": "https://www.comunidad.madrid/sites/default/files/styles/imagen_enlace_opcional/public/aud/educacion/rectorado_uc3m.jpg?itok=CqDwgmkZ",
				   "disponibilidad": "Cerrada"
				},
				{
				  	"id": "3",
				  	"nombre": "Universidad Politécnica de Madrid",
				  	"ubicacion": "CALLE RAMIRO DE MAEZTU, 7",
				  	"estado": "Pública",
				  	"photo": "https://www.comunidad.madrid/sites/default/files/styles/imagen_enlace_opcional/public/aud/educacion/upm_2.jpg?itok=BiaVDFnT",
				  	"disponibilidad": "Abierta"
				}
				]
					         """;
		assertThat(jsonList.parse(expected)).isEqualTo(universidades);
	}
}
