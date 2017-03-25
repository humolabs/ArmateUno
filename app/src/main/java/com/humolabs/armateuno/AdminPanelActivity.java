package com.humolabs.armateuno;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminPanelActivity extends FragmentActivity {

    EditText btnFechaHora;
    ImageView btnMapa;
    TextView txtDireccion;
    Button btnListaContactos;
    EditText inputCantJugadores;
    Button btnVaciarLista;
    List<String> convocados = new ArrayList<>();
    ArrayAdapter<String> convocadosAdapter;
    Integer cantidadJugadores;

    int PLACE_PICKER_REQUEST = 0;
    int PICK_CONTACT = 0;

    private SimpleDateFormat mFormatter = new SimpleDateFormat(getString(R.string.fecha_y_hora_layout));

    ListView listaConvocados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.admin_panel_activity);
        super.onCreate(savedInstanceState);



        listaConvocados = (ListView)findViewById(R.id.simpleListView);
        convocadosAdapter = new ArrayAdapter<>(this, R.layout.listview_contactos, R.id.textView, convocados);
        listaConvocados.setAdapter(convocadosAdapter);

        btnFechaHora = (EditText) findViewById(R.id.btnfechahora);
        btnFechaHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(new Date())
                        //.setMinDate(minDate)
                        //.setMaxDate(maxDate)
                        //.setIs24HourTime(true)
                        //.setTheme(SlideDateTimePicker.HOLO_DARK)
                        //.setIndicatorColor(Color.parseColor("#990000"))
                        .build()
                        .show();
            }
        });

        btnMapa = (ImageView) findViewById(R.id.mapa);
        btnMapa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PLACE_PICKER_REQUEST = 1;
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent intent;

                try {
                    intent = builder.build(getApplicationContext());
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });

        inputCantJugadores = (EditText) findViewById(R.id.inputCantJugadores);

        btnListaContactos = (Button) findViewById(R.id.btnListaContactos);
        btnListaContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkJugadores = inputCantJugadores.getText().toString();
                if(!checkJugadores.matches("")) {
                    PICK_CONTACT = 1;
                    cantidadJugadores = Integer.parseInt(checkJugadores);
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, PICK_CONTACT);
                }else{
                    Toast.makeText(getApplicationContext(), "Debe ingresar una cantidad de jugadores", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnVaciarLista = (Button) findViewById(R.id.btnVaciarLista);
        btnVaciarLista.setOnClickListener(vaciarListaListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PLACE_PICKER_REQUEST){
            if(resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(data, this);
                String address = String.format("%s", place.getAddress());
                txtDireccion = (TextView) findViewById(R.id.txtDireccion);
                txtDireccion.setText(address);
            }
            PLACE_PICKER_REQUEST = 0;
        }

        if(requestCode == PICK_CONTACT){
            if (resultCode == Activity.RESULT_OK) {

                Uri contactData = data.getData();
                Cursor c = getContentResolver().query(contactData, null, null, null, null);

                if (c.moveToFirst())
                {
                    String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                    String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                    if (hasPhone.equalsIgnoreCase("1"))
                    {
                        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);
                        phones.moveToFirst();
                        String telefono = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String nombre = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        if(convocados.size()<cantidadJugadores){
                            convocados.add(nombre + " " + telefono);
                            convocadosAdapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(getApplicationContext(), "Máxima cantidad de jugadores alcanzada", Toast.LENGTH_SHORT).show();
                        }
                        c.close();
                    }
                }
                PICK_CONTACT = 0;
            }
        }
    }

    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
            Toast.makeText(AdminPanelActivity.this, mFormatter.format(date), Toast.LENGTH_SHORT).show();
            btnFechaHora.setText(mFormatter.format(date));
        }

        @Override
        public void onDateTimeCancel()
        {
            Toast.makeText(AdminPanelActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
        }
    };


    View.OnClickListener vaciarListaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            convocados.clear();
            convocadosAdapter.notifyDataSetChanged();
        }
    };

}