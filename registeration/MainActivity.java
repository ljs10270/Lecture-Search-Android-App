package com.ljs10270.registeration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//로그인 후 들어가는 메인 액티비티
public class MainActivity extends AppCompatActivity {

    private ListView noticeListView;
    private  NoticeListAdapter adapter;
    private List<Notice> noticeList; //공지사항 요소들을 담을 리스트

    // 로그인 액션에서 인텐트로 아이디를 추가 함( intent.putExtra(네임,변수) )
    // 나중에 가져와서 쓸거면 변수 선언하고 getIntent().getStringExtra(네임) 하기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //스마트폰 화면 고정

        noticeListView = (ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        adapter = new NoticeListAdapter(getApplicationContext(), noticeList); // 어댑터에 공지사항 리스트 내용들이 들어감
        noticeListView.setAdapter(adapter); //리스트 뷰에 어댑터에 들어간 내용(공지사항)들이 매칭되어 출력

        final Button courseButton = (Button) findViewById(R.id.courseButton);
        final LinearLayout notice = (LinearLayout) findViewById(R.id.notice);

        //강의검색 버튼을 눌렀을 시 이벤트 처리
        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE); //공지사항이 안보이도록 설정
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark)); //클릭 상태로 색상 변경
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new CourseFragment()); //프레그먼트 변경
                fragmentTransaction.commit();//실행
            }
        });
        new BackgroundTask().execute();
    }

   //php파일을 통해 공지사항 DB에서 업로드 하는 클래스
   class BackgroundTask extends AsyncTask<Void, Void, String>
   {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://ljs10270.cafe24.com/NoticeList.php";
        }

        //DB에서 공지사항 데이터를 얻어옴
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
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String noticeContent, noticeName, noticeDate;
                while(count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    //db에서 받아온 튜플 0행을 object에 대입하고 각 애트리뷰트의 값을 변수마다 대입
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");
                    Notice notice = new Notice(noticeContent,noticeName,noticeDate);
                    noticeList.add(notice); // 리스트에 추가
                    count++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
       }
   }

    //뒤로가기 버튼을 1.5초안에 두번 누르면 종료되는 기능 추가
    private long lastTimeBackPressed;

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            finish();
            return;
        }
        Toast.makeText(this, "'뒤로' 버튼을 한 번 더 누르셔서 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }

}
