package car.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class NewPositionRequest {
    @NotNull
    private Coordinate initialCoordinate;
    @NotNull
    private List<MovementCommand> movementCommands;
}
