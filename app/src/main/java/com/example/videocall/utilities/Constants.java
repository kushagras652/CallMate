package com.example.videocall.utilities;

import java.util.HashMap;

public class Constants {
    public static final String KEY_COLLECTION_USERS="users";
    public static final String KEY_FIRST_NAME="first_name";
    public static final String KEY_LAST_NAME="last_name";
    public static final String KEY_EMAIL="email";
    public static final String KEY_PASSWORD="password";

    public static  final String KEY_USER_ID="user_id";
    public static final String KEY_FCM_TOKEN="fcm_token";

    public static final String KEY_PREFERENCE_NAME="callMatePreference";
    public static final String KEY_IS_SIGNED_IN="isSigned";
    public static final String REMOTE_MSG_TYPE="type";
    public static final String REMOTE_MSG_INVITATION="invitation";
    public static final String REMOTE_MSG_MEETING_TYPE="meetingType";
    public static final String REMOTE_MSG_INVITER_TOKEN="inviterToken";
    public static final String REMOTE_MSG_DATA="data";
    public static final String REMOTE_MSG_REGISTRATION_IDS="registration_ids";

    public static final String REMOTE_MSG_AUTHORIZATION="Authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE="Content-Type";

    public static HashMap<String,String> getRemoteMessageHeader(){
        HashMap<String,String> headers=new HashMap<>();
        headers.put(
                Constants.REMOTE_MSG_AUTHORIZATION,
                "AAAA8T6MpX4:APA91bGSKlYfTFgyAQJ6THpu9H9c1uylIQAmwo2X-zJgfqDagW3_VjWvgssAk48-sdKTbArwb1m8u4E9-aZEF2YnsoZys2xQlIC3zaYkcxRVk2Hy5Lt5gw0HgPQWjffQjPfdn0hdrjF0"
        );
        headers.put(
                Constants.REMOTE_MSG_CONTENT_TYPE,"application/json"
        );
        return headers;
    }
}
