package com.example.task91p;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task91p.data.Advert;
import com.example.task91p.data.DatabaseHelper;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Advert> advertList;
    private Context context;

    public RecyclerViewAdapter(List<Advert> advertList, Context context){
        this.advertList = advertList;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.advert_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        //Assign holders
        Advert advert = advertList.get(position);
        holder.advert = advert;
        holder.advertText.setText(advert.getType() + " " + advert.getName());
        holder.id = advertList.get(position).getID();

    }

    @Override
    public int getItemCount() {
        return advertList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView advertText;
        Advert advert;
        Integer id;
        DatabaseHelper db;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variables
            advertText = itemView.findViewById(R.id.advertText);
            db = new DatabaseHelper(context);


            //Assign on click listener for the edit button in each item
            itemView.findViewById(R.id.advertCard).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //Open the Edit Advert activity and push the information from the item selected
                    Intent intent = new Intent(context, AdvertPopup.class);
                    Log.d("ISSUE", "BEFORE PUTTING ID");
                    intent.putExtra("ID", id);
                    Log.d("ISSUE", "AFTER PUTTING ID");
                    context.startActivity(intent);
                }
            });

        }
    }

    //DATE METHODS
    //Create a string for the date
    private String makeDateString(int year, int month, int day)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    };

    //Convert month to letters
    private String getMonthFormat(int month)
    {
        switch (month)
        {
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "OCT";
            case 10:
                return "SEP";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
            default:
                return "JAN";
        }

    };
}
