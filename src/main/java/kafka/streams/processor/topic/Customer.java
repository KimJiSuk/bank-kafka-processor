package kafka.streams.processor.topic;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
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

    private String cstno;
    private String custNm;
    private String recNm;
    private String sexCd;
    private String birth;
    private String createAt;

}
