package kafka.streams.processor.topic;

import lombok.Getter;

@Getter
public class Transaction {

    private static final String EMPTY_STRING = "";

    public Transaction(String transaction, String transactionDetail) {
        String[] transactions = transaction.split(",", -1);
        String[] transactionDetails = transactionDetail.split(",", -1);
        this.acno = transactions[1];
        this.seqno = transactions[2];
        this.regDttm = transactions[3];
        this.txChnl = transactions[4];
        this.aftrBal = transactions[5];

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

    private final String acno;
    private final String seqno;
    private final String regDttm;
    private final String txChnl;
    private final String aftrBal;
    private final String atmCd;
    private final String autoCycl;
    private final String recvNm;

    @Override
    public String toString() {
        return "Transaction{" +
                "acno='" + acno + '\'' +
                ", seqno='" + seqno + '\'' +
                ", regDttm='" + regDttm + '\'' +
                ", txChnl='" + txChnl + '\'' +
                ", aftrBal='" + aftrBal + '\'' +
                ", atmCd='" + atmCd + '\'' +
                ", recvNm='" + recvNm + '\'' +
                ", autoCycl='" + autoCycl + '\'' +
                '}';
    }
}
