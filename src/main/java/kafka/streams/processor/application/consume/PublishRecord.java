package kafka.streams.processor.application.consume;

import kafka.streams.processor.domain.AccoRepository;
import kafka.streams.processor.domain.CustomerRepository;
import kafka.streams.processor.domain.TranRepository;
import kafka.streams.processor.topic.Account;
import kafka.streams.processor.topic.Customer;
import kafka.streams.processor.topic.Transaction;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class PublishRecord {

    private final CustomerRepository customerRepository;
    private final AccoRepository accoRepository;
    private final TranRepository tranRepository;

    @Bean
    public Consumer<KStream<String, Customer>> consumeCustomer() {
        return customer -> customer.foreach((key, value) -> customerRepository.save(value.toEntity()));
    }

    @Bean
    public Consumer<KStream<String, Account>> consumeAccount() {
        return account -> account.foreach((key, value) -> accoRepository.save(value.toEntity()));
    }

    @Bean
    public Consumer<KStream<String, Transaction>> consumeTransaction() {
        return transaction -> transaction.foreach((key, value) -> tranRepository.save(value.toEntity()));
    }
}
