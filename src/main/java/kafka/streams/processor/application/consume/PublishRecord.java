package kafka.streams.processor.application.consume;

import kafka.streams.processor.topic.Account;
import kafka.streams.processor.topic.Customer;
import kafka.streams.processor.topic.Transaction;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class PublishRecord {

    @Bean
    public Consumer<KStream<String, Account>> consumeAccount() {
        return account -> account.foreach(((key, value) -> System.out.println(value.toString())));
    }

    @Bean
    public Consumer<KStream<String, Customer>> consumeCustomer() {
        return customer -> customer.foreach(((key, value) -> System.out.println(value.toString())));
    }

    @Bean
    public Consumer<KStream<String, Transaction>> consumeTransaction() {
        return transaction -> transaction.foreach(((key, value) -> System.out.println(value.toString())));
    }
}
