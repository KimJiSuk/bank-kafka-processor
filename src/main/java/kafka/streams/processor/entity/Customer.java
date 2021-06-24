package kafka.streams.processor.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
public class Customer {

    @Id
    @Column(name = "cstno")
    private String cstno;

    @Column(name = "cust_nm")
    private String custNm;

    @Column(name = "rec_nm")
    private String recNm;

    @Column(name = "sex_cd")
    private String sexCd;

    @Column(name = "birth")
    private String birth;
}
