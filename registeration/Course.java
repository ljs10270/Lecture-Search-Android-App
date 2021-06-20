package com.ljs10270.registeration;

public class Course {  //jsp의 자바빈즈와 같은 역할을 함, db에서 가져온 값을 변수에 대입에 화면에 뿌려줌
    String lectureCode; //강의코드
    String major;   //학과
    String lectureName; //강의명
    String term;  //학기
    String grade;  //학년
    String gradePoint;  //학점

    public String getLectureCode() {
        return lectureCode;
    }

    public void setLectureCode(String lectureCode) {
        this.lectureCode = lectureCode;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }


    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(String gradePoint) {
        this.gradePoint = gradePoint;
    }

    public Course(String lectureCode, String major, String lectureName, String term, String grade, String gradePoint) {
        this.lectureCode = lectureCode;
        this.major = major;
        this.lectureName = lectureName;
        this.term = term;
        this.grade = grade;
        this.gradePoint = gradePoint;
    }
}
