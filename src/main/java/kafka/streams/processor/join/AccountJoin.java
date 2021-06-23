package kafka.streams.processor.join;

import kafka.streams.processor.topic.Account;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.function.BiFunction;
import java.util.function.Function;

@Configuration
public class AccountJoin {

    @Bean
    public Function<KStream<String, String>, KStream<String, Account>> account() {
        return input -> input.map((k, v) -> new KeyValue<>(k, new Account(v)));
    }

    @Bean
    public BiFunction<KStream<String, String>, KStream<String, String>, KStream<String, Account>> safebox() {
        return (safebox, accountConnect) -> safebox.join(accountConnect,
                Account::new,
                JoinWindows.of(Duration.ofSeconds(10)),
                StreamJoined.with(Serdes.String(), Serdes.String(), Serdes.String()));
    }
}
