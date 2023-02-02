package com.itecknologi.iteckapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.VH> {

    private List<TripModel> tripModelList;

    public TripAdapter(List<TripModel> categoryModelList) {
        this.tripModelList = categoryModelList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_detail_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        holder.setData(tripModelList.get(position).getTime1(), tripModelList.get(position).getTime2(),
                tripModelList.get(position).getLocation1(), tripModelList.get(position).getLocation2(),
                tripModelList.get(position).getDistance(), tripModelList.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return tripModelList.size();
    }

    static public class VH extends RecyclerView.ViewHolder {

        private TextView time1;
        private TextView time2;

        private TextView location1;
        private TextView location2;

        private TextView distance;
        private TextView date;

        public VH(@NonNull View itemView) {
            super(itemView);

            time1 = itemView.findViewById(R.id.time1);
            time2 = itemView.findViewById(R.id.time2);
            location1 = itemView.findViewById(R.id.location1);
            location2 = itemView.findViewById(R.id.location2);
            distance = itemView.findViewById(R.id.distance);
            date = itemView.findViewById(R.id.date);

        }

        private void setData(final String time1,
                             final String time2,
                             final String location1,
                             final String location2,
                             final String distance,
                             final String date) {

            this.time1.setText(time1);
            this.time2.setText(time2);
            this.location1.setText(location1);
            this.location2.setText(location2);
            this.distance.setText(distance);
            this.date.setText(date);

        }


    }
}
