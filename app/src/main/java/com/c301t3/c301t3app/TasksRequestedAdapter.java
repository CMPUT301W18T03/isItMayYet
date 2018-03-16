package com.c301t3.c301t3app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


public class TasksRequestedAdapter extends RecyclerView.Adapter<TasksRequestedAdapter.TaskHolder> {
    private ArrayList<Task> tasks;
    private Context context;
    private int position;

    //clicks
    private View.OnLongClickListener longClickListener;
    private

    public TaskListAdapter(Context context, ArrayList<Task> tasks) {
        this.tasks = tasks;
        this.context = context;
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item,parent,false);
        return new TaskHolder(this.context, view);
    }

    @Override
    public void onBindViewHolder(final TaskHolder holder, final int pos) {

        // get task and place to holder
        final Task task = this.tasks.get(pos);
        holder.bindTask(task);

        // set pos on item click
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener()) {
            @Override
                public boolean onLongClick(View v) {
                position = pos;
                return false;
            }
        }

    }

    @Override
    public int getItemCount() {return this.tasks.size();}

    public class TaskHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,
            View.OnCreateContextMenuListener {

    }

}
