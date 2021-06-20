package com.ljs10270.registeration;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CourseListAdapter extends BaseAdapter {

    private Context context;
    private List<Course> courseList;

    public CourseListAdapter(Context context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int i) {
        return courseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.course, null);
        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseGradePoint = (TextView) v.findViewById(R.id.courseGradePoint);
        TextView courseTerm = (TextView) v.findViewById(R.id.courseTerm);
        TextView courseMajor = (TextView) v.findViewById(R.id.courseMajor);
        TextView courseCode = (TextView) v.findViewById(R.id.courseCode);

        //db에서 가져와 사용자에게 보여주기 위해 설정 후 리턴 - 프레그먼트로 가서 파싱해야 보인다.
        courseGrade.setText(courseList.get(i).getGrade());
        courseTitle.setText(courseList.get(i).getLectureName());
        courseGradePoint.setText(courseList.get(i).getGradePoint());
        courseTerm.setText(courseList.get(i).getTerm());
        courseMajor.setText(courseList.get(i).getMajor());
        courseCode.setText(courseList.get(i).getLectureCode());

        v.setTag(courseList.get(i).getLectureCode());
        return v;
    }
}
