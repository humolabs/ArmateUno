package com.humolabs.armateuno.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.humolabs.armateuno.R;
import com.humolabs.armateuno.domain.Cancha;
import com.humolabs.armateuno.domain.Partido;
import com.humolabs.armateuno.domain.Ubicacion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdminPanelActivity extends FragmentActivity implements View.OnClickListener{

    static final String TAG = "AdminPanelActivity";
    static final Integer READ_CONTACT_PERMISSION = 1;

    EditText btnDateHour;
    EditText inputPlayerQuantity;
    ImageView btnMap;
    TextView txtAddress;
    TextView txtNombreCancha;
    Button btnContactList;
    Button btnCleanPlayersList;
    Button btnGuardarPartido;
    List<String> playerList = new ArrayList<>();
    ArrayAdapter<String> playersAdapter;
    Integer playersQuantity;
    ProgressBar spinner;
    Place place;

    int PLACE_PICKER_REQUEST = 0;
    int PICK_CONTACT = 0;

    private SimpleDateFormat mFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);

    ListView playersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.admin_panel_activity);
        super.onCreate(savedInstanceState);
        initializeComponents();
    }

    private void initializeComponents() {

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        playersListView = (ListView)findViewById(R.id.playerList);
        playersAdapter = new ArrayAdapter<>(this, R.layout.listview_contactos, R.id.textView, playerList);
        playersListView.setAdapter(playersAdapter);
        playersListView.setOnItemClickListener(deleteContactFromList);

        btnDateHour = (EditText) findViewById(R.id.btnfechahora);
        btnDateHour.setOnClickListener(this);

        btnMap = (ImageView) findViewById(R.id.mapa);
        btnMap.setOnClickListener(this);

        inputPlayerQuantity = (EditText) findViewById(R.id.inputCantJugadores);
        inputPlayerQuantity.setOnClickListener(this);

        btnContactList = (Button) findViewById(R.id.btnListaContactos);
        btnContactList.setOnClickListener(this);

        btnCleanPlayersList = (Button) findViewById(R.id.btnVaciarLista);
        btnCleanPlayersList.setOnClickListener(this);

        btnGuardarPartido = (Button) findViewById(R.id.btnGuardarPartido);
        btnGuardarPartido.setOnClickListener(this);

        txtNombreCancha = (TextView) findViewById(R.id.txtNombreCancha);
    }


    AdapterView.OnItemClickListener deleteContactFromList = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(AdminPanelActivity.this);
            dialog.setMessage(R.string.confirmar_delete).setTitle(R.string.confirmar_delete_title);
            dialog.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    playerList.remove(playerList.get(position));
                    playersAdapter.notifyDataSetChanged();
                }
            });
            dialog.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            dialog.setCancelable(false);
            dialog.show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{

        if(requestCode == PLACE_PICKER_REQUEST){
            if(resultCode == RESULT_OK){

                spinner.setVisibility(View.GONE);
                place = PlacePicker.getPlace(this, data);
                String address = String.format("%s", place.getAddress());
                txtAddress = (TextView) findViewById(R.id.txtDireccion);
                txtAddress.setText(address);
            }
            PLACE_PICKER_REQUEST = 0;
        }

        if(requestCode == PICK_CONTACT){
            if (resultCode == Activity.RESULT_OK) {

                Uri contactData = data.getData();
                Cursor c = getContentResolver().query(contactData, null, null, null, null);
                    if (c!=null && c.moveToFirst()) {
                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                            if(phones != null) {
                                phones.moveToFirst();
                                String phone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                                if (playerList.size() < playersQuantity) {
                                    playerList.add(name + " " + phone);
                                    playersAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Máxima cantidad de jugadores alcanzada", Toast.LENGTH_SHORT).show();
                                }
                                phones.close();
                            }
                        }
                        c.close();
                    }
                PICK_CONTACT = 0;
            }
        }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Hubo un error", Toast.LENGTH_SHORT).show();
            Log.e(TAG, e.getMessage());
        }
    }

    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
            Toast.makeText(AdminPanelActivity.this, mFormatter.format(date), Toast.LENGTH_SHORT).show();
            btnDateHour.setText(mFormatter.format(date));
        }

        @Override
        public void onDateTimeCancel()
        {
            Toast.makeText(AdminPanelActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnfechahora:
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(new Date())
                        //.setMinDate(minDate)
                        //.setMaxDate(maxDate)
                        //.setIs24HourTime(true)
                        .setTheme(SlideDateTimePicker.HOLO_DARK)
                        //.setIndicatorColor(Color.parseColor("#990000"))
                        .build()
                        .show();
                break;
            case R.id.mapa:
                PLACE_PICKER_REQUEST = 1;

                spinner.setVisibility(View.VISIBLE);
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    Intent intent = builder.build(AdminPanelActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnListaContactos:
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AdminPanelActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACT_PERMISSION);
                } else {
                    pickContact();
                    break;
                }
            case R.id.btnVaciarLista:
                playerList.clear();
                playersAdapter.notifyDataSetChanged();
                break;
            case R.id.btnGuardarPartido:
                FirebaseDataHandler dataHandler = new FirebaseDataHandler();
                dataHandler.savePartido(buildFulbacho(), getApplicationContext());
                break;
        }
    }

    private Partido buildFulbacho() {
        Partido partido = new Partido();
        partido.setUbicacion(new Ubicacion(place));
        partido.setHorario(btnDateHour.getText().toString());
        String domicilio = place.getAddress().toString();
        partido.setCancha(new Cancha(txtNombreCancha.getText().toString(), domicilio, Integer.parseInt(inputPlayerQuantity.getText().toString())));
        return partido;
    }

    private void pickContact() {
        String checkJugadores = inputPlayerQuantity.getText().toString();
        if(!checkJugadores.matches("")) {
            PICK_CONTACT = 1;
            playersQuantity = Integer.parseInt(checkJugadores);
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);
        }else{
            Toast.makeText(getApplicationContext(), "Debe ingresar una cantidad de jugadores", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickContact();
                } else {
                    Toast.makeText(AdminPanelActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}