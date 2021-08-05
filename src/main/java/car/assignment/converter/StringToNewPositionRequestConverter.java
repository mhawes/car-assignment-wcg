package car.assignment.converter;

import car.assignment.dto.MovementCommand;
import car.assignment.dto.NewPositionRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class StringToNewPositionRequestConverter implements Converter<String, NewPositionRequest> {
    public static final String REQUEST_REGEX = "^\\d,\\d:[FLR]*$";
    public static final int X_INDEX = 0;
    public static final int Y_INDEX = 2;
    public static final int MOVEMENT_COMMAND_START_INDEX = 4;

    @Override
    public NewPositionRequest convert(final String newPositionRequestString) {
        if (newPositionRequestString == null || !newPositionRequestString.matches(REQUEST_REGEX)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request string");
        }
        return NewPositionRequest.builder()
                .initialX(convertInitialX(newPositionRequestString))
                .initialY(convertInitialY(newPositionRequestString))
                .movementCommands(convertMovementCommands(newPositionRequestString))
                .build();
    }

    private Integer convertInitialX(final String newPositionRequestString) {
        return Integer.valueOf(newPositionRequestString.substring(X_INDEX, X_INDEX + 1));
    }

    private Integer convertInitialY(final String newPositionRequestString) {
        return Integer.valueOf(newPositionRequestString.substring(Y_INDEX, Y_INDEX + 1));
    }

    private List<MovementCommand> convertMovementCommands(final String newPositionRequestString) {
        List<MovementCommand> movementCommands = new ArrayList<>();
        for (String movementCommandString : newPositionRequestString.substring(MOVEMENT_COMMAND_START_INDEX).split("")) {
            if (movementCommandString != null && !movementCommandString.isEmpty()) {
                movementCommands.add(MovementCommand.valueOf(movementCommandString));
            }
        }
        return movementCommands;
    }
}
