package com.example.travelog.ui.Trips;

import android.icu.text.Transliterator;
import android.net.sip.SipSession;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelog.R;

import java.sql.Time;
import java.util.List;

public class ItineraryRowAdapter extends RecyclerView.Adapter<ItineraryRowAdapter.rowVH> {

    List<ItineraryRow> ItineraryList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onItemClick(ItineraryRow itineraryRow);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ItineraryRowAdapter(List<ItineraryRow> itineraryList) {
        ItineraryList = itineraryList;
    }

    @NonNull
    @Override
    public rowVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itinerary_row, parent, false);
        rowVH rvh = new rowVH(view, mListener);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull rowVH holder, int position) {
        ItineraryRow ItineraryRow = ItineraryList.get(position);
        holder.rowTitle.setText(ItineraryRow.getRowTitle());
        holder.description.setText(ItineraryRow.getDescription());
        holder.location.setText(ItineraryRow.getLocation());
        holder.startTime.setText(String.valueOf(ItineraryRow.getStartTime()));
        holder.endTime.setText(String.valueOf(ItineraryRow.getEndTime()));

        Boolean isExpandable = ItineraryList.get(position).getExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE: View.GONE);
    }

    @Override
    public int getItemCount() {
        return ItineraryList.size();
    }

    public class rowVH extends RecyclerView.ViewHolder {

        TextView rowTitle, description, location, startTime, endTime;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;
        ImageView delete;
        ImageView expand;

        public rowVH(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            rowTitle = itemView.findViewById(R.id.rowTitle);
            description = itemView.findViewById(R.id.description);
            location = itemView.findViewById(R.id.location);
            startTime = itemView.findViewById(R.id.startTime);
            endTime = itemView.findViewById(R.id.endTime);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            delete = itemView.findViewById(R.id.delete);
            expand = itemView.findViewById(R.id.expand);

            expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ItineraryRow itineraryRow = ItineraryList.get(getAdapterPosition());
                    itineraryRow.setExpandable(!itineraryRow.getExpandable());
                    notifyItemChanged(getAdapterPosition());


                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(ItineraryList.get(position));
                    }
                }
            });


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }

                }
            });



        }
    }


}
