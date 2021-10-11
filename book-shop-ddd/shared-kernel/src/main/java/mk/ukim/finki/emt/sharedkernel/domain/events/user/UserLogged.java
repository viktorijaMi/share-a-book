package mk.ukim.finki.emt.sharedkernel.domain.events.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;
import mk.ukim.finki.emt.sharedkernel.domain.user.Address;

@Getter
public class UserLogged extends DomainEvent {
    private String userId;
    private String username;
    private Address userAddress;

    public UserLogged(){
        super(TopicHolder.TOPIC_USER_LOGGED);
        this.userId = null;
        this.username = null;
        this.userAddress = new Address("", 0, "","");
    }

    public UserLogged(String topic) {
        super(TopicHolder.TOPIC_USER_LOGGED);
    }

    @JsonCreator
    public UserLogged(
            @JsonProperty("userId") String userId,
            @JsonProperty("username") String username,
            @JsonProperty("userAddress") Address userAddress
            ) {
        super(TopicHolder.TOPIC_USER_LOGGED);
        this.userId = userId;
        this.username = username;
        this.userAddress = userAddress;
    }
}
