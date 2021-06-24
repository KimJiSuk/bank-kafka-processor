package kafka.streams.processor.topic;

import kafka.streams.processor.entity.Acco;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Account {

    private static final String EMPTY_STRING = "";

    public Account(String account) {
        String[] accounts = account.split(",", -1);

        this.acno = accounts[1];
        this.cstno = accounts[3];
        this.gdsNm = accounts[4];
        this.linkAcno = EMPTY_STRING;
        this.createAt = accounts[2];
    }

    public Account(String account, String safeBox) {
        String[] accounts = account.split(",", -1);
        String[] safeBoxes = safeBox.split(",", -1);

        this.acno = accounts[1];
        this.cstno = accounts[3];
        this.gdsNm = accounts[4];
        this.linkAcno = safeBoxes[3];
        this.createAt = accounts[2];
    }

    private String acno;
    private String cstno;
    private String gdsNm;
    private String linkAcno;
    private String createAt;

    public Acco toEntity() {
        return Acco.builder()
                .acno(this.acno)
                .cstno(this.cstno)
                .gdsNm(this.gdsNm)
                .linkAcno(this.linkAcno)
                .build();
    }
}
