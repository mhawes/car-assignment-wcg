package car.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class Coordinate {
    private Integer x;
    private Integer y;

    @Override
    public String toString() {
        return String.format("%d,%d", x, y);
    }
}
