//package mk.ukim.finki.emt.ordermanagement.service;
//
//import mk.ukim.finki.emt.ordermanagement.domain.exceptions.OrderIdNotExistsException;
//import mk.ukim.finki.emt.ordermanagement.domain.model.Order;
//import mk.ukim.finki.emt.ordermanagement.domain.model.OrderId;
//import mk.ukim.finki.emt.ordermanagement.domain.valueObjects.Book;
//import mk.ukim.finki.emt.ordermanagement.service.forms.OrderForm;
//import mk.ukim.finki.emt.ordermanagement.service.forms.OrderItemForm;
//import mk.ukim.finki.emt.ordermanagement.xport.client.BookClient;
//import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
//import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Arrays;
//import java.util.List;
//
//@SpringBootTest
//public class OrderServiceImplTest {
//
//    @Autowired
//    private OrderService orderService;
//
//    @Autowired
//    private BookClient bookClient;
//
//    @Test
//    public void testPlaceOrder() {
//        List<Book> bookList = bookClient.findAll();
//        Book b1 = bookList.get(0);
//        Book b2 = bookList.get(1);
//
//        OrderItemForm oi1 = new OrderItemForm();
//        oi1.setBook(b1);
//        oi1.setQuantity(1);
//
//        OrderItemForm oi2 = new OrderItemForm();
//        oi2.setBook(b2);
//        oi2.setQuantity(2);
//
//        OrderForm orderForm = new OrderForm();
//        orderForm.setCurrency(Currency.MKD);
//        orderForm.setItems(Arrays.asList(oi1, oi2));
//
//        OrderId newOrderId = orderService.placeOrder(orderForm);
//        Order newOrder = orderService.findById(newOrderId).orElseThrow(OrderIdNotExistsException::new);
//
//        Money outMoney = b1.getPrice().multiply(oi1.getQuantity()).add(b2.getPrice().multiply(oi2.getQuantity()));
//        Assertions.assertEquals(newOrder.total(), outMoney);
//
//    }
//}
