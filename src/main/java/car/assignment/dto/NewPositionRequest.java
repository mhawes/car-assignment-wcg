package car.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class NewPositionRequest {
    @NotNull
    @Min(1)
    @Max(15)
    private Integer initialX;

    @NotNull
    @Min(1)
    @Max(15)
    private Integer initialY;

    @NotNull
    @Valid
    private List<MovementCommand> movementCommands;
}
