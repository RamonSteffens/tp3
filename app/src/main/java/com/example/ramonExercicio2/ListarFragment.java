package com.example.ramonExercicio2;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListarFragment extends Fragment {

    private ListView lvCustomer;
    private CustomerService customerService;
    private List<Customer> listaCustomer;
    private ArrayAdapter<Customer> adapterCustomer;
    private FragmentManager fm;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listar, null);
        lvCustomer = v.findViewById(R.id.lv_contatos);
        this.customerService = RetrofitUtils.retrofit.create(CustomerService.class);
        fm = getFragmentManager();

        customerService.listarCustomer().enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if(response.isSuccessful()) {
                    listaCustomer = response.body();
                    adapterCustomer = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listaCustomer);
                    lvCustomer.setAdapter(adapterCustomer);
                }else {
                    Toast.makeText(getContext(), "Erro ao buscar customers!"+response.errorBody(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Log.i("DEBUG", t.getMessage());
                Toast.makeText(getContext(), "Servidor fora do ar, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
            }
        });



        lvCustomer.setOnItemClickListener((adapterView, view, posicao, l) -> {
            Customer customerSelecionado = listaCustomer.get(posicao);
            Bundle bundle = getArguments();
            int opcaoMenu = bundle.getInt("menu");
            if(opcaoMenu==2) {
                FragmentTransaction ft = fm.beginTransaction();
                EditarFragment frag = new EditarFragment();
                Bundle bundleCustomer = new Bundle();
                bundleCustomer.putSerializable("customer", customerSelecionado);
                frag.setArguments(bundleCustomer);
                ft.replace(R.id.ll_conteudo, frag);
                ft.commit();


            }else if(opcaoMenu==3) {
                customerService.deletar(customerSelecionado.getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()) {
                            listaCustomer.remove(posicao);
                            adapterCustomer.notifyDataSetChanged();
                        }else {
                            Toast.makeText(getContext(), "Erro ao excluir o customer"+response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.i("DEBUG", t.getMessage());
                        Toast.makeText(getContext(), "Servidor fora do ar, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return v;
    }
}


