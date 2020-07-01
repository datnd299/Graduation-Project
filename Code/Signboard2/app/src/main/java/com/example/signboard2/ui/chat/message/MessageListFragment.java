package com.example.signboard2.ui.chat.message;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.signboard2.MainActivity;
import com.example.signboard2.R;
import com.example.signboard2.model.RespJArr;
import com.example.signboard2.model.other.Authenticate;
import com.example.signboard2.rest.ApiCallback;
import com.example.signboard2.rest.BaseAPIService;
import com.example.signboard2.rest.UtilsAPI;
import com.example.signboard2.ui.chat.ChatRoomFragment;
import com.example.signboard2.ui.chat.ListRoomAdapter;
import com.example.signboard2.ui.tasks.components.ListImageTextAdapter;
import com.example.signboard2.ui.tasks.detail.setup.ListOfListImageReportAdapter;
import com.example.signboard2.utils.Constant;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MessageListFragment extends Fragment {

    List<JsonObject> messLst;
    private MessageListViewModel mViewModel;
    MessageListAdapter messageListAdapter;
    BaseAPIService mApiService;
    RecyclerView recyclerView;
    public static MessageListFragment newInstance() {
        return new MessageListFragment();
    }

    private Socket mSocket;

    private void refreshAdapter(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageListAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messLst.size()-1);
            }
        });

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        IO.Options opts = new IO.Options();
        opts.forceNew = true;
        opts.transports = new String[] {"websocket"};


        try {
            mSocket = IO.socket(UtilsAPI.BASE_URL_SOCKET,opts);
        } catch (URISyntaxException e) {
            Log.d("xxxx", "onCreateView: "+e.getMessage());
        }
        mSocket.connect();

        View root = inflater.inflate(R.layout.message_list_fragment, container, false);
        mApiService = UtilsAPI.getAPIService();
        try {
            mSocket.emit("authenticate", new JSONObject("{token:'"+Constant.TOKEN+"'}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        mSocket.on("newMessageSended", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject jsonObject = (JSONObject) args[0];
                JsonObject gJsonObject = new JsonParser().parse(jsonObject.toString()).getAsJsonObject();
                messLst.add(gJsonObject);
                refreshAdapter();


                Log.d("xxxx", "call: ");
            }
        });
        Bundle bundle = this.getArguments();
        String id = null;
        if(bundle != null){
            id=(String)bundle.get("roomID");
        }
        Call<RespJArr> call = mApiService.getMessagesByPtID(id);


        ((MainActivity) getActivity()).showPrg();
        call.enqueue(new ApiCallback<RespJArr>(getActivity()) {
            @Override
            public void handleSuccess(Call<RespJArr> call, Response<RespJArr> response) {
                ((MainActivity) getActivity()).hidePrg();
                JsonArray res = response.body().getData();
                 messLst = new ArrayList<JsonObject>();
                for (JsonElement f :res){

                    messLst.add(f.getAsJsonObject());
                }

                 recyclerView = root.findViewById(R.id.reyclerview_message_list);
               recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext(),LinearLayoutManager.VERTICAL,false));
                recyclerView.setItemAnimator(new DefaultItemAnimator());


                 messageListAdapter = new MessageListAdapter(getActivity(),recyclerView,messLst);

                recyclerView.setAdapter(messageListAdapter);
                if(messLst.size()>0){
                    recyclerView.smoothScrollToPosition(messLst.size()-1);
                }



            }

            @Override
            public void handleFailure(Call<RespJArr> call, Response<RespJArr> response, Throwable t) {

            }


        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MessageListViewModel.class);
        // TODO: Use the ViewModel
    }

}
