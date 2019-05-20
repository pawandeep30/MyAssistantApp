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
import com.training.myassistantapp.VehicleRegistration;
import com.training.myassistantapp.model.User;

import java.util.ArrayList;

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.ViewHolder> {

    Context context;
    int resource;
   //ArrayList<User> objects;
  ArrayList<VehicleRegistration> objects;

    VehicleRegistration vehicleRegistration;

    OnRecyclerItemClickListener recyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener recyclerItemClickListener){
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    public CustomersAdapter(Context context, int resource, ArrayList<VehicleRegistration> objects) {
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public CustomersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(resource,parent,false);
        final CustomersAdapter.ViewHolder holder = new CustomersAdapter.ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerItemClickListener.onItemClick(holder.getAdapterPosition());
            }
        });

        return holder;
    }


    // onBindViewHolder will be execute n number of time from 0 to n-1 when n is the count whiwh we are returning
    @Override
    public void onBindViewHolder(@NonNull CustomersAdapter.ViewHolder holder, int position) {

       // User user = objects.get(position);
       VehicleRegistration  vehicleRegistration = objects.get(position);

    // vehicleRegistration = new VehicleRegistration();
        // Extracting Data from News Object and Setting the data on list_item
     //   holder.txtTitle.setText(user.name);
     //   holder.txtEMAIL.setText(user.email);
       // holder.txtADDRESS.setText(user.address);

        holder.txtTitle2.setText("Name: "+vehicleRegistration.Make);
        holder.txtMODEL.setText("Model: "+vehicleRegistration.Model);
        holder.txtYEAR.setText("Year: "+vehicleRegistration.Year);
        holder.txtCOLOR.setText("Color: "+vehicleRegistration.Color);

    }

    @Override
    public int getItemCount() {
        return objects.size(); // how many list items we wish to have in our recycler view
    }


    // Nested Class : ViewHolder to hold Views of list_item
    class ViewHolder extends RecyclerView.ViewHolder{

        // Attributes of ViewHolder
        TextView txtTitle;
        TextView txtEMAIL;
        TextView txtADDRESS;


        TextView txtTitle2;
        TextView txtMODEL;
        TextView txtYEAR;
        TextView txtCOLOR;



        public ViewHolder(View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.textViewTitle);
            txtEMAIL = itemView.findViewById(R.id.textViewEMAIL);
            txtADDRESS = itemView.findViewById(R.id.textViewADDRESS);


            txtTitle2 = itemView.findViewById(R.id.textViewTitle2);
            txtMODEL = itemView.findViewById(R.id.textViewMODEL);
            txtYEAR = itemView.findViewById(R.id.textViewYEAR);
            txtCOLOR= itemView.findViewById(R.id.textViewCOLOR);
        }
    }

  }
