package car.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.swing.text.Position;

@Builder
@Getter
@AllArgsConstructor
public class Car {
    private Position position;
    private Direction direction;
}
