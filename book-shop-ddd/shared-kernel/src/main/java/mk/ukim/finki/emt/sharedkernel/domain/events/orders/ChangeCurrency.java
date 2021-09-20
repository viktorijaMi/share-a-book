//package mk.ukim.finki.emt.sharedkernel.domain.events.orders;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Getter;
//import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObject;
//import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;
//import mk.ukim.finki.emt.sharedkernel.domain.config.TopicHolder;
//import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;
//import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
//
//@Getter
//public class ChangeCurrency extends DomainEvent {
//
//    private Currency currency;
//
//    public ChangeCurrency(){
//        super(TopicHolder.CHANGE_CURRENCY);
//        this.currency = Currency.MKD;
//    }
//
//    public ChangeCurrency(String topic) {
//        super(TopicHolder.CHANGE_CURRENCY);
//    }
//
//    public ChangeCurrency(
//            @JsonProperty("currency") Currency currency) {
//        super(TopicHolder.CHANGE_CURRENCY);
//        this.currency = currency;
//    }
//}
