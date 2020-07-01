package com.example.signboard2.ui.chat;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.signboard2.model.RespJObj;
import com.example.signboard2.model.place.PlaceRental;
import com.example.signboard2.model.task.FeeLineReport;
import com.example.signboard2.model.task.TaskLst;
import com.example.signboard2.rest.ApiCallback;
import com.example.signboard2.rest.BaseAPIService;
import com.example.signboard2.rest.UtilsAPI;
import com.example.signboard2.ui.chat.message.MessageListFragment;
import com.example.signboard2.ui.tasks.detail.setup.SetupTaskDetail;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomFragment extends Fragment {

    private ChatRoomViewModel mViewModel;
    BaseAPIService mApiService;
    public static ChatRoomFragment newInstance() {
        return new ChatRoomFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.chat_fragment, container, false);

        mApiService = UtilsAPI.getAPIService();
        Call<RespJArr> call = mApiService.getMyChatRoom();
        final ListView listView = (ListView) root.findViewById(R.id.listRoom);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("xxxx", "onItemClick: ");
            }
        });
        final ChatRoomFragment fragment = this;

        ((MainActivity) getActivity()).showPrg();
        call.enqueue(new ApiCallback<RespJArr>(getActivity()) {
            @Override
            public void handleSuccess(Call<RespJArr> call, Response<RespJArr> response) {
                ((MainActivity) getActivity()).hidePrg();
                JsonArray res = response.body().getData();
                List<JsonObject> roomLst = new ArrayList<JsonObject>();
                for (JsonElement f :res){

                    roomLst.add(f.getAsJsonObject());
                }

                listView.setAdapter(new ListRoomAdapter(getActivity(), roomLst));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        Bundle bundle = new Bundle();
                        bundle.putString("roomID", roomLst.get(position).get("_id").getAsString() );
                        MessageListFragment fragment = new MessageListFragment();
                        fragment.setArguments(bundle);

                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
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
        mViewModel = ViewModelProviders.of(this).get(ChatRoomViewModel.class);
        // TODO: Use the ViewModel
    }

}
