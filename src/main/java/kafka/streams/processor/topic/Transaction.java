package kafka.streams.processor.topic;

import kafka.streams.processor.entity.Tran;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Transaction {

    private static final String EMPTY_STRING = "";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Transaction(String transaction, String transactionDetail) {
        String[] transactions = transaction.split(",", -1);
        String[] transactionDetails = transactionDetail.split(",", -1);
        this.acno = transactions[1];
        this.seqno = transactions[2];
        this.regDttm = transactions[3];
        this.txChnl = transactions[4];
        this.aftrBal = transactions[5];

        // 거래채널에 따라 값 셋팅
        switch (this.txChnl) {
            case "ATM":
                this.atmCd = transactionDetails[transactionDetails.length-1];
                this.recvNm = EMPTY_STRING;
                this.autoCycl = EMPTY_STRING;
                break;
            case "AUT":
                this.autoCycl = transactionDetails[transactionDetails.length-1];
                this.atmCd = EMPTY_STRING;
                this.recvNm = EMPTY_STRING;
                break;
            case "TRN":
                this.recvNm = transactionDetails[transactionDetails.length-1];
                this.atmCd = EMPTY_STRING;
                this.autoCycl = EMPTY_STRING;
                break;
            default:
                this.atmCd = EMPTY_STRING;
                this.recvNm = EMPTY_STRING;
                this.autoCycl = EMPTY_STRING;
        }
    }

    private String acno;
    private String seqno;
    private String regDttm;
    private String txChnl;
    private String aftrBal;
    private String atmCd;
    private String autoCycl;
    private String recvNm;

    public Tran toEntity() {
        return Tran.builder()
                .acno(this.acno)
                .seqno(this.seqno)
                .regDttm(LocalDateTime.parse(this.regDttm, dateTimeFormatter))
                .txChnl(this.txChnl)
                .aftrBal(this.aftrBal)
                .atmCd(this.atmCd)
                .autoCycl(this.autoCycl)
                .recvNm(this.recvNm)
                .build();
    }

}
