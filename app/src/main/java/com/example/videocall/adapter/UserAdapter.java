package com.example.videocall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videocall.R;
import com.example.videocall.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private List<User> users;

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_user,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView textFirstChar,textUserName,textEmail;
        ImageView imageCallMeeting,imageVideoMeeting;

      UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textFirstChar=itemView.findViewById(R.id.textFirstChar);
            textUserName=itemView.findViewById(R.id.textUserName);
            textEmail=itemView.findViewById(R.id.textEmail);
            imageCallMeeting=itemView.findViewById(R.id.imageCallMeeting);
            imageVideoMeeting=itemView.findViewById(R.id.imageVideoMeeting);
        }

        void setUserData(User user){
          textFirstChar.setText(user.firstName.substring(0,1));
          textUserName.setText(String.format("%s %s",user.firstName,user.LastName));
          textEmail.setText(user.email);
        }

    }
}
