package kafka.streams.processor.topic;

import lombok.Getter;

@Getter
public class Account {
    public Account(String account) {
        String[] accounts = account.split(",", -1);

        this.acno = accounts[1];
        this.cstno = accounts[3];
        this.gdsNm = accounts[4];
        this.linkAcno = "";
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

    private final String acno;
    private final String cstno;
    private final String gdsNm;
    private final String linkAcno;
    private final String createAt;

    @Override
    public String toString() {
        return "Account{" +
                "acno='" + acno + '\'' +
                ", cstno='" + cstno + '\'' +
                ", gdsNm='" + gdsNm + '\'' +
                ", linkAcno='" + linkAcno + '\'' +
                ", createAt='" + createAt + '\'' +
                '}';
    }
}
