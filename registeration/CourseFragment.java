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

    //??????????????? ????????? ??????
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //??????,?????? ??????, ?????? ????????? ?????? ?????????, ????????? ?????? ??????
    private ArrayAdapter areaAdapter;
    private Spinner areaSpinner;
    private ArrayAdapter majorAdapter;
    private Spinner majorSpinner;
    private ArrayAdapter area2Adapter;
    private Spinner area2Spinner;

    //?????? ???????????? ???????????? ?????? ?????????, ????????? ?????? ??????
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

            //??? ????????? ????????? ?????? ????????? ????????? ????????? ????????????.
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(areaSpinner.getSelectedItem().equals("??????")) {
                    area2Adapter = ArrayAdapter.createFromResource(getActivity(), R.array.areaMajor, android.R.layout.simple_spinner_dropdown_item);
                    area2Spinner.setAdapter(area2Adapter);
                }

                if(areaSpinner.getSelectedItem().equals("??????")) {
                    area2Adapter = ArrayAdapter.createFromResource(getActivity(), R.array.areaRefinement, android.R.layout.simple_spinner_dropdown_item);
                    area2Spinner.setAdapter(area2Adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        area2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //????????? ????????? ????????? ?????? ????????? ????????? ????????? ????????????.
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(area2Spinner.getSelectedItem().equals("????????????")) {
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.major1, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }

                if(area2Spinner.getSelectedItem().equals("??????????????????")) {
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.major2, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }

                if(area2Spinner.getSelectedItem().equals("????????????")) {
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.major3, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }

                if(area2Spinner.getSelectedItem().equals("?????????-?????????????????????")) {
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.major4, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }

                if(area2Spinner.getSelectedItem().equals("??????-???????????????")) {
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.major5, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }

                if(area2Spinner.getSelectedItem().equals("??????????????????")) {
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.major6, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }

                if(area2Spinner.getSelectedItem().equals("??????/????????????")) {
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

        //???????????? ?????? ?????????
        Button searchButton = (Button) getView().findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
                // ????????? ????????? php??? db??? ????????? ????????? ?????? ???????????? ??????????????? ???????????? ????????????.
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

        //DB?????? ??????????????? ???????????? ?????????
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream(); //???????????? ??????????????? ??????
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); //??????????????? ?????????????????? ????????? ?????? ??? ????????? ??????
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null) { //temp??? ?????????????????? ????????? ?????? ????????? ??????
                    stringBuilder.append(temp + "\n"); //?????????????????? ????????? temp ?????? ????????? ??????
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

        //????????????
        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        //??????(??????)??????
        @Override
        public void onPostExecute(String result) {
            try {
                courseList.clear();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String lectureCode; //????????????
                String major;   //??????
                String lectureName; //?????????
                String term;  //??????
                String grade;  //??????
                String gradePoint;  //??????

                while(count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    //object??? db?????? ????????? ?????? 0?????? ????????????????????? ?????? ?????? ??? ????????? ??????
                    lectureCode = object.getString("lectureCode"); //????????????
                    major = object.getString("major");   //??????
                    lectureName = object.getString("lectureName"); //?????????
                    term = object.getString("term");  //??????
                    grade = object.getString("grade");  //??????
                    gradePoint = object.getString("gradePoint");  //??????
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
