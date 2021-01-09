package com.example.weektwotest.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.weektwotest.R;
import com.example.weektwotest.ui.main.PhoneNumber;

import java.util.ArrayList;

public class PhoneNumberAdapter extends ArrayAdapter<PhoneNumber> {
    private Context context;
    private ArrayList<PhoneNumber> phoneNumbers;

    public PhoneNumberAdapter(Context context, ArrayList<PhoneNumber> phoneNumbers) {
        super(context,-1,phoneNumbers);
        this.context = context;
        this.phoneNumbers = phoneNumbers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.single_phonenumber, parent, false);

        TextView nameTextView = (TextView) rowView.findViewById(R.id.txt_name);
        nameTextView.setText(phoneNumbers.get(position).getName());
        final TextView numberTextView = (TextView) rowView.findViewById(R.id.number);
        numberTextView.setText(phoneNumbers.get(position).getNumber());

        return rowView;
    }
}