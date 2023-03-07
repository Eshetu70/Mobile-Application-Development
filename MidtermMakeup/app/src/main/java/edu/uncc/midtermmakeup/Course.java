package edu.uncc.midtermmakeup;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Course implements Serializable {

    String course_id, course_name, course_number, letter_grade;
    double credit_hours;

    public Course() {
    }

    public Course(JSONObject  jsonObject) throws JSONException {

        this.course_id =jsonObject.getString("course_id");
        this.course_name =jsonObject.getString("course_name");
        this.course_number =jsonObject.getString("course_number");
        this.credit_hours =jsonObject.getDouble("credit_hours");
        this.letter_grade =jsonObject.getString("letter_grade");
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_number() {
        return course_number;
    }

    public void setCourse_number(String course_number) {
        this.course_number = course_number;
    }

    public Double getCredit_hours() {
        return credit_hours;
    }

    public void setCredit_hours(double credit_hours) {
        this.credit_hours = credit_hours;
    }

    public String getLetter_grade() {
        return letter_grade;
    }

    public void setLetter_grade(String letter_grade) {
        this.letter_grade = letter_grade;
    }
}


