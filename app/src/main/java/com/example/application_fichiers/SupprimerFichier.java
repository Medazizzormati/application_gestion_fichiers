package com.example.application_fichiers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.io.File;

public class SupprimerFichier extends AppCompatActivity {

    private EditText deleteFileNameEditText;
    private static final int STORAGE_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supprimer);

        deleteFileNameEditText = findViewById(R.id.deleteFileNameEditText);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button annuller = findViewById(R.id.annuller);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFile();
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

    private void deleteFile() {
        String fileName = deleteFileNameEditText.getText().toString();
        //File dir = Environment.getExternalStorageDirectory();
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS); // Pour le dossier Téléchargements
        File file = new File(dir, fileName);

        if (file.exists()) {
            if (file.delete()) {
                Toast.makeText(this, "Fichier supprimé : " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erreur lors de la suppression du fichier", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Fichier non trouvé", Toast.LENGTH_SHORT).show();
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