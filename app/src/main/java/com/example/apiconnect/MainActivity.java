package com.example.apiconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.speech.RecognitionListener;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    SpeechRecognizer speechRecognizer;
    final int PERMISSION = 1; //퍼미션 변수

    boolean recording = false; //현재 녹음중인지 여부
    Button btn1;
    EditText edit1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckPermission(); //녹음 퍼미션 체크

        //UI
        btn1=findViewById(R.id.btn1);
        edit1=findViewById(R.id.edit1);

        //버튼 클릭 이벤트 리스너 등록
//        btn1.setOnClickListener(click);

        //RecognizerIntent 객체 생성
        intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");


    }

//    View.OnClickListener click = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            switch (view.getID()) {
//                //녹음버튼
//                case R.id.btn1:
//                    if (!recording) { //녹음 시작
//                        StartRecord();
//                        Toast.makeText(getApplicationContext(), "지금부터 음성으로" +
//                                "기록합니다.", Toast.LENGTH_SHORT).show();
//                    }
//                    else { //이미 녹음 중이면 녹음 중지
//                        StopRecord();
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           if(!recording) {
               StartRecord();
               Toast.makeText(getApplicationContext(), "지금부터 음성으로 기록합니다.", Toast.LENGTH_SHORT).show();
           }
           else {
               StopRecord();
           }
        }
    };


    void CheckPermission() {
        //안드로이드 버전이 6.0 이상
        if(Build.VERSION.SDK_INT >= 23) {
            //인터넷이나 녹음 권한이 없으면 권한 요청
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
            == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET,
                        Manifest.permission.RECORD_AUDIO}, PERMISSION);
            }
        }
    }

    //녹음 시작
    void StartRecord() {
        recording = true;

        speechRecognizer=SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        speechRecognizer.setRecognitionListener(listener);
        speechRecognizer.startListening(intent);
    }

    //녹음 중지
    void StopRecord() {
        recording = false;

        speechRecognizer.stopListening(); //녹음중지
        Toast.makeText(getApplicationContext(), "음성 기록을 중지합니다", Toast.LENGTH_SHORT).show();

    }

    RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float rmsdB) {

        }

        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int error) {

        }

        @Override
        public void onResults(Bundle results) {
            ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);    //인식 결과를 담은 ArrayList
            String originText = edit1.getText().toString();  //기존 text

            //인식 결과
            String newText="";
            for (int i = 0; i < matches.size() ; i++) {
                newText += matches.get(i);
            }

            edit1.setText(originText + newText + " ");   //기존의 text에 인식 결과를 이어붙임
            speechRecognizer.startListening(intent);    //녹음버튼을 누를 때까지 계속 녹음해야 하므로 녹음 재개

        }

        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    }


}