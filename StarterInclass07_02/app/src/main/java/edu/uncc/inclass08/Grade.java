package edu.uncc.inclass08;

public class Grade {
    public  String docId,letterGrade,name,number,own_id;
    public  double credit;

    public Grade() {
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOwn_id() {
        return own_id;
    }

    public void setOwn_id(String own_id) {
        this.own_id = own_id;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "docId='" + docId + '\'' +
                ", letterGrade='" + letterGrade + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", own_id='" + own_id + '\'' +
                ", credit=" + credit +
                '}';
    }
}
