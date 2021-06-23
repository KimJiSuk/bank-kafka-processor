package kafka.streams.processor.join;

import kafka.streams.processor.topic.Customer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.function.BiFunction;

@Configuration
public class CustomerJoin {

    @Bean
    public BiFunction<KStream<String, String>, KStream<String, String>, KStream<String, Customer>> customer() {
        return (customer, customerDetails) -> customer.join(customerDetails,
                Customer::new,
                JoinWindows.of(Duration.ofSeconds(10)),
                StreamJoined.with(Serdes.String(), Serdes.String(), Serdes.String()));
    }
}
