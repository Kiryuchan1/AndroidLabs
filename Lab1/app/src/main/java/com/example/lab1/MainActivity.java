package com.example.lab1;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RadioGroup shapeGroup;
    private CheckBox cbArea, cbPerimeter;
    private Button btnOk;
    private TextView tvResult;

    private EditText inputRadius, inputSide, inputLength, inputWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shapeGroup = findViewById(R.id.shapeGroup);
        cbArea = findViewById(R.id.cbArea);
        cbPerimeter = findViewById(R.id.cbPerimeter);
        btnOk = findViewById(R.id.btnOk);
        tvResult = findViewById(R.id.tvResult);

        inputRadius = findViewById(R.id.inputRadius);
        inputSide = findViewById(R.id.inputSide);
        inputLength = findViewById(R.id.inputLength);
        inputWidth = findViewById(R.id.inputWidth);

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
            int selectedShapeId = shapeGroup.getCheckedRadioButtonId();
            boolean isAreaChecked = cbArea.isChecked();
            boolean isPerimeterChecked = cbPerimeter.isChecked();

            if (selectedShapeId == -1 || (!isAreaChecked && !isPerimeterChecked)) {
                showAlert("Помилка", "Будь ласка, завершіть введення всіх даних.");
                return;
            }

            double area = 0, perimeter = 0;
            boolean validInput = true;

            if (selectedShapeId == R.id.rbCircle) {
                String radiusStr = inputRadius.getText().toString();
                if (radiusStr.isEmpty()) {
                    validInput = false;
                } else {
                    double r = Double.parseDouble(radiusStr);
                    if (isAreaChecked) area = Math.PI * r * r;
                    if (isPerimeterChecked) perimeter = 2 * Math.PI * r;
                }
            } else if (selectedShapeId == R.id.rbSquare) {
                String sideStr = inputSide.getText().toString();
                if (sideStr.isEmpty()) {
                    validInput = false;
                } else {
                    double a = Double.parseDouble(sideStr);
                    if (isAreaChecked) area = a * a;
                    if (isPerimeterChecked) perimeter = 4 * a;
                }
            } else if (selectedShapeId == R.id.rbRectangle) {
                String lengthStr = inputLength.getText().toString();
                String widthStr = inputWidth.getText().toString();
                if (lengthStr.isEmpty() || widthStr.isEmpty()) {
                    validInput = false;
                } else {
                    double l = Double.parseDouble(lengthStr);
                    double w = Double.parseDouble(widthStr);
                    if (isAreaChecked) area = l * w;
                    if (isPerimeterChecked) perimeter = 2 * (l + w);
                }
            }

            if (!validInput) {
                showAlert("Помилка", "Будь ласка, введіть всі необхідні параметри.");
                return;
            }

            StringBuilder result = new StringBuilder();
            RadioButton selectedRadio = findViewById(selectedShapeId);
            String shape = selectedRadio.getText().toString();

            result.append("Фігура: ").append(shape).append("\n");

            if (isAreaChecked)
                result.append("Площа: ").append(String.format("%.2f", area)).append("\n");
            if (isPerimeterChecked)
                result.append("Периметр: ").append(String.format("%.2f", perimeter)).append("\n");

            tvResult.setText(result.toString());
        });
    }

    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
