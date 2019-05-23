package com.training.myassistantapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.training.myassistantapp.Listener.OnRecyclerItemClickListener;
import com.training.myassistantapp.R;
import com.training.myassistantapp.model.User;

import java.util.ArrayList;

public class UserAdapter  extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;
    int resource;
    ArrayList<User> objects;

    User user;

    OnRecyclerItemClickListener recyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener recyclerItemClickListener){
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    public UserAdapter(Context context, int resource, ArrayList<User> objects) {
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public UserAdapter .ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(resource,parent,false);
        final UserAdapter.ViewHolder holder = new UserAdapter.ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerItemClickListener.onItemClick(holder.getAdapterPosition());
            }
        });

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        User user = objects.get(position);

        // Extracting Data from News Object and Setting the data on list_item
        holder.txtTitle.setText(user.name);
        holder.txtEMAIL.setText(user.email);
        holder.txtADDRESS.setText(user.phone);


    }


    @Override
    public int getItemCount() {
        return objects.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        // Attributes of ViewHolder
        TextView txtTitle;
        TextView txtEMAIL;
        TextView txtADDRESS;


        public ViewHolder(View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.textViewTitle);
            txtEMAIL = itemView.findViewById(R.id.textViewEMAIL);
            txtADDRESS = itemView.findViewById(R.id.textViewADDRESS);


        }
    }

}

