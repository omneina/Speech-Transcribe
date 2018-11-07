package com.libyanelite.speechtotexttest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private static final String TAG ="OSAMA" ;
    private TextView mVoiceInputTv;
    private ImageButton mSpeakBtn;
    private SpeechRecognizer sr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());
        mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);
        mSpeakBtn = (ImageButton) findViewById(R.id.btnSpeak);
        mSpeakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });
    }

    class listener implements RecognitionListener
    {
        public void onReadyForSpeech(Bundle params)
        {
            Log.d(TAG, "onReadyForSpeech");
        }
        public void onBeginningOfSpeech()
        {
            Log.d(TAG, "onBeginningOfSpeech");
        }
        public void onRmsChanged(float rmsdB)
        {
            Log.d(TAG, "onRmsChanged");
        }
        public void onBufferReceived(byte[] buffer)
        {
            Log.d(TAG, "onBufferReceived");
        }
        public void onEndOfSpeech()
        {
            Log.d(TAG, "onEndofSpeech");
        }
        public void onError(int error)
        {
            Log.d(TAG,  "error " +  error);
            mVoiceInputTv.setText("error " + error);
        }
        public void onResults(Bundle results)
        {
            /*
            String str = new String();
            Log.d(TAG, "onResults " + results);
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); i++)
            {
                Log.d(TAG, "result " + data.get(i));
                str += data.get(i);
            }
            mVoiceInputTv.setText("results: "+String.valueOf(data.size()));
            */
        }
        public void onPartialResults(Bundle partialResults)
        {
            ArrayList data = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String word = (String) data.get(data.size() - 1);
            mVoiceInputTv.setText(word);

            Log.i("TEST", "partial_results: " + word);
        }
        public void onEvent(int eventType, Bundle params)
        {
            Log.d(TAG, "onEvent " + eventType);
        }
    }
    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-EG"/* Locale.UK  -Locale.getDefault() */);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,true);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
        sr.startListening(intent);

        }
    }




