public class database {
    private String SSN;
    private String fname;
    private String lname;
    private String address;
    private String state;
    private String city;
    private String zipcode;
    private boolean hasBackUpAcc;

    public database() {
        //int the variables
        SSN = "";
        fname = "zzhang";
        lname = "wwei";
        address = "";
        city = "";
        zipcode = "";
        state = "";
        hasBackUpAcc = false;
    }

    public database(String SSN, String fName, String lname, String address,String city,String state,String zipcode) {
        this.SSN = SSN;
        this.lname = lname;
        fname = fName;
        this.zipcode = zipcode;
        this.address=address;
        this.city=city;
        this.state=state;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public boolean isHasBackUpAcc() {
        return hasBackUpAcc;
    }

    public void setHasBackUpAcc(boolean hasBackUpAcc) {
        this.hasBackUpAcc = hasBackUpAcc;
    }
}