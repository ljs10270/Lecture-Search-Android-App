package com.ljs10270.registeration;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CourseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseFragment newInstance(String param1, String param2) {
        CourseFragment fragment = new CourseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //강의목록이 나오는 부분
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //전공,교양 선택, 학과 선택을 위한 스피너, 어댑터 변수 선언
    private ArrayAdapter areaAdapter;
    private Spinner areaSpinner;
    private ArrayAdapter majorAdapter;
    private Spinner majorSpinner;
    private ArrayAdapter area2Adapter;
    private Spinner area2Spinner;

    //강의 리스트를 보여주기 위한 리스트, 어댑터 변수 선언
    private ListView courseListView;
    private CourseListAdapter adapter;
    private List<Course> courseList;

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        areaSpinner = (Spinner) getView().findViewById(R.id.areaSpinner);
        area2Spinner = (Spinner) getView().findViewById(R.id.area2Spinner);
        majorSpinner = (Spinner) getView().findViewById(R.id.majorSpinner);

        areaAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.area, android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(areaAdapter);

        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //첫 리스너 선택에 따라 두번째 리스너 선택이 달라진다.
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(areaSpinner.getSelectedItem().equals("전공")) {
                    area2Adapter = ArrayAdapter.createFromResource(getActivity(), R.array.areaMajor, android.R.layout.simple_spinner_dropdown_item);
                    area2Spinner.setAdapter(area2Adapter);
                }

                if(areaSpinner.getSelectedItem().equals("교양")) {
                    area2Adapter = ArrayAdapter.createFromResource(getActivity(), R.array.areaRefinement, android.R.layout.simple_spinner_dropdown_item);
                    area2Spinner.setAdapter(area2Adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        area2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //두번째 리스너 선택에 따라 세번째 리스너 선택이 달라진다.
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(area2Spinner.getSelectedItem().equals("항공학부")) {
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.major1, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }

                if(area2Spinner.getSelectedItem().equals("항공융합학부")) {
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.major2, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }

                if(area2Spinner.getSelectedItem().equals("보건학부")) {
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.major3, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }

                if(area2Spinner.getSelectedItem().equals("디자인-엔터미디어학부")) {
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.major4, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }

                if(area2Spinner.getSelectedItem().equals("해양-스포츠학부")) {
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.major5, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }

                if(area2Spinner.getSelectedItem().equals("융합교양학부")) {
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.major6, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }

                if(area2Spinner.getSelectedItem().equals("교양/교양필수")) {
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.lectureRefinement, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }

                if(area2Spinner.getSelectedItem().equals("OCU/Cyber")) {
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.lectureCyber, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        courseListView = (ListView)getView().findViewById(R.id.courseListView);
        courseList = new ArrayList<Course>();
        adapter = new CourseListAdapter(getContext().getApplicationContext(), courseList);
        courseListView.setAdapter(adapter);

        //강의검색 버튼 이벤트
        Button searchButton = (Button) getView().findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
                // 버튼을 누르면 php가 db를 연동해 조건에 맞는 강의들을 출력해주는 메서드가 호출된다.
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute() {

            try {
                target = "http://ljs10270.cafe24.com/CourseList.php?courseMajor=" + URLEncoder.encode(majorSpinner.getSelectedItem().toString(), "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //DB에서 강의리스트 데이터를 얻어옴
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream(); //넘어오는 결과값들을 받음
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); //버퍼리더에 인풋스트림의 내용일 읽을 수 있도록 담음
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null) { //temp에 버퍼리더에서 받아온 값을 한줄씩 넣음
                    stringBuilder.append(temp + "\n"); //스트링빌더에 받아온 temp 값을 한줄씩 추가
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        //업데이트
        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        //결과(응답)처리
        @Override
        public void onPostExecute(String result) {
            try {
                courseList.clear();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String lectureCode; //강의코드
                String major;   //학과
                String lectureName; //강의명
                String term;  //학기
                String grade;  //학년
                String gradePoint;  //학점

                while(count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    //object에 db에서 추출된 처음 0행의 애트리뷰트들의 값을 넣고 각 변수에 대입
                    lectureCode = object.getString("lectureCode"); //강의코드
                    major = object.getString("major");   //학과
                    lectureName = object.getString("lectureName"); //강의명
                    term = object.getString("term");  //학기
                    grade = object.getString("grade");  //학년
                    gradePoint = object.getString("gradePoint");  //학점
                    Course course = new Course(lectureCode, major, lectureName, term, grade, gradePoint);
                    courseList.add(course);
                    count++;
                }
                adapter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
