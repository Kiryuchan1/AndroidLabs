package com.example.lab2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;



public class InputFragment extends Fragment {

    interface OnFormSubmitListener {
        void onFormSubmitted(String result);
    }

    private OnFormSubmitListener callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFormSubmitListener) {
            callback = (OnFormSubmitListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFormSubmitListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false); // ВАЖЛИВО!

        // Тепер додай логіку, яку ти раніше писав — кнопки, поля тощо
        // Наприклад:
        Button btnOk = view.findViewById(R.id.btnOk);
        CheckBox cbArea = view.findViewById(R.id.cbArea);
        CheckBox cbPerimeter = view.findViewById(R.id.cbPerimeter);
        EditText inputRadius = view.findViewById(R.id.inputRadius);
        EditText inputSide = view.findViewById(R.id.inputSide);
        EditText inputLength = view.findViewById(R.id.inputLength);
        EditText inputWidth = view.findViewById(R.id.inputWidth);
        RadioGroup shapeGroup = view.findViewById(R.id.shapeGroup);

        // Показ/приховування полів по вибору фігури
        shapeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            inputRadius.setVisibility(View.GONE);
            inputSide.setVisibility(View.GONE);
            inputLength.setVisibility(View.GONE);
            inputWidth.setVisibility(View.GONE);

            if (checkedId == R.id.rbCircle) {
                inputRadius.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.rbSquare) {
                inputSide.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.rbRectangle) {
                inputLength.setVisibility(View.VISIBLE);
                inputWidth.setVisibility(View.VISIBLE);
            }
        });

        btnOk.setOnClickListener(v -> {
            StringBuilder result = new StringBuilder();

            int selectedShapeId = shapeGroup.getCheckedRadioButtonId();
            boolean area = cbArea.isChecked();
            boolean perimeter = cbPerimeter.isChecked();

            if (selectedShapeId == R.id.rbCircle) {
                double radius = Double.parseDouble(inputRadius.getText().toString());
                if (area) result.append("Площа кола: ").append(Math.PI * radius * radius).append("\n");
                if (perimeter) result.append("Периметр кола: ").append(2 * Math.PI * radius).append("\n");
            } else if (selectedShapeId == R.id.rbSquare) {
                double side = Double.parseDouble(inputSide.getText().toString());
                if (area) result.append("Площа квадрата: ").append(side * side).append("\n");
                if (perimeter) result.append("Периметр квадрата: ").append(4 * side).append("\n");
            } else if (selectedShapeId == R.id.rbRectangle) {
                double length = Double.parseDouble(inputLength.getText().toString());
                double width = Double.parseDouble(inputWidth.getText().toString());
                if (area) result.append("Площа прямокутника: ").append(length * width).append("\n");
                if (perimeter) result.append("Периметр прямокутника: ").append(2 * (length + width)).append("\n");
            }

            callback.onFormSubmitted(result.toString());
        });

        return view;
    }
}
