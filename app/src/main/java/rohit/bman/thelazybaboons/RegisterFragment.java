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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class RegisterFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private EditText etEmail, etName, etPassword;
    private Spinner spDomain;
    private Button btnRegister;
    private DbHelper dbHelper;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        dbHelper = new DbHelper(getContext());
        etEmail = view.findViewById(R.id.et_email_fragment_register);
        etName = view.findViewById(R.id.et_name_fragment_register);
        etPassword = view.findViewById(R.id.et_password_fragment_register);
        spDomain = view.findViewById(R.id.spinner_domain_fragment_register);
        btnRegister = view.findViewById(R.id.btn_register_fragment_register);

        spDomain.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, dbHelper.getDomainNames()));

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();

                int id = dbHelper.getIdOfDomain(String.valueOf(spDomain.getSelectedItem()).trim());

                if (!(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || id == 0)) {
                    mListener.onRegisterUser(id, name, email, pass);
                } else {
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getContext(), R.string.please_enter_email, Toast.LENGTH_SHORT).show();
                    }
                    if (TextUtils.isEmpty(pass)) {
                        Toast.makeText(getContext(), R.string.please_enter_password, Toast.LENGTH_SHORT).show();
                    }
                    if (TextUtils.isEmpty(name)) {
                        Toast.makeText(getContext(), R.string.please_enter_name, Toast.LENGTH_SHORT).show();
                    }
                }
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

        void onRegisterUser(int domainId, String name, String emailId, String password);

    }
}
