package kafka.streams.processor;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class ProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcessorApplication.class, args);
	}

	public static class BankLogBranch {

		@Bean
		public Function<KStream<String, String>, KStream<?, String>[]> process() {

			Predicate<Object, String> customer = (k, v) -> v.split(",")[0].equals("고객");
			Predicate<Object, String> customerDetails = (k, v) -> v.split(",")[0].equals("고객상세");
			Predicate<Object, String> account = (k, v) -> v.split(",")[0].equals("계좌");
			Predicate<Object, String> accountConnect = (k, v) -> v.split(",")[0].equals("연결계좌");
			Predicate<Object, String> transaction = (k, v) -> v.split(",")[0].equals("거래내역");
			Predicate<Object, String> atmTransaction = (k, v) -> v.split(",")[0].equals("ATM거래내역");
			Predicate<Object, String> autoTransaction = (k, v) -> v.split(",")[0].equals("자동이체거래내역");
			Predicate<Object, String> transferTransaction = (k, v) -> v.split(",")[0].equals("이체거래내역");

			return input -> input.branch(customer, customerDetails, account, accountConnect, transaction,
					atmTransaction, autoTransaction, transferTransaction);
		}
	}
}
