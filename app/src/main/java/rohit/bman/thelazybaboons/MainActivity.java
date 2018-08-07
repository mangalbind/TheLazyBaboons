package rohit.bman.thelazybaboons;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rohit.bman.thelazybaboons.api.RetrofitClient;
import rohit.bman.thelazybaboons.models.BaseResponse;

public class MainActivity extends AppCompatActivity {

    IntentIntegrator intentIntegrator;

    LinearLayout llPunchingAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llPunchingAttendance = findViewById(R.id.ll_punching_attendance);

        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.setOrientationLocked(true);


    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        llPunchingAttendance.setVisibility(View.VISIBLE);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null && result.getContents() != null) {

            String rawData = result.getContents();

            double latitiude = 19.675849d;

            double longitude = 72.8372829d;

            RetrofitClient.getApis().markAttendance(SPUtil.getSPString(this, Constants.TOKEN_ID, Constants.USER_DATA),
                    latitiude, longitude, rawData)
                    .enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                            if (response.body() != null && response.isSuccessful()) {

                                Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                            llPunchingAttendance.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {
                            Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            llPunchingAttendance.setVisibility(View.GONE);
                        }
                    });

        }

        llPunchingAttendance.setVisibility(View.GONE);

    }


    public void onScanForAttendace(View view) {

        intentIntegrator.initiateScan();

    }

    public void onCheckHolidays(View view) {


        startActivity(new Intent(this, HolidaysActivity.class));

    }
}
