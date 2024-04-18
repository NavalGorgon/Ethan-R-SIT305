package com.example.task41p;

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

import com.example.task41p.data.DatabaseHelper;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Task> taskList;
    private Context context;

    public RecyclerViewAdapter(List<Task> taskList, Context context){
        this.taskList = taskList;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.task_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        Task task = taskList.get(position);
        holder.taskTitle.setText(taskList.get(position).getTitle());
        //Convert date to string
        holder.taskDate.setText(taskList.get(position).getDate());
        holder.task = task;
        holder.editButton.setText("Edit");

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle;
        TextView taskDate;
        Button editButton;
        Task task;
        DatabaseHelper db;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.taskTitle);
            taskDate = itemView.findViewById(R.id.taskDate);
            editButton = itemView.findViewById(R.id.button2);
            db = new DatabaseHelper(context);


            Log.wtf("here", "Here");

            itemView.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditTask.class);
                    intent.putExtra("Title", task.getTitle());
                    intent.putExtra("Details", task.getDetails());
                    intent.putExtra("Date", task.getDate());

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
