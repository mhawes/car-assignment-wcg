package car.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
public class Coordinate {
    @NotNull
    @Min(1)
    @Max(15)
    private Integer x;

    @NotNull
    @Min(1)
    @Max(15)
    private Integer y;
}
