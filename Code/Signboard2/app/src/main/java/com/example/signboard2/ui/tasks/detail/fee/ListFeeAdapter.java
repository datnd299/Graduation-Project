package com.example.signboard2.ui.tasks.detail.fee;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.signboard2.R;
import com.example.signboard2.model.task.FeeLineReport;
import com.example.signboard2.model.task.ImageReport;
import com.example.signboard2.model.task.ImageSignboardReport;
import com.example.signboard2.ui.tasks.components.ListImageAdapter;
import com.example.signboard2.ui.tasks.detail.setup.SetupTaskDetail;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListFeeAdapter extends BaseAdapter {
    private List<FeeLineReport> feeLst;
    private Context context;
    private FeeTaskDetail fragment;
    private LayoutInflater layoutInflater;
    public ListFeeAdapter(Context aContext, FeeTaskDetail fragment, List<FeeLineReport> fees) {
        this.context = aContext;
        this.feeLst = fees;
        this.fragment = fragment;
        layoutInflater = LayoutInflater.from(aContext);
    }




    @Override
    public int getCount() {
        return feeLst.size();
    }

    @Override
    public Object getItem(int position) {
        return feeLst.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.component_single_fee, null);
            FeeLineReport feeLineReport = this.feeLst.get(position);
            ((TextView)convertView.findViewById(R.id.txtPlaceName)).setText(feeLineReport.getPlace().getName());
            ((TextView)convertView.findViewById(R.id.txtAmount)).setText("Số tiền: "+String.format("%1$,.0f", feeLineReport.getAmount()));
            ((TextView)convertView.findViewById(R.id.txtTime)).setText(feeLineReport.getTimeString());

            CheckBox chEmComfirm = ((CheckBox)convertView.findViewById(R.id.chEmComfirm));
            CheckBox chPtBConfirm = ((CheckBox)convertView.findViewById(R.id.chPtBConfirm));
            TextInputEditText edtEmNote= ((TextInputEditText)convertView.findViewById(R.id.edtEmNote));
            TextInputEditText edtPtBNote= ((TextInputEditText)convertView.findViewById(R.id.edtPtBNote));

            chEmComfirm.setChecked(feeLineReport.isEmConfirm());
            chPtBConfirm.setChecked(feeLineReport.isPtBConfirm());
            edtEmNote.setText(feeLineReport.getEmNote());
            edtPtBNote.setText(feeLineReport.getPtBNote());
            if(feeLineReport.isEmConfirm()&&feeLineReport.isPtBConfirm()){
                chPtBConfirm.setEnabled(false);
                chEmComfirm.setEnabled(false);
            }
            if(feeLineReport.isRoleA()){
               chEmComfirm.setEnabled(true);
                edtEmNote.setEnabled(true);

                chPtBConfirm.setEnabled(false);
                edtPtBNote.setEnabled(false);
            }else {
                chEmComfirm.setEnabled(false);
                edtEmNote.setEnabled(false);

                chPtBConfirm.setEnabled(true);
                edtPtBNote.setEnabled(true);
            }
            chEmComfirm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    feeLineReport.setEmConfirm(isChecked);
                }
            });
            chPtBConfirm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    feeLineReport.setPtBConfirm(isChecked);
                }
            });
            edtEmNote.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                        feeLineReport.setEmNote(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            edtPtBNote.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    feeLineReport.setPtBNote(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } else {

        }


        return convertView;
    }
}
