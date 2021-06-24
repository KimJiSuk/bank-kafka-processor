package kafka.streams.processor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Tran")
@IdClass(TranPK.class)
public class Tran {

    @Id
    @Column(name = "acno")
    private String acno;

    @Id
    @Column(name = "seqno")
    private String seqno;

    @Column(name = "reg_dttm")
    private LocalDateTime regDttm;

    @Column(name = "tx_chnl")
    private String txChnl;

    @Column(name = "aftr_bal")
    private String aftrBal;

    @Column(name = "atm_cd")
    private String atmCd;

    @Column(name = "auto_cycl")
    private String autoCycl;

    @Column(name = "recv_nm")
    private String recvNm;
}
