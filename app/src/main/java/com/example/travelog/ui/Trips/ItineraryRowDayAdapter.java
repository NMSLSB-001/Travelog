package com.example.travelog.ui.Trips;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelog.R;

import java.util.List;

public class ItineraryRowDayAdapter extends RecyclerView.Adapter<ItineraryRowDayAdapter.rowVH> {

    List<ItineraryRowDay> ItineraryListDay;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(ItineraryRowDay itineraryRowDay);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ItineraryRowDayAdapter(List<ItineraryRowDay> itineraryListDay) {
        ItineraryListDay = itineraryListDay;
    }

    public void listClear() {
        int size = ItineraryListDay.size();
        ItineraryListDay.clear();
        notifyItemRangeRemoved(0, size);
    }

    @NonNull
    @Override
    public rowVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itinerary_row_day, parent, false);
        rowVH rvh = new rowVH(view, mListener);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull rowVH holder, int position) {
        ItineraryRowDay ItineraryRowDay = ItineraryListDay.get(position);
        holder.dayTitle.setText(ItineraryRowDay.getDayTitle());
        holder.description.setText(ItineraryRowDay.getDescription());
        holder.day.setText(ItineraryRowDay.getDayNum());

        Boolean isExpandable = ItineraryListDay.get(position).getExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE: View.GONE);
    }

    @Override
    public int getItemCount() {
        return ItineraryListDay.size();
    }

    public class rowVH extends RecyclerView.ViewHolder {

        TextView dayTitle, description, day;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;
        ImageView expand;

        public rowVH(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            dayTitle = itemView.findViewById(R.id.dayTitle);
            description = itemView.findViewById(R.id.description);
            day = itemView.findViewById(R.id.day);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            expand = itemView.findViewById(R.id.expand);

            expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ItineraryRowDay itineraryRowDay = ItineraryListDay.get(getAdapterPosition());
                    itineraryRowDay.setExpandable(!itineraryRowDay.getExpandable());
                    notifyItemChanged(getAdapterPosition());

                    Log.i("testing:", "expand");


                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(ItineraryListDay.get(position));

                    }
                }
            });

        }
    }


}
