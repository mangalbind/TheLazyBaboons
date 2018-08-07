package rohit.bman.thelazybaboons;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rohit.bman.thelazybaboons.api.RetrofitClient;
import rohit.bman.thelazybaboons.models.Holiday;
import rohit.bman.thelazybaboons.models.HolidayResponse;

public class HolidaysActivity extends AppCompatActivity {

    RecyclerView rvHolidays;
    ProgressBar pbHolidays;
    HolidayAdapter holidayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holidays);

        rvHolidays = findViewById(R.id.rv_holidays);

        pbHolidays = findViewById(R.id.pb_holidays);

        holidayAdapter = new HolidayAdapter(new ArrayList<Holiday>());

        rvHolidays.setAdapter(holidayAdapter);
        rvHolidays.setLayoutManager(new LinearLayoutManager(this));
        rvHolidays.addItemDecoration(new DividerItemDecoration(this, 1));

        loadHolidays();


    }

    private void loadHolidays() {

        String token = SPUtil.getSPString(this, Constants.TOKEN_ID, Constants.USER_DATA);

        RetrofitClient.getApis().getHolidays(token)
                .enqueue(new Callback<HolidayResponse>() {
                    @Override
                    public void onResponse(Call<HolidayResponse> call, Response<HolidayResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            HolidayResponse holidayResponse = response.body();

                            if (holidayResponse.isSuccessFul()) {
                                holidayAdapter.updateData(holidayResponse.getHolidayData().getHolidays());
                            }

                        }
                        rvHolidays.setVisibility(View.VISIBLE);
                        pbHolidays.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<HolidayResponse> call, Throwable t) {
                        Toast.makeText(HolidaysActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        pbHolidays.setVisibility(View.GONE);
                    }
                });
    }
}
