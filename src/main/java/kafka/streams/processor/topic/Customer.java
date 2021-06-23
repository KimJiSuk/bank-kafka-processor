package kafka.streams.processor.topic;

import lombok.Getter;

@Getter
public class Customer {

    public Customer(String customer, String customerDetail) {
        String[] customers = customer.split(",", -1);
        String[] customerDetails = customerDetail.split(",", -1);

        this.cstno = customers[1];
        this.custNm = customers[3];
        this.recNm = customers[4];
        this.sexCd = customerDetails[3];
        this.birth = customerDetails[4];
        this.createAt = customers[2];
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
