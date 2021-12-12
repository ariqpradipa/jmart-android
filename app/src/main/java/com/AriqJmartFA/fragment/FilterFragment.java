package com.AriqJmartFA.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.AriqJmartFA.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FilterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilterFragment newInstance(String param1, String param2) {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static boolean applyFilterStatus = false;
    public static Integer filterPageMax;

    public static EditText productnameFilter;
    public static EditText lowestPrice;
    public static EditText highetPrice;
    public static RadioGroup radioConditionUsed;
    public static Spinner categorySpinner;
    public static ArrayAdapter<CharSequence> spinnerAdapter;

    public static String conditionUsed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_filter, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        productnameFilter = root.findViewById(R.id.productname_filter);
        lowestPrice = root.findViewById(R.id.lowest_filter);
        highetPrice = root.findViewById(R.id.highest_filter);
        radioConditionUsed = root.findViewById(R.id.radiogroup_condition);

        radioConditionUsed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioConditionUsed, int checkedId) {

                int selectedID = radioConditionUsed.getCheckedRadioButtonId();

                if(selectedID == R.id.new_filter) {

                    conditionUsed = "false";

                } else if(selectedID == R.id.used_filter) {

                    conditionUsed = "true";

                }
            }
        });

        categorySpinner = root.findViewById(R.id.category_filter);
        spinnerAdapter = ArrayAdapter.createFromResource(getContext(),R.array.category_list,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(spinnerAdapter);

        Button applyFilter = root.findViewById(R.id.apply_filter);
        Button clearFilter = root.findViewById(R.id.clear_filter);

        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                applyFilterStatus = true;
                Toast.makeText(getContext(), "Filter Applied", Toast.LENGTH_SHORT).show();

            }
        });

        clearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                applyFilterStatus = false;
                Toast.makeText(getContext(), "Filter Cleared", Toast.LENGTH_SHORT).show();

            }
        });

        return root;

    }
}