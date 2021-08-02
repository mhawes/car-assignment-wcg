package car.assignment.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class NewPositionRequest {
    private Coordinate initialCoordinate;
    private List<MovementCommand> movementCommands;
}
