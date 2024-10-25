package com.example.mepass;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mepass.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<DataClass> dataList;

    private Context context;
    public static class MyViewHolder extends RecyclerView.ViewHolder {


        TextView email, password, website, user;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.emailR);
            password = itemView.findViewById(R.id.passwordR);
            website = itemView.findViewById(R.id.websiteR);
            imageView = itemView.findViewById(R.id.imageButton);

        }




    }



    public MyAdapter(Context context, ArrayList<DataClass> dataList) {

        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Glide.with(context).load(dataList.get(position).getImageURL()).into(holder.recyclerImage);
        int i =0;
        String username = dataList.get(position).getUser();
        String email = dataList.get(position).getEmail();
        String website = dataList.get(position).getWebsite();
        String password = dataList.get(position).getPassword();


            holder.email.setText(dataList.get(position).getEmail());
            holder.password.setText(dataList.get(position).getPassword());
            holder.website.setText(dataList.get(position).getWebsite());
            holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu menu = new PopupMenu(v.getContext(), v);
                menu.inflate(R.menu.popup);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference ref = db.getReference();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userId = user.getUid();
                String websiteCleansed = website.replace(".","");

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.popup_edit:
                                Intent intent = new Intent(context, EditActivity.class);
                                Bundle extras = new Bundle();
                                extras.putString("user", username);
                                extras.putString("email", email);
                                extras.putString("pass", password);
                                extras.putString("site", website);
                                intent.putExtras(extras);
                                context.startActivity(intent);
                                return true;
                            case R.id.popup_del:


                                ref.child("users").child(userId).child(websiteCleansed).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(context, "Credentials Deleted Succesfully", Toast.LENGTH_SHORT).show();
                                            //finish();
                                        } else {
                                            Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                menu.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


}