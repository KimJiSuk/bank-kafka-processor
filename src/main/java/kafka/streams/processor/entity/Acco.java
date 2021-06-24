package kafka.streams.processor.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
public class Acco {

    @Id
    @Column(name = "acno")
    private String acno;

    @Column(name = "cstno")
    private String cstno;

    @Column(name = "gds_nm")
    private String gdsNm;

    @Column(name = "link_acno")
    private String linkAcno;

}
