package com.example.videocall.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.videocall.Network.ApiClient;
import com.example.videocall.Network.ApiService;
import com.example.videocall.R;
import com.example.videocall.model.User;
import com.example.videocall.utilities.Constants;
import com.example.videocall.utilities.PreferenceManager;
import com.google.firebase.installations.FirebaseInstallations;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Outgoing2Activity extends AppCompatActivity {

    private PreferenceManager preferenceManager;
    private String inviterToken=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outgoing2);

        preferenceManager=new PreferenceManager(getApplicationContext());

        FirebaseInstallations.getInstance().getId().addOnCompleteListener(task -> {
            if(task.isSuccessful() && task.getResult()!=null){
                inviterToken=task.getResult();
            }
        });


        ImageView imageMeetingType=findViewById(R.id.imageMeetingType);
        String meetingType= getIntent().getStringExtra("type");

        if(meetingType!=null){
            if(meetingType.equals("video")){
                imageMeetingType.setImageResource(R.drawable.videocam_24);
            }
        }
        TextView textFirstChar=findViewById(R.id.textFirstChar);
        TextView textUserName=findViewById(R.id.textUserName);
        TextView textEmail=findViewById(R.id.textEmail);

        User user=(User) getIntent().getSerializableExtra("user");
        if(user!=null){
            textFirstChar.setText(user.firstName.substring(0,1));
            textUserName.setText(String.format("%s %s",user.firstName,user.LastName));
            textEmail.setText(user.email);
        }

        ImageView imageStopInvitation=findViewById(R.id.imageStopInvitation);
        imageStopInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if(meetingType!=null && user!=null){
            initiateMeeting(meetingType,user.token);
        }
    }
    private void initiateMeeting(String meetingType,String receiveToken){
        try {
            JSONArray tokens=new JSONArray();
            tokens.put(receiveToken);

            JSONObject body=new JSONObject();
            JSONObject data=new JSONObject();

            data.put(Constants.REMOTE_MSG_TYPE,Constants.REMOTE_MSG_INVITATION);
            data.put(Constants.REMOTE_MSG_MEETING_TYPE,meetingType);
            data.put(Constants.KEY_FIRST_NAME,preferenceManager.getString(Constants.KEY_FIRST_NAME));
            data.put(Constants.KEY_EMAIL,preferenceManager.getString(Constants.KEY_EMAIL));
            data.put(Constants.REMOTE_MSG_INVITER_TOKEN,inviterToken);

            body.put(Constants.REMOTE_MSG_DATA,data);
            body.put(Constants.REMOTE_MSG_REGISTRATION_IDS,tokens);
            sendRemoteMessage(body.toString(),Constants.REMOTE_MSG_INVITATION );

        }catch (Exception exception){
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void sendRemoteMessage(String remoteMessageBody,String type){
        ApiClient.getClient().create(ApiService.class).sendRemoteMessage(
                Constants.getRemoteMessageHeader(),remoteMessageBody
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call,@NonNull Response<String> response) {
                if(response.isSuccessful()){
                    if(type.equals(Constants.REMOTE_MSG_INVITATION)){
                        Toast.makeText(Outgoing2Activity.this, "Invitation sent successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Outgoing2Activity.this, response.message(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call,@NonNull Throwable t) {
                Toast.makeText(Outgoing2Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}