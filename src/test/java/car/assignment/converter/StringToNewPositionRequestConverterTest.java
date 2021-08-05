package car.assignment.converter;

import car.assignment.dto.NewPositionRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static car.assignment.dto.MovementCommand.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StringToNewPositionRequestConverterTest {

    @InjectMocks
    private StringToNewPositionRequestConverter converter;

    @Test
    void shouldConvertStringToObject() {
        NewPositionRequest result = converter.convert("5,6:FLR");

        assertThat(result).isNotNull();
        assertThat(result.getInitialX()).isEqualTo(5);
        assertThat(result.getInitialY()).isEqualTo(6);
        assertThat(result.getMovementCommands()).containsExactly(F, L, R);
    }

    @Test
    void shouldConvertIfNoMovementsProvided() {
        NewPositionRequest result = converter.convert("5,6:");

        assertThat(result).isNotNull();
        assertThat(result.getInitialX()).isEqualTo(5);
        assertThat(result.getInitialY()).isEqualTo(6);
        assertThat(result.getMovementCommands()).isEmpty();
    }

    @Test
    void shouldFailToConvertIfStringIsInvalid() {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> converter.convert("5,6,7:FLRO"),
                "Invalid request string");
    }
}