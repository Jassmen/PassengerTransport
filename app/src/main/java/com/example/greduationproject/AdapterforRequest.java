package com.example.greduationproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greduationproject.login_tasks.models.Request;
import com.example.greduationproject.login_tasks.models.users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterforRequest extends RecyclerView.Adapter<AdapterforRequest.ViewerHolder> {
    interface OnAcceptclickList{
        void onClickRequest(int pos);
    }





    OnAcceptclickList onAcceptclickLis;
     Context context;
    ArrayList<Request> usersrequest;
    public AdapterforRequest(Context context, ArrayList<Request> requests) {
        this.context = context;
        this.usersrequest = requests;
    }
    @NonNull
    @Override
    public ViewerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewerHolder(LayoutInflater.from(context).inflate(R.layout.item_row_request, parent, false),onAcceptclickLis);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewerHolder holder, int position) {
        holder.fullname.setText("Name: "+usersrequest.get(position).getName_users());
        holder.phone.setText("Phone: "+usersrequest.get(position).getPhone_users());
        Picasso.get().load(usersrequest.get(position).getImage_users()).into(holder.profile_image);
    }


public void onClickAccept(OnAcceptclickList onAcceptclickLis){
        this.onAcceptclickLis=onAcceptclickLis;
}



    @Override
    public int getItemCount() {
        return usersrequest.size();
    }

    public class ViewerHolder extends RecyclerView.ViewHolder {
        TextView fullname,phone;
        ImageView profile_image;
        Button btn_accept;
        public ViewerHolder(@NonNull View itemView, final OnAcceptclickList onAcceptclickLis) {
            super(itemView);
            fullname=itemView.findViewById(R.id.text_name);
            phone=itemView.findViewById(R.id.text_phone);
            profile_image=itemView.findViewById(R.id.image_user);
            btn_accept=itemView.findViewById(R.id.btnaccept);
            btn_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAcceptclickLis.onClickRequest(getAdapterPosition());
                }
            });

        }
    }
}


