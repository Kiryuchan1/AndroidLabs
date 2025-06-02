package com.example.lab3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.FileOutputStream;
import java.io.IOException;

public class InputFragment extends Fragment {

    interface OnFormSubmitListener {
        void onFormSubmitted(String result);
    }

    private OnFormSubmitListener callback;

    private RadioGroup shapeGroup;
    private CheckBox cbArea, cbPerimeter;
    private EditText inputRadius, inputSide, inputLength, inputWidth;

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
        View view = inflater.inflate(R.layout.fragment_input, container, false);

        shapeGroup = view.findViewById(R.id.shapeGroup);
        cbArea = view.findViewById(R.id.cbArea);
        cbPerimeter = view.findViewById(R.id.cbPerimeter);
        inputRadius = view.findViewById(R.id.inputRadius);
        inputSide = view.findViewById(R.id.inputSide);
        inputLength = view.findViewById(R.id.inputLength);
        inputWidth = view.findViewById(R.id.inputWidth);
        Button btnOk = view.findViewById(R.id.btnOk);
        Button btnOpen = view.findViewById(R.id.btnOpen);

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
            int checkedId = shapeGroup.getCheckedRadioButtonId();
            String result = "";
            double area = 0, perimeter = 0;
            String shapeName = "";

            if (checkedId == R.id.rbCircle) {
                shapeName = "Коло";
                String rStr = inputRadius.getText().toString();
                if (TextUtils.isEmpty(rStr)) return;
                double r = Double.parseDouble(rStr);
                if (cbArea.isChecked()) area = Math.PI * r * r;
                if (cbPerimeter.isChecked()) perimeter = 2 * Math.PI * r;

                result += "Фігура: " + shapeName + "\n";
                result += "Радіус: " + r + "\n";

            } else if (checkedId == R.id.rbSquare) {
                shapeName = "Квадрат";
                String sStr = inputSide.getText().toString();
                if (TextUtils.isEmpty(sStr)) return;
                double s = Double.parseDouble(sStr);
                if (cbArea.isChecked()) area = s * s;
                if (cbPerimeter.isChecked()) perimeter = 4 * s;

                result += "Фігура: " + shapeName + "\n";
                result += "Сторона: " + s + "\n";

            } else if (checkedId == R.id.rbRectangle) {
                shapeName = "Прямокутник";
                String lStr = inputLength.getText().toString();
                String wStr = inputWidth.getText().toString();
                if (TextUtils.isEmpty(lStr) || TextUtils.isEmpty(wStr)) return;
                double l = Double.parseDouble(lStr);
                double w = Double.parseDouble(wStr);
                if (cbArea.isChecked()) area = l * w;
                if (cbPerimeter.isChecked()) perimeter = 2 * (l + w);

                result += "Фігура: " + shapeName + "\n";
                result += "Довжина: " + l + "\n";
                result += "Ширина: " + w + "\n";

            } else {
                Toast.makeText(getContext(), "Оберіть фігуру", Toast.LENGTH_SHORT).show();
                return;
            }

            if (cbArea.isChecked()) result += "Площа: " + area + "\n";
            if (cbPerimeter.isChecked()) result += "Периметр: " + perimeter + "\n";

            writeToFile(result);
            Toast.makeText(getContext(), "Результат збережено", Toast.LENGTH_SHORT).show();
            callback.onFormSubmitted(result);
        });

        btnOpen.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), StorageActivity.class));
        });

        return view;
    }

    private void writeToFile(String data) {
        try (FileOutputStream fos = requireContext().openFileOutput("results.txt", Context.MODE_APPEND)) {
            fos.write((data + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
