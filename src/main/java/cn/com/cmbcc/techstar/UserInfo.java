package cn.com.cmbcc.techstar;

public class UserInfo {
    private long ID;
    private String Person;
    private String mobile;
    private String Addr;
    private String encrymobile;

    public String getEncrymobile() {
        return encrymobile;
    }

    public void setEncrymobile(String encrymobile) {
        this.encrymobile = encrymobile;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getPerson() {
        return Person;
    }

    public void setPerson(String person) {
        Person = person;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddr() {
        return Addr;
    }

    public void setAddr(String addr) {
        Addr = addr;
    }
}
