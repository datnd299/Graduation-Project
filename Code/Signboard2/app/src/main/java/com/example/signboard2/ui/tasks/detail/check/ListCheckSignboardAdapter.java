package com.example.signboard2.ui.tasks.detail.check;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.signboard2.R;
import com.example.signboard2.model.place.PlaceRental;
import com.example.signboard2.model.task.CheckLineReport;
import com.example.signboard2.model.task.ImageReport;
import com.example.signboard2.model.task.ImageSignboardReport;
import com.example.signboard2.ui.tasks.TaskFragment;
import com.example.signboard2.ui.tasks.components.ListImageAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListCheckSignboardAdapter extends BaseAdapter {

    private  Context context;
    private  Fragment fragment;
    private  List<ImageSignboardReport> signboardReportList;
    ListImageAdapter rAdapter;
    private LayoutInflater layoutInflater;
    RecyclerView recyclerView;
    public ListCheckSignboardAdapter(Context aContext, Fragment fragment, List<ImageSignboardReport> signboardReportList) {
        this.context = aContext;
        this.signboardReportList = signboardReportList;
        this.fragment = fragment;
        layoutInflater = LayoutInflater.from(aContext);
    }

    public void refreshRecycleView(){
//        if(rAdapter!=null) {
        rAdapter.notifyItemInserted(rAdapter.getItemCount()-1);
            rAdapter.notifyDataSetChanged();

            recyclerView.setAdapter(rAdapter);
//        }

    }
    @Override
    public int getCount() {
        return signboardReportList.size();
    }

    @Override
    public Object getItem(int position) {
        return signboardReportList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.component_single_check_signboard, null);
            ImageSignboardReport imageSignboardReport = this.signboardReportList.get(position);
            ((TextView)convertView.findViewById(R.id.sbName)).setText(imageSignboardReport.getName());
            ((TextInputEditText) convertView.findViewById(R.id.edtNote)).setText(imageSignboardReport.getNote());
            ((RatingBar) convertView.findViewById(R.id.rating)).setRating(imageSignboardReport.getRating());

            ((RatingBar) convertView.findViewById(R.id.rating)).setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if(fromUser){
                        imageSignboardReport.setRating(rating);
                    }

                }
            });
            ((TextInputEditText) convertView.findViewById(R.id.edtNote)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    imageSignboardReport.setNote(s.toString());
                }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            recyclerView = (RecyclerView) convertView.findViewById(R.id.imgsList);
            recyclerView.setLayoutManager(new LinearLayoutManager(convertView.getContext(),LinearLayoutManager.HORIZONTAL,false));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            rAdapter = new ListImageAdapter(convertView.getContext(),recyclerView,fragment, imageSignboardReport.getImgs());
            imageSignboardReport.setListImageAdapter(rAdapter);
            for (ImageReport im:imageSignboardReport.getImgs()){
                im.setListImageAdapter(rAdapter);
            }
            recyclerView.setAdapter(rAdapter);

        } else {

        }


        return convertView;
    }
}
