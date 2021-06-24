package kafka.streams.processor.application.join;

import kafka.streams.processor.topic.Transaction;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.function.BiFunction;

@Configuration
public class TransactionJoin {

    @Value("${spring.cloud.stream.join.duration.second.transaction}")
    private int d_seconds;

    @Bean
    public BiFunction<KStream<String, String>, KStream<String, String>, KStream<String, Transaction>> atmTransaction() {
        // ATM Type 거래내역, ATM거래내역 Join
        return (atm, atmDetails) -> atm.join(atmDetails,
                Transaction::new,
                JoinWindows.of(Duration.ofSeconds(d_seconds)),
                StreamJoined.with(Serdes.String(), Serdes.String(), Serdes.String()));
    }

    @Bean
    public BiFunction<KStream<String, String>, KStream<String, String>, KStream<String, Transaction>> autoTransaction() {
        // AUT Type 거래내역, 자동이체거래내역 Join
        return (auto, autoDetails) -> auto.join(autoDetails,
                Transaction::new,
                JoinWindows.of(Duration.ofSeconds(d_seconds)),
                StreamJoined.with(Serdes.String(), Serdes.String(), Serdes.String()));
    }

    @Bean
    public BiFunction<KStream<String, String>, KStream<String, String>, KStream<String, Transaction>> transferTransaction() {
        // TRN Type 거래내역, 이체거래내역 Join
        return (transfer, transferDetails) -> transfer.join(transferDetails,
                Transaction::new,
                JoinWindows.of(Duration.ofSeconds(d_seconds)),
                StreamJoined.with(Serdes.String(), Serdes.String(), Serdes.String()));
    }

}
