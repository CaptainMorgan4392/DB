package ru.nsu.kosarev.db.organizer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import ru.nsu.kosarev.db.DbApplication;

@SpringBootTest(classes = DbApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrganizerCrudIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void create() {

    }

    @Test
    public void fetchAll() {

    }

    @Test
    public void fetchPage() {

    }

    @Test
    public void update() {

    }

    @Test
    public void delete() {

    }

}
