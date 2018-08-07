package rohit.bman.thelazybaboons;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rohit.bman.thelazybaboons.api.RetrofitClient;
import rohit.bman.thelazybaboons.models.DomainResponse;
import rohit.bman.thelazybaboons.models.LoginResponse;
import rohit.bman.thelazybaboons.models.RegisterResponse;
import rohit.bman.thelazybaboons.models.RequestError;

public class LoginActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener,
        RegisterFragment.OnFragmentInteractionListener {

    DbHelper dbHelper;
    SharedPreferences sp;
    ProgressBar progressBar;
    FrameLayout flContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DbHelper(this);

        progressBar = findViewById(R.id.pb_activity_login);
        flContainer = findViewById(R.id.fl_container_activity_login);

        sp = getPreferences(MODE_PRIVATE);

        boolean isSavedDomainsAtFirstLoad = sp.getBoolean(Constants.SAVED_DOMAINS_AT_FIRST_LOAD, false);

        if (!isSavedDomainsAtFirstLoad) {
            progressBar.setVisibility(View.VISIBLE);
            flContainer.setVisibility(View.GONE);
            loadDomainsAndSave();
        } else {
            loadLoginFragment();
        }


    }

    private void loadLoginFragment() {
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container_activity_login, new LoginFragment())
                .commit();
    }

    private void loadDomainsAndSave() {


        RetrofitClient.getApis().getDomains().enqueue(new Callback<DomainResponse>() {
            @Override
            public void onResponse(Call<DomainResponse> call, Response<DomainResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    DomainResponse domainResponse = response.body();

                    if (domainResponse.isSuccessFul()) {
                        boolean isSaved = dbHelper.saveDomains(domainResponse.getDomainList().getDomains());

                        if (isSaved) {
                            SharedPreferences.Editor editor = sp.edit();

                            editor.putBoolean(Constants.SAVED_DOMAINS_AT_FIRST_LOAD, true);

                            editor.apply();
                        }
                    }

                }

                loadLoginFragment();

                progressBar.setVisibility(View.GONE);
                flContainer.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<DomainResponse> call, Throwable t) {
                loadLoginFragment();
                progressBar.setVisibility(View.GONE);
                flContainer.setVisibility(View.VISIBLE);

            }
        });


    }


    @Override
    public void onStartRegister() {
        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fl_container_activity_login, new RegisterFragment()).commit();

    }

    @Override
    public void onLogin(int domain, String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.getApis().loginUser(domain, email, password)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            LoginResponse loginResponse = response.body();

                            if (loginResponse.isSuccessFul()) {

                                Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                SPUtil.setSPString(LoginActivity.this, Constants.TOKEN_ID, loginResponse.getLoginData().getToken(),
                                        Constants.USER_DATA);
                                startHomeActivity();
                            } else {
                                Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                List<RequestError> errors = loginResponse.getRequestErrors();

                                if (errors != null) {
                                    for (RequestError requestError :
                                            errors) {
                                        Toast.makeText(LoginActivity.this, requestError.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        }
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


    @Override
    public void onRegisterUser(int domainId, String name, String emailId, String password) {

        progressBar.setVisibility(View.VISIBLE);

        RetrofitClient.getApis().registerUser(domainId, emailId, name, password)
                .enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            RegisterResponse registerResponse = response.body();

                            if (registerResponse.isSuccessFul()) {

                                Toast.makeText(LoginActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                SPUtil.setSPString(LoginActivity.this, Constants.TOKEN_ID, registerResponse.getRegisterData().getToken(),
                                        Constants.USER_DATA);
                                startHomeActivity();
                            } else {
                                //Toast.makeText(LoginActivity.this, R.string.error_occurred, Toast.LENGTH_SHORT).show();

                                List<RequestError> errors = registerResponse.getRequestErrors();

                                for (RequestError requestError :
                                        errors) {
                                    Toast.makeText(LoginActivity.this, requestError.getMsg(), Toast.LENGTH_SHORT).show();
                                }

                            }

                        }
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);

        finish();


    }
}
