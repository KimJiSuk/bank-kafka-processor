package kafka.streams.processor.branch;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class LogBranch {

    @Bean
    public Function<KStream<String, String>, KStream<String, String>[]> process() {

        return input -> {
            input = input.map(((key, value) -> new KeyValue<>(getKeyByValue(value), value)));

            Predicate<String, String> customer = (k, v) -> k != null && v.split(",")[0].equals("고객");
            Predicate<String, String> customerDetails = (k, v) -> k != null && v.split(",")[0].equals("고객상세");
            Predicate<String, String> account = (k, v) -> k != null && v.split(",")[0].equals("계좌");
            Predicate<String, String> accountConnect = (k, v) -> k != null && v.split(",")[0].equals("연결계좌");
            Predicate<String, String> transaction = (k, v) -> k != null && v.split(",")[0].equals("거래내역");
            Predicate<String, String> atmTransaction = (k, v) -> k != null && v.split(",")[0].equals("ATM거래내역");
            Predicate<String, String> autoTransaction = (k, v) -> k != null && v.split(",")[0].equals("자동이체거래내역");
            Predicate<String, String> transferTransaction = (k, v) -> k != null && v.split(",")[0].equals("이체거래내역");

            return input.branch(customer, customerDetails, account, accountConnect, transaction,
                    atmTransaction, autoTransaction, transferTransaction);
        };
    }

    private String getKeyByValue(String value) {
        String[] valueList = value.split(",");

        if (valueList.length < 3)
            return null;

        switch (valueList[0]) {
            case "고객":
            case "고객상세":
            case "계좌":
            case "연결계좌":
                return valueList[1];
            case "거래내역":
            case "ATM거래내역":
            case "자동이체거래내역":
            case "이체거래내역":
                return valueList[1] + "," + valueList[2];
            default:
                return null;
        }
    }
}