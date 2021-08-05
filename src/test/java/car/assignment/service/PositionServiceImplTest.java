package car.assignment.service;

import car.assignment.dto.Coordinate;
import car.assignment.dto.NewPositionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static car.assignment.dto.MovementCommand.*;
import static car.assignment.dto.MovementCommand.F;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PositionServiceImplTest {

    @InjectMocks
    private PositionServiceImpl service;

    @Test
    void shouldMoveToNewPosition_typicalScenario() {
        final NewPositionRequest newPositionRequest = NewPositionRequest.builder()
                .initialX(5)
                .initialY(5)
                .movementCommands(Arrays.asList(R, F, L, F, R, F, L, F))
                .build();

        Coordinate result = service.moveToNewPosition(newPositionRequest);

        assertThat(result.getX()).isEqualTo(7);
        assertThat(result.getY()).isEqualTo(7);
    }

    @Test
    void shouldMoveToNewPosition_noMovement() {
        final NewPositionRequest newPositionRequest = NewPositionRequest.builder()
                .initialX(1)
                .initialY(1)
                .movementCommands(Collections.emptyList())
                .build();

        Coordinate result = service.moveToNewPosition(newPositionRequest);

        assertThat(result.getX()).isEqualTo(1);
        assertThat(result.getY()).isEqualTo(1);
    }

    @Test
    void shouldMoveToNewPosition_noMovementSpin() {
        final NewPositionRequest newPositionRequest = NewPositionRequest.builder()
                .initialX(1)
                .initialY(1)
                .movementCommands(Arrays.asList(L,L,L,L,R,R,R,R))
                .build();

        Coordinate result = service.moveToNewPosition(newPositionRequest);

        assertThat(result.getX()).isEqualTo(1);
        assertThat(result.getY()).isEqualTo(1);
    }

    @Test
    void shouldMoveToNewPosition_moveForward() {
        final NewPositionRequest newPositionRequest = NewPositionRequest.builder()
                .initialX(5)
                .initialY(5)
                .movementCommands(Collections.singletonList(F))
                .build();

        Coordinate result = service.moveToNewPosition(newPositionRequest);

        assertThat(result.getX()).isEqualTo(5);
        assertThat(result.getY()).isEqualTo(6);
    }

    @Test
    void shouldMoveToNewPosition_moveBackwards() {
        final NewPositionRequest newPositionRequest = NewPositionRequest.builder()
                .initialX(5)
                .initialY(5)
                .movementCommands(Arrays.asList(L,L,F))
                .build();

        Coordinate result = service.moveToNewPosition(newPositionRequest);

        assertThat(result.getX()).isEqualTo(5);
        assertThat(result.getY()).isEqualTo(4);
    }

    @Test
    void shouldMoveToNewPosition_moveLeftAndForward() {
        final NewPositionRequest newPositionRequest = NewPositionRequest.builder()
                .initialX(5)
                .initialY(5)
                .movementCommands(Arrays.asList(L,F))
                .build();

        Coordinate result = service.moveToNewPosition(newPositionRequest);

        assertThat(result.getX()).isEqualTo(4);
        assertThat(result.getY()).isEqualTo(5);
    }

    @Test
    void shouldMoveToNewPosition_moveRightAndForward() {
        final NewPositionRequest newPositionRequest = NewPositionRequest.builder()
                .initialX(5)
                .initialY(5)
                .movementCommands(Arrays.asList(R,F))
                .build();

        Coordinate result = service.moveToNewPosition(newPositionRequest);

        assertThat(result.getX()).isEqualTo(6);
        assertThat(result.getY()).isEqualTo(5);
    }

    @Test
    void shouldMoveToNewPosition_noMovementAtCorner() {
        final NewPositionRequest newPositionRequest = NewPositionRequest.builder()
                .initialX(15)
                .initialY(15)
                .movementCommands(Arrays.asList(F,R,F))
                .build();

        Coordinate result = service.moveToNewPosition(newPositionRequest);

        assertThat(result.getX()).isEqualTo(15);
        assertThat(result.getY()).isEqualTo(15);
    }

    @Test
    void shouldMoveToNewPosition_noMovementAtAnotherCorner() {
        final NewPositionRequest newPositionRequest = NewPositionRequest.builder()
                .initialX(1)
                .initialY(1)
                .movementCommands(Arrays.asList(R,R,F,R,F))
                .build();

        Coordinate result = service.moveToNewPosition(newPositionRequest);

        assertThat(result.getX()).isEqualTo(1);
        assertThat(result.getY()).isEqualTo(1);
    }

    @Test
    void shouldMoveToNewPosition_stopAtEdgeIgnoringSubsequentMovements() {
        final NewPositionRequest newPositionRequest = NewPositionRequest.builder()
                .initialX(14)
                .initialY(14)
                .movementCommands(Arrays.asList(F,F,F,F,F,F))
                .build();

        Coordinate result = service.moveToNewPosition(newPositionRequest);

        assertThat(result.getX()).isEqualTo(14);
        assertThat(result.getY()).isEqualTo(15);
    }
}