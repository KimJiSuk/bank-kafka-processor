package kafka.streams.processor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Customer")
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
