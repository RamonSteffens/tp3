package com.example.ramonExercicio2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView lvMenu;
    private String[] vetMenu = {"Cadastrar", "Listar", "Editar", "Excluir"};
    private FragmentManager fm = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.inicializaComponentes();
        ArrayAdapter<String> adapterMenu = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, vetMenu);
        lvMenu.setAdapter(adapterMenu);
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long l) {
                Fragment frag;
                FragmentTransaction ft = fm.beginTransaction();
                Bundle bundle;
                switch (posicao){
                    case 0:
                        frag = new CadastrarFragment();
                        ft.replace(R.id.ll_conteudo, frag);
                        ft.commit();

                        break;
                    case 1:
                        frag = new ListarFragment();
                        bundle = new Bundle();
                        bundle.putInt("menu", 1);
                        frag.setArguments(bundle);
                        ft.replace(R.id.ll_conteudo, frag);
                        ft.commit();
                        break;
                    case 2:
                        frag = new ListarFragment();
                        bundle = new Bundle();
                        bundle.putInt("menu", 2);
                        frag.setArguments(bundle);
                        ft.replace(R.id.ll_conteudo, frag);
                        ft.commit();
                        break;
                    case 3:
                        frag = new ListarFragment();
                        bundle = new Bundle();
                        bundle.putInt("menu", 3);
                        frag.setArguments(bundle);
                        ft.replace(R.id.ll_conteudo, frag);
                        ft.commit();
                        break;
                }
            }
        });

    }

    private void inicializaComponentes() {
        this.lvMenu = findViewById(R.id.lv_menu);
    }


}