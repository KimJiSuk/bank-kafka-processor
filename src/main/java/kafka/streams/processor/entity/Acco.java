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
@Table(name = "Acco")
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
