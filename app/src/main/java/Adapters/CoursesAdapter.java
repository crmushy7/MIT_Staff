package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crmushi.mitapp.R;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {

    private List<CoursesSetGet> items;
    private OnItemClickListener mListener;
    private boolean clickable = true;

    public CoursesAdapter(List<CoursesSetGet> items) {
        this.items = items;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }


    public void updateData(List<CoursesSetGet> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CoursesSetGet item = items.get(position);
        holder.bind(item);

        // Set the background drawable for receiptStatus TextView based on the status
//        if (item.getStatus() != null && item.getStatus().equals("Debt")) {
//            holder.receiptStatus.setBackgroundResource(R.drawable.roundedred);
//        } else {
//            holder.receiptStatus.setBackgroundResource(R.drawable.roundedgreen);
//        }

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
        private TextView course_name;
        private TextView course_duration;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            course_name=itemView.findViewById(R.id.course_name);
            course_duration=itemView.findViewById(R.id.course_duration);

        }

        public void bind(CoursesSetGet itemSetGet) {
            course_name.setText(itemSetGet.getCourseName());
            course_duration.setText(itemSetGet.getCourseDuration());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position,CoursesSetGet itemSetGet);
    }
}

