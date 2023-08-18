package com.zoologico.zoo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testEliminarUsuario() {
        String url = "/eliminar?id=3"; // Cambia el ID según tus necesidades
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

        HttpStatus statusCode = (HttpStatus) response.getStatusCode();
        System.out.println("Status Code: " + statusCode);

        // Simula el valor de rolUserLoad
        String rolUserLoad = "ADMIN";
        System.out.println("rolUserLoad: " + rolUserLoad);
    }
}
