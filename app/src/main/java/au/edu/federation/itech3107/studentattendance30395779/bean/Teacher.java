package au.edu.federation.itech3107.studentattendance30395779.bean;

import org.litepal.crud.LitePalSupport;

public class Teacher extends LitePalSupport {

    private int teacher_id;
    private String teacher_name;
    private char teacher_sex;
    private float weight;
    private String teacher_pwd;

    public int getteacher_id() {
        return teacher_id;
    }

    public void setteacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getteacher_name() {
        return teacher_name;
    }

    public void setteacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public char getteacher_sex() {
        return teacher_sex;
    }

    public void setteacher_sex(char teacher_sex) {
        this.teacher_sex = teacher_sex;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getteacher_pwd() {
        return teacher_pwd;
    }

    public void setteacher_pwd(String teacher_pwd) {
        this.teacher_pwd = teacher_pwd;
    }
}
