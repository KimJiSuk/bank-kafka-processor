package kafka.streams.processor.application.branch;

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
            // 사전 준비 데이터는 Round-Robin 방식으로 들어가 있기 때문에 Key 값이 없어, 셋팅 필요
            // 향후 Join 위해서 Key 값 셋팅.
            input = input.map(((key, value) -> new KeyValue<>(getKeyByValue(value), value)));

            // 10개의 토픽으로 branch
            Predicate<String, String> customer = (k, v) -> v.split(",")[0].equals("고객");
            Predicate<String, String> customerDetails = (k, v) -> v.split(",")[0].equals("고객상세");
            Predicate<String, String> account = (k, v) -> {
                String[] temp = v.split(",");
                return temp[0].equals("계좌") && temp[temp.length-1].equals("입출금");
            };
            Predicate<String, String> safebox = (k, v) -> {
                String[] temp = v.split(",");
                return temp[0].equals("계좌") && temp[temp.length-1].equals("세이프박스");
            };
            Predicate<String, String> accountConnect = (k, v) -> v.split(",")[0].equals("연결계좌");

            Predicate<String, String> atmTransaction = (k, v) -> {
                String[] temp = v.split(",");
                return v.split(",")[0].equals("거래내역") && temp[temp.length-2].equals("ATM");
            };
            Predicate<String, String> autoTransaction = (k, v) -> {
                String[] temp = v.split(",");
                return v.split(",")[0].equals("거래내역") && temp[temp.length-2].equals("AUT");
            };
            Predicate<String, String> transferTransaction = (k, v) -> {
                String[] temp = v.split(",");
                return v.split(",")[0].equals("거래내역") && temp[temp.length-2].equals("TRN");
            };
            Predicate<String, String> atmTransactionDetails = (k, v) -> v.split(",")[0].equals("ATM거래내역");
            Predicate<String, String> autoTransactionDetails = (k, v) -> v.split(",")[0].equals("자동이체거래내역");
            Predicate<String, String> transferTransactionDetails = (k, v) -> v.split(",")[0].equals("이체거래내역");

            return input.branch(customer, customerDetails, account, safebox, accountConnect,
                    atmTransaction, autoTransaction, transferTransaction,
                    atmTransactionDetails, autoTransactionDetails, transferTransactionDetails);
        };
    }

    /*
    로그명으로 Key 값을 구함.
     */
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