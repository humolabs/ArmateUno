package com.humolabs.armateuno.activity;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.humolabs.armateuno.domain.Partido;
import com.humolabs.armateuno.domain.User;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDataHandler {

    FirebaseDatabase database;
    DatabaseReference reference;

    public FirebaseDataHandler (){

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("usuarios");
    }

    public void savePartido(Partido partido, final Context applicationContext) {

        try {
            User user = new User("usuario1", "asd123");

            if (user.getPartidosCreados() == null || user.getPartidosCreados().isEmpty()) {
                List<Partido> partidos = new ArrayList<>();
                user.setPartidosCreados(partidos);
            }
            user.getPartidosCreados().add(partido);
            getReference().push().setValue(user, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Toast.makeText(applicationContext, "Data could not be saved " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(applicationContext, "Data saved successfully." + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch(Exception e){
            Toast.makeText(applicationContext,"Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public DatabaseReference getReference() {
        return reference;
    }

    public void setReference(DatabaseReference reference) {
        this.reference = reference;
    }
}
