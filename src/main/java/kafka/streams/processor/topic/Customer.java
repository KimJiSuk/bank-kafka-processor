package kafka.streams.processor.topic;

import lombok.Getter;

@Getter
public class Customer {

    public Customer(String customer, String customerDetails) {
        String[] customerList = customer.split(",", -1);
        String[] customerDetailsList = customerDetails.split(",", -1);

        this.cstno = customerList[1];
        this.custNm = customerList[3];
        this.recNm = customerList[4];
        this.sexCd = customerDetailsList[3];
        this.birth = customerDetailsList[4];
        this.createAt = customerList[2];
    }

    private final String cstno;
    private final String custNm;
    private final String recNm;
    private final String sexCd;
    private final String birth;
    private final String createAt;

    @Override
    public String toString() {
        return "Customer{" +
                "cstno='" + cstno + '\'' +
                ", custNm='" + custNm + '\'' +
                ", recNm='" + recNm + '\'' +
                ", sexCd='" + sexCd + '\'' +
                ", birth='" + birth + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
