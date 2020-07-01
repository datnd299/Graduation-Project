package com.example.signboard2.ui.chat.message;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.signboard2.R;
import com.example.signboard2.model.task.ImageReport;
import com.example.signboard2.utils.Utils;
import com.google.gson.JsonObject;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.messageViewHolder> implements View.OnClickListener,View.OnLongClickListener {

    Context context;
    List<JsonObject> messLst;
    RecyclerView mRecyclerView;

    public MessageListAdapter(Context context, RecyclerView recyclerView, List<JsonObject> messLst) {
        this.context = context;
        this.messLst = messLst;
        this.mRecyclerView = recyclerView;


    }

    @Override
    public MessageListAdapter.messageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_sent_item, viewGroup, false);


        return new MessageListAdapter.messageViewHolder(view);
    }
    @Override
    public  void onBindViewHolder(MessageListAdapter.messageViewHolder viewHolder, int position) {

        JsonObject jMess = messLst.get(position);
        if(jMess.get("s")!=null && jMess.get("s").getAsString().equals("me")){
            viewHolder.layoutU.setVisibility(View.GONE);
//            ViewGroup.LayoutParams params = viewHolder.layoutU.getLayoutParams();
//            params.height = 0;
//            viewHolder.layoutU.setLayoutParams(params);
           viewHolder.layoutM.setVisibility(View.VISIBLE);
            viewHolder.txtMContent.setText(jMess.get("content").getAsString());
            viewHolder.txtMInfo.setText(jMess.getAsJsonObject("sender").get("name").getAsString() +" - "+ Utils.toDateTimeString(jMess.get("createdAt").getAsString()));
        }else {
            viewHolder.layoutM.setVisibility(View.GONE);
//            ViewGroup.LayoutParams params = viewHolder.layoutM.getLayoutParams();
//            params.height = 0;
//            viewHolder.layoutM.setLayoutParams(params);
            viewHolder.layoutU.setVisibility(View.VISIBLE);
            viewHolder.txtUContent.setText(jMess.get("content").getAsString());
            viewHolder.txtUInfo.setText(jMess.getAsJsonObject("sender").get("name").getAsString() +" - "+ Utils.toDateTimeString(jMess.get("createdAt").getAsString()));
        }





    }

    @Override
    public int getItemCount() {
        return messLst.size();
    }

    @Override
    public void onClick(View v) {

    }



    @Override
    public boolean onLongClick(View v) {
        return false;
    }

     class messageViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutU;
        LinearLayout layoutM;
        TextView txtUContent;
         TextView txtUInfo;
         TextView txtMContent;
         TextView txtMInfo;
        public messageViewHolder(View itemView) {
            super(itemView);
            layoutU = (LinearLayout) itemView.findViewById(R.id.layoutYou);
            layoutM = (LinearLayout) itemView.findViewById(R.id.layoutMe);
            txtMContent = (TextView) itemView.findViewById(R.id.txtMContent);
            txtMInfo = (TextView) itemView.findViewById(R.id.txtMInfo);
            txtUContent = (TextView) itemView.findViewById(R.id.txtUContent);
            txtUInfo = (TextView) itemView.findViewById(R.id.txtUInfo);

//            imgImage = (ImageView) itemView.findViewById(R.id.imgImage);
//            btnDel = (Button) itemView.findViewById(R.id.btnDel);

        }
    }

}


