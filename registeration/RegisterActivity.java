package com.ljs10270.registeration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

//회원가입 동작
public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner spinner;
    private String userID;
    private String userPassword;
    private String userGender;
    private String userMajor;
    private String userEmail;
    private AlertDialog dialog; //알림창
    private boolean validate = false; //사용가능한 아이디인지 체크

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinner = (Spinner) findViewById(R.id.majorSpinner); //레이아웃의 id가 majorSpinner인 것을 가져옴
        // adapter에 arrays.xml의 학과들을 가져옴
        adapter = ArrayAdapter.createFromResource(this, R.array.major, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter); //스피너에 adapter 추가

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText emailText = (EditText) findViewById(R.id.emailText);

        RadioGroup genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        int genderGroupID = genderGroup.getCheckedRadioButtonId();
        //남자인지 여자인지 체크하면 userGender에 담긴다.
        userGender = ((RadioButton) findViewById(genderGroupID)).getText().toString();

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton genderButton = (RadioButton) findViewById(i); //현재 체크되어 있는 젠더 버튼
                userGender = genderButton.getText().toString();
            }
        });
        //중복 버튼 동작
        final Button validateButton = (Button) findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString(); //버튼을 클릭했을 떄 중복체크 진행
                if(validate) //중복체크가 되어 있다면
                {
                    return; //그냥 종료
                }
                if(userID.equals("")) //아이디 입력란에 아무런 내용이 없다면
                {
                    //경고창 출력 (아이디는 빈공간으로 두고 회원가입 불가)
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디는 빈 칸일 수 없습니다.")
                            .setPositiveButton("확인", null)
                            .create(); //다이어로그 창에 생성
                    dialog.show(); //다이어로그 창 실행
                    return;
                }
                //정상적으로 아이디 입력칸에 공백이 아닌 아이디를 적었다면 중복체크 진행
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response); //json응답 받기
                            boolean success = jsonResponse.getBoolean("success"); //리스폰이 정상적으로 수행되었는지
                            if(success) {//사용할 수 있는 아이디라면
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인", null)
                                        .create(); //다이어로그 창에 생성
                                dialog.show(); //다이어로그 창 실행
                                idText.setEnabled(false); //중복체크를 성공적으로 진행했으면 아이디 수정 불가
                                validate = true; //중복체크 완료
                                idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            }
                            else { // 중복된 아이디가 있다면
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("이미 존재하는 아이디 입니다.")
                                        .setNegativeButton("확인", null)
                                        .create(); //다이어로그 창에 생성
                                dialog.show(); //다이어로그 창 실행
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                //리퀘스트를 다른 액티비티로 보낼 수 있도록 큐를 생성
                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });

        //회원가입 버튼 동작
        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userMajor = spinner.getSelectedItem().toString();
                String userEmail = emailText.getText().toString();

                if(!validate) { //회원가입 버튼을 눌렀는데 아이디 중복체크가 되어 있지 않다면
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디 중복 체크를 해주세요.")
                            .setNegativeButton("확인", null)
                            .create(); //다이어로그 창에 생성
                    dialog.show(); //다이어로그 창 실행
                    return;
                }

                //회원가입시 하나라도 빈 칸이 있다면
                if(userID.equals("") || userPassword.equals("") || userMajor.equals("")
                        || userEmail.equals("") || userGender.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create(); //다이어로그 창에 생성
                    dialog.show(); //다이어로그 창 실행
                    return;
                }

                // 회원가입에 성공한 경우
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response); //json응답 받기
                            boolean success = jsonResponse.getBoolean("success"); //리스폰이 정상적으로 수행되었는지
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("회원가입이 완료 되었습니다..")
                                        .setPositiveButton("확인", null)
                                        .create(); //다이어로그 창에 생성
                                dialog.show(); //다이어로그 창 실행
                                finish(); //회원가입 등록 창 닫음
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("회원가입에 실패 하였습니다.")
                                        .setNegativeButton("확인", null)
                                        .create(); //다이어로그 창에 생성
                                dialog.show(); //다이어로그 창 실행
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                //리퀘스트를 다른 액티비티로 보낼 수 있도록 큐를 생성
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userGender, userMajor, userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }

    @Override
    protected void onStop() { //회원가입 액티비티가 꺼지게 되면
        super.onStop();
        if(dialog != null) //액티비티가 꺼졌는데 다이어로그가 널이 아니면
        {
            dialog.dismiss(); //다이어로그 종료
            dialog = null;
        }
    }
}
