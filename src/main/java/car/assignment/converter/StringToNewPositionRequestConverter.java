package car.assignment.converter;

import car.assignment.dto.NewPositionRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToNewPositionRequestConverter implements Converter<String, NewPositionRequest> {
    @Override
    public NewPositionRequest convert(String newPositionRequestString) {
        return null;
    }
}
