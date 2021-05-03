package com.example.ramonExercicio2;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.ramonExercicio2.CadastrarFragment.getBlacklist;
import static com.example.ramonExercicio2.CadastrarFragment.getMilliFromDate;
import static com.example.ramonExercicio2.CadastrarFragment.inserePadroes;

public class EditarFragment extends Fragment {

    private EditText etNome;
    private EditText etFone;
    private Button btEditar;
    private CustomerService customerService;

    private Customer customer;

    private EditText etDate;

    private TextView edBlacklist;
    private RadioGroup radioGroup;
    private RadioButton radioTrue;
    private RadioButton radioFalse;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cadastrar, null);
        etNome = v.findViewById(R.id.et_nome);
        etDate = v.findViewById(R.id.et_date);
        etFone = v.findViewById(R.id.et_fone);
        btEditar = v.findViewById(R.id.bt_cadastrar);
        btEditar.setText("Editar");
        edBlacklist = v.findViewById(R.id.blacklist_txt);
        radioGroup = v.findViewById(R.id.rd_group);
        radioTrue = v.findViewById(R.id.rd_true);
        radioFalse = v.findViewById(R.id.rd_false);

        inserePadroes(etDate, etFone);

        Bundle bundleCustomer = getArguments();
        customer = (Customer) bundleCustomer.getSerializable("customer");
        customerService = RetrofitUtils.retrofit.create(CustomerService.class);

        btEditar.setOnClickListener(v1 -> {
            customer.setName(etNome.getText().toString());
            customer.setPhoneNumber(etFone.getText().toString());
            customer.setBirthDateInMillis(getMilliFromDate(etDate.getText().toString()));
            customer.setBlacklist(getBlacklist(radioGroup.getCheckedRadioButtonId()));
            customerService.atualizar(customer.getId(), customer).enqueue(new Callback<Customer>() {
                @Override
                public void onResponse(Call<Customer> call, Response<Customer> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(getContext(), "Customer editado com sucesso!", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getContext(), "Erro ao editar!"+response.errorBody(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Customer> call, Throwable t) {
                    Log.i("DEBUG", t.getMessage());
                    Toast.makeText(getContext(), "Servidor fora do ar, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                }
            });
        });

        return v;
    }
}
