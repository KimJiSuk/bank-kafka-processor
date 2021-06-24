package kafka.streams.processor.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TranPK implements Serializable {
    private String acno;
    private String seqno;
}
