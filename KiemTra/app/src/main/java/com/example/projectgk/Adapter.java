package com.example.projectgk;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.bumptech.glide.Glide;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    ArrayList<ListItem> itemList;

    public Adapter(Context context, ArrayList<ListItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Collections.sort(itemList, new Comparator<ListItem>() {
            @Override
            public int compare(ListItem user1, ListItem user2) {
                return user1.getFullName().compareTo(user2.getFullName());
            }
        });
        ListItem item = itemList.get(position);
        holder.name.setText(item.getFullName());
        holder.number.setText(item.getPhoneNumber());
        Glide.with(context)
                .load(item.getProfilePicture())
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(holder.img);
        holder.header.setText(item.getFullName().substring(0,1));
        if(position > 0){
            int i = position - 1;
            if (i < itemList.size() && item.getFullName().substring(0,1).equals(itemList.get(i).getFullName().substring(0,1))){
                holder.cardHeader.setVisibility(View.GONE);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (item instanceof Employee) {
                    intent = new Intent(context, EmployeeDetailActivity.class);
                    Employee employee = (Employee) item;
                    intent.putExtra("employeeId", employee.getEmployeeId());
                    intent.putExtra("position", employee.getPosition());
                    intent.putExtra("email", employee.getEmail());
                    intent.putExtra("unitId", employee.getUnitId());
                } else {
                    intent = new Intent(context, UnitDetailActivity.class);
                    Unit unit = (Unit) item;
                    intent.putExtra("unitId", unit.getUnitId());
                    intent.putExtra("email", unit.getEmail());
                    intent.putExtra("website", unit.getWebsite());
                    intent.putExtra("address", unit.getAddress());
                    intent.putExtra("parentUnitId", unit.getParentUnitId());
                }
                intent.putExtra("fullName", item.getFullName());
                intent.putExtra("phoneNumber", item.getPhoneNumber());
                intent.putExtra("profilePicture", item.getProfilePicture());
                context.startActivity(intent);
            }
        });
    }

    public void filterAdapter(List<ListItem> filterList){
        itemList = (ArrayList<ListItem>) filterList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, number, header;
        CardView cardHeader;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            number = itemView.findViewById(R.id.itemNumber);
            img = itemView.findViewById(R.id.itemImage);
            header = itemView.findViewById(R.id.itemHeader);
            cardHeader = itemView.findViewById(R.id.cardHeader);
        }
    }
}
