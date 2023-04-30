package com.example.videocall.listner;

import com.example.videocall.model.User;

public interface UserListener {
    void initiateVideoMeeting(User user);
    void initiateAudioMeeting(User user);
}
