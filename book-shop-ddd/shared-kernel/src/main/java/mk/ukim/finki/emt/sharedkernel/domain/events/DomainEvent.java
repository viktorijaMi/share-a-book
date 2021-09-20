package mk.ukim.finki.emt.sharedkernel.domain.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.ui.Model;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
public class DomainEvent {

    private String topic;
    private LocalDateTime occurredOn;

    public DomainEvent(String topic) {
        this.occurredOn = LocalDateTime.now();
        this.topic = topic;
    }

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        String output = null;
        try {
            output = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {

        }
        return output;
    }

    public String topic() {
        return topic;
    }

    public static <E extends DomainEvent> E fromJson(String json, Class<E> eventClass) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json,eventClass);
    }
}

