package com.tsi.rooprang.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

import com.tsi.rooprang.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddressBottomFragment extends BottomSheetDialogFragment {

    private BottomSheetListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.address_bottomsheet, container, false);

        Button confrm = v.findViewById(R.id.confrm);
        confrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                dismiss();
            }
        });
        ImageButton close = v.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });


        return v;
    }

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement BottomSheetListener");
        }
    }
}