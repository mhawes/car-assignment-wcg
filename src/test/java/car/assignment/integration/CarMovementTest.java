package car.assignment.integration;

import car.assignment.dto.Coordinate;
import car.assignment.dto.NewPositionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;

import static car.assignment.dto.MovementCommand.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarMovementTest {
    public static final String PATH_CAR_NEW_POSITION = "/car/new/position";
    public static final String PATH_CAR_NEW_POSITION_STRING = "/car/new/position/string";

    @Autowired
    private TestRestTemplate template;

    @Test
    public void testCaseOne() {
        //5,5:RFLFRFLF
        final NewPositionRequest request = NewPositionRequest.builder()
                .initialX(5)
                .initialY(5)
                .movementCommands(Arrays.asList(R, F, L, F, R, F, L, F))
                .build();

        ResponseEntity<Coordinate> response = template.postForEntity(PATH_CAR_NEW_POSITION, request, Coordinate.class);

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
                .initialX(6)
                .initialY(6)
                .movementCommands(Arrays.asList(F, F, L, F, F, L, F, F, L, F, F))
                .build();

        ResponseEntity<Coordinate> response = template.postForEntity(PATH_CAR_NEW_POSITION, request, Coordinate.class);

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
                .initialX(5)
                .initialY(5)
                .movementCommands(Arrays.asList(F, L, F, L, F, F, R, F, F, F))
                .build();

        ResponseEntity<Coordinate> response = template.postForEntity(PATH_CAR_NEW_POSITION, request, Coordinate.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Coordinate responseCoordinate = response.getBody();
        assertThat(responseCoordinate).isNotNull();
        assertThat(responseCoordinate.getX()).isEqualTo(4);
        assertThat(responseCoordinate.getY()).isEqualTo(1);
    }

    @Test
    public void shouldReturnBadRequestDueToMissingMandatoryFields() {
        final NewPositionRequest request = NewPositionRequest.builder().build();

        ResponseEntity<Coordinate> response = template.postForEntity(PATH_CAR_NEW_POSITION, request, Coordinate.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldReturnOkWithValidCoordinateRangeMax() {
        final NewPositionRequest request = NewPositionRequest.builder()
                .initialX(15)
                .initialY(15)
                .movementCommands(Collections.emptyList())
                .build();

        ResponseEntity<Coordinate> response = template.postForEntity(PATH_CAR_NEW_POSITION, request, Coordinate.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturnBadRequestDueToInvalidCoordinateRangeMax() {
        final NewPositionRequest request = NewPositionRequest.builder()
                .initialX(16)
                .initialY(16)
                .movementCommands(Collections.emptyList())
                .build();

        ResponseEntity<Coordinate> response = template.postForEntity(PATH_CAR_NEW_POSITION, request, Coordinate.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldReturnBadRequestDueToInvalidCoordinateRangeMin() {
        final NewPositionRequest request = NewPositionRequest.builder()
                .initialX(0)
                .initialY(0)
                .movementCommands(Collections.emptyList())
                .build();

        ResponseEntity<Coordinate> response = template.postForEntity(PATH_CAR_NEW_POSITION, request, Coordinate.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldReturnOkWithValidCoordinateRangeMin() {
        final NewPositionRequest request = NewPositionRequest.builder()
                .initialX(1)
                .initialY(1)
                .movementCommands(Collections.emptyList())
                .build();

        ResponseEntity<Coordinate> response = template.postForEntity(PATH_CAR_NEW_POSITION, request, Coordinate.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldAcceptRequestAsStringParameter() {
        URI uri = UriComponentsBuilder
                .fromPath(PATH_CAR_NEW_POSITION_STRING)
                .queryParam("newPositionString", "6,6:FFLFFLFFLFF")
                .build()
                .toUri();
        ResponseEntity<String> response = template.postForEntity(uri, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        String responseCoordinate = response.getBody();
        assertThat(responseCoordinate).isNotNull();
        assertThat(responseCoordinate).isEqualTo("6,6");
    }

    @Test
    public void shouldRejectRequestAsStringParameterWhenNotValid() {
        URI uri = UriComponentsBuilder
                .fromPath(PATH_CAR_NEW_POSITION_STRING)
                .queryParam("newPositionString", "6,6,5:FFABC")
                .build()
                .toUri();
        ResponseEntity<String> response = template.postForEntity(uri, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldRejectRequestAsXIsOutsideAcceptedRange() {
        URI uri = UriComponentsBuilder
                .fromPath(PATH_CAR_NEW_POSITION_STRING)
                .queryParam("newPositionString", "16,6:FF")
                .build()
                .toUri();
        ResponseEntity<String> response = template.postForEntity(uri, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldRejectRequestAsYIsOutsideAcceptedRange() {
        URI uri = UriComponentsBuilder
                .fromPath(PATH_CAR_NEW_POSITION_STRING)
                .queryParam("newPositionString", "6,16:FF")
                .build()
                .toUri();
        ResponseEntity<String> response = template.postForEntity(uri, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldRejectRequestAsStringParameterWhenEmptyString() {
        URI uri = UriComponentsBuilder
                .fromPath(PATH_CAR_NEW_POSITION_STRING)
                .queryParam("newPositionString", "")
                .build()
                .toUri();
        ResponseEntity<String> response = template.postForEntity(uri, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldRejectRequestAsStringParameterWhenBlankString() {
        URI uri = UriComponentsBuilder
                .fromPath(PATH_CAR_NEW_POSITION_STRING)
                .queryParam("newPositionString", " ")
                .build()
                .toUri();
        ResponseEntity<String> response = template.postForEntity(uri, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
