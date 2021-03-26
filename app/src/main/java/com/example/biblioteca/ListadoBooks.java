package com.example.biblioteca;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.biblioteca.Config.config;
import com.example.biblioteca.Inteface.retrofitInterface;
import com.example.biblioteca.Modelos.book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListadoBooks extends AppCompatActivity {
    EditText editCText;
    Button btnCconsultar;
    ListView lvCbooks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_books);
        editCText = findViewById(R.id.edtCbuscar);
        btnCconsultar = findViewById(R.id.btnCconsultar);
        lvCbooks = findViewById(R.id.LvcBooks);
        getBooks();

        btnCconsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editCText.getText().toString().equals("")) {
                    getByFiltro(editCText.getText().toString());
                }
            }
        });

    }

    private void getBooks(){
        retrofitInterface cliente = config.getRetrofit().create(retrofitInterface.class);
        Call<List<book>> call = cliente.getBooks("books");
        call.enqueue(new Callback<List<book>>() {
            @Override
            public void onResponse(Call<List<book>> call, Response<List<book>> response) {
                HashMap<String,String> diccionario = new HashMap<>();
                //ArrayList<String> data = new ArrayList<String>();
                for (book databook :response.body()){
                    diccionario.put(databook.getId_book(),databook.getTitle());
                }
                List<HashMap<String,String>> listDiccionario = new ArrayList<>();
                SimpleAdapter adaptador= new SimpleAdapter(getApplicationContext(),listDiccionario,R.layout.listapersonal,
                        new String[]{"id_book","title"},
                        new int[]{R.id.txtCidbook,R.id.txtCtitle});
                Iterator veces = diccionario.entrySet().iterator();
                while (veces.hasNext()){
                    HashMap<String,String> resultado = new HashMap<>();
                    Map.Entry par = (Map.Entry) veces.next();
                    resultado.put("id_book",par.getKey().toString());
                    resultado.put("title",par.getValue().toString());
                    listDiccionario.add(resultado);
                }
                lvCbooks.setAdapter(adaptador);




                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,data);
               // lvCbooks.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<book>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"algo no esta bien",Toast.LENGTH_SHORT).show();
            }
        });
    }//fin





    private void getByFiltro(String title) {
        retrofitInterface buscar = config.getRetrofit().create(retrofitInterface.class);
        Call<List<book>> call = buscar.getBooks("buscar/" + title );
        call.enqueue(new Callback<List<book>>() {
            @Override
            public void onResponse(Call<List<book>> call, Response<List<book>> response) {
                ArrayList<String> data = new ArrayList<String>();
                for (book databook :response.body()){
                    data.add(databook.getTitle());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,data);
                lvCbooks.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<book>> call, Throwable t) {

            }
        });
    }
}