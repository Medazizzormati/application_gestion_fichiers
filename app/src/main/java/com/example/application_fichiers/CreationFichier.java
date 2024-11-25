package com.example.application_fichiers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx .appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreationFichier extends AppCompatActivity {

    EditText fileNameEditText;
    EditText editTextFileContent;

    private static final int STORAGE_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);

        fileNameEditText = findViewById(R.id.fileNameEditText);
        editTextFileContent = findViewById(R.id.editTextFileContent);
        Button createButton = findViewById(R.id.createButton);
        Button annuller = findViewById(R.id.annuller);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = fileNameEditText.getText().toString().trim();
                String fileContent = editTextFileContent.getText().toString().trim();
                if (fileName.isEmpty()) {
                    Toast.makeText(CreationFichier.this, "Veuillez saisir un nom de fichier", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fileContent.isEmpty()) {
                    Toast.makeText(CreationFichier.this, "Veuillez saisir du contenu pour le fichier", Toast.LENGTH_SHORT).show();
                    return;
                }
                createFile();
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


    private void createFile() {
        String fileName = fileNameEditText.getText().toString();
        String fileContent = editTextFileContent.getText().toString(); // Get content from EditText
       // File dir = getExternalFilesDir(null); // Répertoire spécifique à l'application
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS); // Pour le dossier Téléchargements
        File file = new File(dir, fileName);

        try {
            if (!dir.exists()) {
                dir.mkdirs(); // Crée le répertoire si nécessaire
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(fileContent.getBytes());
            //fos.write("Contenu du fichier".getBytes());
            fos.close();
            Toast.makeText(this, "Fichier créé : " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur lors de la création du fichier : " + e.getMessage(), Toast.LENGTH_SHORT).show();
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