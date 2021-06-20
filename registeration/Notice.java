package com.ljs10270.registeration;

//공지사항에 들어가는 내용
public class Notice {
    String notice; //공지사항 내용
    String name; //공지사항 작성자 이름
    String date; //작성 날짜

    public Notice(String notice, String name, String date) {
        this.notice = notice;
        this.name = name;
        this.date = date;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
