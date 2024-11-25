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
import java.io.FileOutputStream;
import java.io.IOException;

public class ModifierFichier extends AppCompatActivity {

    private EditText editFileNameEditText;
    private EditText editFileContentEditText;
    private static final int STORAGE_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier);

        editFileNameEditText = findViewById(R.id.editFileNameEditText);
        editFileContentEditText = findViewById(R.id.editFileContentEditText);
        Button editButton = findViewById(R.id.editButton);
        Button annuller = findViewById(R.id.annuller);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vérifier si le nom du fichier et le contenu sont saisis
                String fileName = editFileNameEditText.getText().toString().trim();
                String newContent = editFileContentEditText.getText().toString().trim();

                if (fileName.isEmpty()) {
                    Toast.makeText(ModifierFichier.this, "Veuillez saisir un nom de fichier", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (newContent.isEmpty()) {
                    Toast.makeText(ModifierFichier.this, "Veuillez saisir un contenu pour le fichier", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Si tout est valide, procéder à la modification du fichier
                editFile(fileName, newContent);
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

    private void editFile(String fileName, String newContent) {
        //String fileName = editFileNameEditText.getText().toString();
      //  String newContent = editFileContentEditText.getText().toString();
        //File dir = Environment.getExternalStorageDirectory();
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS); // Pour le dossier Téléchargements
        File file = new File(dir, fileName);

        if ( file.exists()) {
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(newContent.getBytes());
                fos.close();
                Toast.makeText(this, "Fichier modifié : " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Erreur lors de la modification du fichier", Toast.LENGTH_SHORT).show();
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