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

import com.github.rtoshiro.util.format.MaskFormatter;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.pattern.MaskPattern;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastrarFragment extends Fragment {

    private EditText etNome;
    private EditText etFone;
    private Button btCadastrar;
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
       edBlacklist = v.findViewById(R.id.blacklist_txt);
       radioGroup = v.findViewById(R.id.rd_group);
       radioTrue = v.findViewById(R.id.rd_true);
       radioFalse = v.findViewById(R.id.rd_false);

       inserePadroes(etDate, etFone);
       btCadastrar = v.findViewById(R.id.bt_cadastrar);

       customerService = RetrofitUtils.retrofit.create(CustomerService.class);


       btCadastrar.setOnClickListener(view -> {
           customer = new Customer();
           customer.setName(etNome.getText().toString());
           customer.setBirthDateInMillis(getMilliFromDate(etDate.getText().toString()));
           customer.setBlacklist(getBlacklist(radioGroup.getCheckedRadioButtonId()));
           customer.setPhoneNumber(etFone.getText().toString());
            customerService.adicionar(customer).enqueue(new Callback<Customer>() {
                @Override
                public void onResponse(Call<Customer> call, Response<Customer> response) {
                    if(response.isSuccessful()) {
                        limpaEditTexts();
                        Toast.makeText(getContext(), "Customer cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getContext(), "Erro ao cadastrar!"+response.errorBody(), Toast.LENGTH_LONG).show();
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

    public static boolean getBlacklist(Integer id) {
        return id == R.id.rd_true;
    }

    public static long getMilliFromDate(String dateFormat) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = formatter.parse(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static void inserePadroes(EditText etDate, EditText etFone) {
        MaskPattern mp03 = new MaskPattern("[0-3]");
        MaskPattern mp09 = new MaskPattern("[0-9]");
        MaskPattern mp01 = new MaskPattern("[0-1]");
        MaskFormatter mf = new MaskFormatter("[0-3][0-9]/[0-1][0-9]/[0-9][0-9][0-9][0-9]");
        mf.registerPattern(mp01);
        mf.registerPattern(mp03);
        mf.registerPattern(mp09);
        MaskTextWatcher mswData = new MaskTextWatcher(etDate, mf);
        etDate.addTextChangedListener(mswData);

        SimpleMaskFormatter maskTelefone = new SimpleMaskFormatter("(NN)NNNNNNNNN");
        MaskTextWatcher mswTelefone = new MaskTextWatcher(etFone, maskTelefone);
        etFone.addTextChangedListener(mswTelefone);
    }

    private void limpaEditTexts() {
       etNome.setText("");
       etDate.setText("");
       etFone.setText("");
    }

}
