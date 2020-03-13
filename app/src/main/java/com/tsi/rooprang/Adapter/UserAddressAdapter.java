package com.tsi.rooprang.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.tsi.rooprang.Account.Saved_Address_Activity;
import com.tsi.rooprang.Account.UserAddressDataModel;
import com.tsi.rooprang.Account.UserAddressDatabaseHelper;
import com.tsi.rooprang.DTO.Address_DTO.Delete_Address_DTO;
import com.tsi.rooprang.DTO.Address_DTO.Show_Address_Details_DTO;
import com.tsi.rooprang.R;
import com.tsi.rooprang.Session.SessionManager;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.retrofit.NewApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tsi.rooprang.Account.Saved_Address_Activity.saved_address_activity;

public class UserAddressAdapter extends RecyclerView.Adapter<UserAddressAdapter.UserViewHolder> {

    List<Show_Address_Details_DTO> show_address_details_dtos;
    Context context;
    UserAddressDatabaseHelper dbHelper;
    SQLiteDatabase db;
    ProgressDialog mProgressDialog;
    ApiInterface apiService;
    String user_id = "", address_id = "";
    private int selectedPosition = -1;// no selection by default

    public UserAddressAdapter(Context applicationContext, List<Show_Address_Details_DTO> show_address_details_dtos) {
        this.show_address_details_dtos = show_address_details_dtos;


    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View iteView = inflater.inflate(R.layout.item_address_list, parent, false);
        UserViewHolder viewHolder = new UserViewHolder(iteView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int position) {
        apiService = NewApiClient.getClient().create(ApiInterface.class);
        mProgressDialog = new ProgressDialog(context);


        holder.tvName.setText(show_address_details_dtos.get(position).getFullName() != null ? show_address_details_dtos.get(position).getFullName() : "");
        holder.tvPincode.setText(show_address_details_dtos.get(position).getPincode() != null ? show_address_details_dtos.get(position).getPincode() : "");
        holder.tvAddress.setText(show_address_details_dtos.get(position).getAddressId() != null ? show_address_details_dtos.get(position).getAddressId() : "");
        holder.tvLandmark.setText(show_address_details_dtos.get(position).getLandmark() != null ? show_address_details_dtos.get(position).getLandmark() : "");
        holder.tvCity.setText(show_address_details_dtos.get(position).getCity() != null ? show_address_details_dtos.get(position).getCity() : "");
        holder.tvState.setText(show_address_details_dtos.get(position).getState() != null ? show_address_details_dtos.get(position).getState() : "");
        holder.tvMobile.setText(show_address_details_dtos.get(position).getMobile() != null ? show_address_details_dtos.get(position).getMobile() : "");
        user_id = SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_USERID);

        if (selectedPosition == position) {
            holder.check_address.setChecked(true);
        } else {
            holder.check_address.setChecked(false);
        }

        holder.check_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                address_id = show_address_details_dtos.get(selectedPosition).getAddressId() != null ? show_address_details_dtos.get(selectedPosition).getAddressId() : "";
                saved_address_activity.button_click(address_id);
                notifyDataSetChanged();
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address_id = show_address_details_dtos.get(position).getAddressId() != null ? show_address_details_dtos.get(position).getAddressId() : "";
                delete_address(user_id, address_id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return show_address_details_dtos.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPincode, tvAddress, tvLandmark, tvCity, tvState, tvMobile;
        ImageView ivEdit, ivDelete;
        CheckBox check_address;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPincode = (TextView) itemView.findViewById(R.id.tv_pincode);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            tvLandmark = (TextView) itemView.findViewById(R.id.tv_landmark);
            tvCity = (TextView) itemView.findViewById(R.id.tv_city);
            tvState = (TextView) itemView.findViewById(R.id.tv_state);
            tvMobile = (TextView) itemView.findViewById(R.id.tv_mobile);
            ivDelete = (ImageView) itemView.findViewById(R.id.iv_delete);
            check_address = itemView.findViewById(R.id.check_address);
        }
    }

    public void delete_address(String userId, String address_id) {
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        Call<Delete_Address_DTO> cartDtoCall = apiService.delete_address_api(userId, address_id);

        cartDtoCall.enqueue(new Callback<Delete_Address_DTO>() {
            @Override
            public void onResponse(Call<Delete_Address_DTO> call, Response<Delete_Address_DTO> response) {
                Delete_Address_DTO delete_address_dto = response.body();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                if (delete_address_dto.getMessage().equalsIgnoreCase("success")) {
                    Toast.makeText(context, delete_address_dto.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    if (saved_address_activity != null) {
                        saved_address_activity.recreate();
                    }
                }
            }

            @Override
            public void onFailure(Call<Delete_Address_DTO> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("failure", t + "");
            }
        });
    }

}
