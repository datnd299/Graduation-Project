package com.example.signboard2.ui.tasks.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.signboard2.R;
import com.example.signboard2.model.other.ImageText;
import com.example.signboard2.ui.tasks.detail.setup.SetupTaskDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class ListImageTextAdapter extends RecyclerView.Adapter<ListImageTextAdapter.viewHolder> {

    Context context;
    ArrayList<ImageText> arrayList;
    RecyclerView mRecyclerView;
    SetupTaskDetail mSetupTaskDetail;
    private ItemClickListener itemClickListener;

    public ListImageTextAdapter(Context context, RecyclerView recyclerView, SetupTaskDetail setupTaskDetail, ArrayList<ImageText> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.mSetupTaskDetail = setupTaskDetail;
        this.mRecyclerView = recyclerView;


    }

    @Override
    public ListImageTextAdapter.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_text_item, viewGroup, false);

        return new ListImageTextAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListImageTextAdapter.viewHolder viewHolder, int position) {

        ImageText img = arrayList.get(position);
        viewHolder.txtText.setText(img.getText());
        Picasso.get().load(img.getSrc()).into(viewHolder.imgImage);

//        if (imgReport.getHaveImg()) {
//            viewHolder.imgIcon.setVisibility(View.INVISIBLE);
//            viewHolder.imgImage.setVisibility(View.VISIBLE);
//            viewHolder.btnDel.setVisibility(View.VISIBLE);
//            viewHolder.btnDel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    arrayList.remove(position);
//                    mRecyclerView.getAdapter().notifyDataSetChanged();
//                }
//            });
//            viewHolder.imgImage.setImageBitmap(imgReport.getImg());
//        } else {
//            viewHolder.imgIcon.setVisibility(View.VISIBLE);
//            viewHolder.imgImage.setVisibility(View.INVISIBLE);
//            viewHolder.btnDel.setVisibility(View.INVISIBLE);
//        }


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imgImage;
        TextView txtText;


        public viewHolder(View itemView) {
            super(itemView);
            txtText = (TextView) itemView.findViewById(R.id.txtText);
            imgImage = (ImageView) itemView.findViewById(R.id.imgImage);


        }
    }
}