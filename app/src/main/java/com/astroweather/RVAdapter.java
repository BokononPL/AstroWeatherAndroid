package com.astroweather;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {

    private ArrayList<Location> locations;

    private Context context;
    private RVAdapter rvAdapter;

    public RVAdapter(ArrayList<Location> locations, Context context) {
        this.locations = locations;
        this.context = context;
        rvAdapter = this;
    }

    public void addLocation(Location l) {
        this.locations.add(l);
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;

        public MyViewHolder(LinearLayout linearLayout) {
            super(linearLayout);
            this.linearLayout = linearLayout;
        }
    }

    @Override
    public RVAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout ll = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element_layout, parent, false);

        MyViewHolder vh = new MyViewHolder(ll);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ((TextView)holder.linearLayout.findViewById(R.id.RVE_City_textView)).setText(locations.get(position).getCity());
        ((TextView)holder.linearLayout.findViewById(R.id.RVE_Country_textView)).setText(locations.get(position).getCountry());
        holder.linearLayout.findViewById(R.id.cl1).setOnClickListener(v -> {
            System.out.println("xD" + position);
        });
        holder.linearLayout.findViewById(R.id.RVE_Select_button).setOnClickListener(v -> {
            new OnClickAsyncTask().execute(position);
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    private class OnClickAsyncTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            rvAdapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            LocationDatabase db = LocationDatabase.getInstance(rvAdapter.context);
            LocationDao locationDao = db.locationDao();
            locationDao.deleteById(rvAdapter.getLocations().get(integers[0]).getId().toString());
            rvAdapter.getLocations().remove((int)integers[0]);
            return null;
        }
    }
}
