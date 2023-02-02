package com.itecknologi.iteckapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.VH> {

    private List<DriverListModel> driverListModelList;

    public DriverAdapter(List<DriverListModel> driverListModelList) {
        this.driverListModelList = driverListModelList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.driving_activity_rv_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        holder.setData(driverListModelList.get(position).getTitle(), driverListModelList.get(position).getDesc(),
                driverListModelList.get(position).getHours());
    }

    @Override
    public int getItemCount() {
        return driverListModelList.size();
    }

    static public class VH extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView desc;
        private TextView hours;

        public VH(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.txtTitle);
            desc = itemView.findViewById(R.id.txtDesc);
            hours = itemView.findViewById(R.id.txtHours);

        }

        private void setData(final String title, final String desc, final String hours) {
            this.title.setText(title);
            this.desc.setText(desc);
            this.hours.setText(hours);
        }


    }
}
