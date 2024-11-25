package com.example.application_fichiers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.io.File;

public class RechercheFichier extends AppCompatActivity {

    private EditText searchEditText;
    private TextView resultTextView;
    private static final int STORAGE_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);

        searchEditText = findViewById(R.id.searchEditText);
        resultTextView = findViewById(R.id.resultTextView);
        Button searchButton = findViewById(R.id.searchButton);
        Button annuller = findViewById(R.id.annuller);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchFile();
            }
        });

        annuller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

        private void searchFile() {
            String fileName = searchEditText.getText().toString();
            // File dir = Environment.getExternalStorageDirectory();
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS); // Pour le dossier Téléchargements
            File[] files = dir.listFiles();
            boolean found = false;

            if (files != null) {
                for (File file : files) {
                    if (file.getName().equalsIgnoreCase(fileName)) {
                        resultTextView.setText("Fichier trouvé : " + file.getAbsolutePath());
                        found = true;
                        break;
                    }
                }
            }

            if (!found) {
                resultTextView.setText("Fichier non trouvé");
            }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                // Permission denied
            }
        }
    }
}