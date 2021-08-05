package car.assignment.domain;

import car.assignment.dto.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.swing.text.Position;

@Builder
@Getter
@AllArgsConstructor
public class Car {
    private Coordinate coordinate;
    private Direction direction;
}
