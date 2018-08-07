package rohit.bman.thelazybaboons;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class LoginFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private EditText etEmail, etPassword;
    private Spinner spDomain;
    private Button btnLogin, btnRegister;

    private DbHelper dbHelper;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etEmail = view.findViewById(R.id.et_email_fragment_login);
        etPassword = view.findViewById(R.id.et_password_fragment_login);
        spDomain = view.findViewById(R.id.spinner_domain_fragment_login);
        btnLogin = view.findViewById(R.id.btn_login_fragment_login);
        btnRegister = view.findViewById(R.id.btn_register_fragment_login);

        dbHelper = new DbHelper(getContext());


        spDomain.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, dbHelper.getDomainNames()));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etEmail.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();
                int id = dbHelper.getIdOfDomain(String.valueOf(spDomain.getSelectedItem()).trim());

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && id != 0) {
                    mListener.onLogin(id, email, pass);
                } else {
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getContext(), R.string.please_enter_email, Toast.LENGTH_SHORT).show();
                    }
                    if (TextUtils.isEmpty(pass)) {
                        Toast.makeText(getContext(), R.string.please_enter_password, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onStartRegister();
            }
        });


        return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onStartRegister();

        void onLogin(int domain, String email, String password);
    }
}
