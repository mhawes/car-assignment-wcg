package car.assignment.integration;

import car.assignment.dto.Coordinate;
import car.assignment.dto.NewPositionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;

import static car.assignment.dto.MovementCommand.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarMovementTest {
    @Autowired
    private TestRestTemplate template;

    @Test
    public void testCaseOne() {
        //5,5:RFLFRFLF
        final NewPositionRequest request = NewPositionRequest.builder()
                .initialCoordinate(Coordinate.builder()
                        .x(5)
                        .y(5)
                        .build())
                .movementCommands(Arrays.asList(RIGHT, FORWARD, LEFT, FORWARD, RIGHT, FORWARD, LEFT, FORWARD))
                .build();

        ResponseEntity<Coordinate> response = template.postForEntity("/car/new/position", request, Coordinate.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Coordinate responseCoordinate = response.getBody();
        assertThat(responseCoordinate).isNotNull();
        assertThat(responseCoordinate.getX()).isEqualTo(7);
        assertThat(responseCoordinate.getY()).isEqualTo(7);
    }

    @Test
    public void testCaseTwo() {
        //6,6:FFLFFLFFLFF
        final NewPositionRequest request = NewPositionRequest.builder()
                .initialCoordinate(Coordinate.builder()
                        .x(6)
                        .y(6)
                        .build())
                .movementCommands(Arrays.asList(FORWARD, FORWARD, LEFT, FORWARD, FORWARD, LEFT, FORWARD, FORWARD, LEFT, FORWARD, FORWARD))
                .build();

        ResponseEntity<Coordinate> response = template.postForEntity("/car/new/position", request, Coordinate.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Coordinate responseCoordinate = response.getBody();
        assertThat(responseCoordinate).isNotNull();
        assertThat(responseCoordinate.getX()).isEqualTo(6);
        assertThat(responseCoordinate.getY()).isEqualTo(6);
    }

    @Test
    public void testCaseThree() {
        //5,5:FLFLFFRFFF
        final NewPositionRequest request = NewPositionRequest.builder()
                .initialCoordinate(Coordinate.builder()
                        .x(6)
                        .y(6)
                        .build())
                .movementCommands(Arrays.asList(FORWARD, LEFT, FORWARD, LEFT, FORWARD, FORWARD, RIGHT, FORWARD, FORWARD, FORWARD))
                .build();

        ResponseEntity<Coordinate> response = template.postForEntity("/car/new/position", request, Coordinate.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Coordinate responseCoordinate = response.getBody();
        assertThat(responseCoordinate).isNotNull();
        assertThat(responseCoordinate.getX()).isEqualTo(4);
        assertThat(responseCoordinate.getY()).isEqualTo(1);
    }

    @Test
    public void shouldReturnBadRequestDueToMissingMandatoryFields() {
        final NewPositionRequest request = NewPositionRequest.builder()
                .initialCoordinate(Coordinate.builder().build())
                .build();

        ResponseEntity<Coordinate> response = template.postForEntity("/car/new/position", request, Coordinate.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldReturnOkWithValidCoordinateRangeMax() {
        final NewPositionRequest request = NewPositionRequest.builder()
                .initialCoordinate(Coordinate.builder()
                        .x(15)
                        .y(15)
                        .build())
                .movementCommands(Collections.emptyList())
                .build();

        ResponseEntity<Coordinate> response = template.postForEntity("/car/new/position", request, Coordinate.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturnBadRequestDueToInvalidCoordinateRangeMax() {
        final NewPositionRequest request = NewPositionRequest.builder()
                .initialCoordinate(Coordinate.builder()
                        .x(16)
                        .y(16)
                        .build())
                .movementCommands(Collections.emptyList())
                .build();

        ResponseEntity<Coordinate> response = template.postForEntity("/car/new/position", request, Coordinate.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldReturnBadRequestDueToInvalidCoordinateRangeMin() {
        final NewPositionRequest request = NewPositionRequest.builder()
                .initialCoordinate(Coordinate.builder()
                        .x(0)
                        .y(0)
                        .build())
                .movementCommands(Collections.emptyList())
                .build();

        ResponseEntity<Coordinate> response = template.postForEntity("/car/new/position", request, Coordinate.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldReturnOkWithValidCoordinateRangeMin() {
        final NewPositionRequest request = NewPositionRequest.builder()
                .initialCoordinate(Coordinate.builder()
                        .x(1)
                        .y(1)
                        .build())
                .movementCommands(Collections.emptyList())
                .build();

        ResponseEntity<Coordinate> response = template.postForEntity("/car/new/position", request, Coordinate.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
