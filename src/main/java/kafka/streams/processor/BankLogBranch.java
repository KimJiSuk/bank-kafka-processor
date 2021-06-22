package kafka.streams.processor;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class BankLogBranch {

    @Bean
    public Function<KStream<String, String>, KStream<?, String>[]> process() {

        Predicate<String, String> customer = (k, v) -> v.split(",")[0].equals("고객");
        Predicate<String, String> customerDetails = (k, v) -> v.split(",")[0].equals("고객상세");
        Predicate<String, String> account = (k, v) -> v.split(",")[0].equals("계좌");
        Predicate<String, String> accountConnect = (k, v) -> v.split(",")[0].equals("연결계좌");
        Predicate<String, String> transaction = (k, v) -> v.split(",")[0].equals("거래내역");
        Predicate<String, String> atmTransaction = (k, v) -> v.split(",")[0].equals("ATM거래내역");
        Predicate<String, String> autoTransaction = (k, v) -> v.split(",")[0].equals("자동이체거래내역");
        Predicate<String, String> transferTransaction = (k, v) -> v.split(",")[0].equals("이체거래내역");

        return input -> input.branch(customer, customerDetails, account, accountConnect, transaction,
                atmTransaction, autoTransaction, transferTransaction);
    }
}
