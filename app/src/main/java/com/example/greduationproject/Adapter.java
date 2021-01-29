package com.example.greduationproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greduationproject.login_tasks.models.Driver;
import com.example.greduationproject.login_tasks.models.select_driver_travel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewerHolder> {
    Context context;
    ArrayList<Driver> drivertravel;

    List<select_driver_travel> seletravel;
    OnInfoClick onInfoClick;

    // ArrayList<Driver.ct> drivertravel1;
    interface OnInfoClick {
        void onClick(int pos);
        void requestOnClick( int pos);
    }

    public Adapter() {
    }

    public Adapter(Context context, ArrayList<Driver> drivers, List<select_driver_travel> seletravel) {
        this.context = context;
        this.drivertravel = drivers;
        this.seletravel=seletravel;
        //drivertravel1=drivertravels;
    }

    public void onItemClick(OnInfoClick onClick) {
        this.onInfoClick = onClick;
    }

    @NonNull
    @Override
    public ViewerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewerHolder(LayoutInflater.from(context).inflate(R.layout.item_row, parent, false), onInfoClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewerHolder holder, int position) {

            holder.fullname.setText(drivertravel.get(position).getFullname());
            //holder.email.setText(drivertravel.get(position).getEmail());
            holder.car_name.setText("Car Name: "+drivertravel.get(position).getCarName());
            holder.model_car.setText("Car model: "+drivertravel.get(position).getModel());
            String from=seletravel.get(position).getFrom();
            String to=seletravel.get(position).getTo();
            String time=seletravel.get(position).getTime();
            String price=seletravel.get(position).getPricetravel();

            holder.from_travel.setText("From: "+from);
            holder.to_travel.setText("To: "+to);
            holder.time.setText("Time: "+time);

           holder.priceoftravel.setText("Price:"+price);

            // Picasso.get().load(drivertravel.get(position).getUrl_img_crim()).into(holder.url_img_crim);
            //Picasso.get().load(R.drawable.profile).into(holder.url_img);

            Picasso.get().load(drivertravel.get(position).getUrl_img()).into(holder.url_img);




    }

    @Override
    public int getItemCount() {
        return drivertravel.size();
    }

    static class ViewerHolder extends RecyclerView.ViewHolder {

        TextView fullname, email, time, car_name, model_car,from_travel,to_travel,priceoftravel;
        ImageView url_img, url_img_crim;
        Button btnInfo,btnrequest;

        public ViewerHolder(@NonNull View itemView, final OnInfoClick onInfoClick) {
            super(itemView);
            fullname = itemView.findViewById(R.id.person_name);
            email = itemView.findViewById(R.id.person_email);
            url_img = itemView.findViewById(R.id.profile_pic);
            time = itemView.findViewById(R.id.time_travel);
            car_name = itemView.findViewById(R.id.carname);
            model_car = itemView.findViewById(R.id.modelofcar);
            from_travel=itemView.findViewById(R.id.from);
            to_travel=itemView.findViewById(R.id.to);
            priceoftravel=itemView.findViewById(R.id.travelprice11);
            btnInfo = itemView.findViewById(R.id.btnaccept);
            btnrequest=itemView.findViewById(R.id.requst_btn);
            btnInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onInfoClick.onClick(getAdapterPosition());
                }
            });
            btnrequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onInfoClick.requestOnClick(getAdapterPosition());
                }
            });



        }



    }

}
