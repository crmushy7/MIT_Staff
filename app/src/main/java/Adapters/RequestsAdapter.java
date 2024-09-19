package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.registrationapp.R;

import java.util.List;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {

    private List<RequestsSetGet> items;
    private OnItemClickListener mListener;
    private boolean clickable = true;

    public RequestsAdapter(List<RequestsSetGet> items) {
        this.items = items;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }


    public void updateData(List<RequestsSetGet> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RequestsSetGet item = items.get(position);
        holder.bind(item);

        // Set the background drawable for receiptStatus TextView based on the status
        if (item.getRequestStatus() != null && item.getRequestStatus().equals("Declined")) {
            holder.requestStatus.setBackgroundResource(R.drawable.cardbgred);
            holder.requestStatus.setTextColor(R.color.white);
        } else if(item.getRequestStatus() != null && item.getRequestStatus().equals("Accepted")) {
            holder.requestStatus.setBackgroundResource(R.drawable.cardbggreen);
            holder.requestStatus.setTextColor(R.color.white);
        }else {
            holder.requestStatus.setBackgroundResource(R.drawable.cardbgwhite);
        }

        // Set click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(position, item);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView student_name;
        private TextView course_name;
        private TextView requestTime;
        private TextView requestStatus;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            course_name=itemView.findViewById(R.id.req_courseName);
            student_name=itemView.findViewById(R.id.req_stdName);
            requestStatus=itemView.findViewById(R.id.req_status);
            requestTime =itemView.findViewById(R.id.req_time);

        }

        public void bind(RequestsSetGet itemSetGet) {
            course_name.setText(itemSetGet.getCourseName());
            requestTime.setText(itemSetGet.getRequestTime());
            student_name.setText(itemSetGet.getStudentName());
            requestStatus.setText(itemSetGet.getRequestStatus());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position,RequestsSetGet itemSetGet);
    }
}

