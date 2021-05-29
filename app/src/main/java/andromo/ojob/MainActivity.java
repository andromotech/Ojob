package andromo.ojob;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;
import java.util.ArrayList;
import java.util.List;
import andromo.ojob.RestCall.Client;
import andromo.ojob.RestCall.Server;
import andromo.ojob.adapter.NsAdp;
import andromo.ojob.model.NsModel;
import andromo.ojob.model.NsView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

public class MainActivity extends AppCompatActivity {
    private InterstitialAd interstitialAd ;
    private AdView adView;
    private AdView adView1;
    private AdView adView2;
    DrawerLayout drawlV;
    private List<NsModel> spllist;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AudienceNetworkAds.initialize(this);
        interstitialAd = new InterstitialAd(this, "260652328292002_260654271625141");
        adView = new AdView(this, "260652328292002_260677238289511", AdSize.BANNER_HEIGHT_50);
        adView1 = new AdView(this, "260652328292002_260677238289511", AdSize.BANNER_HEIGHT_50);
        adView2 = new AdView(this, "260652328292002_260677238289511", AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        LinearLayout adContainer1 = (LinearLayout) findViewById(R.id.banner_container1);
        LinearLayout adContainer2 = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adContainer1.addView(adView1);
        adContainer2.addView(adView2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button enbtn = (Button)findViewById(R.id.btn);
        enbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.sm05.co.in/job/en.pdf";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(url));
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(browserIntent);
            }
        });
        loadJSON1();
        drawlV = (DrawerLayout) findViewById(R.id.drawerLayout);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
        collapsingToolbar.setTitle("");
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "1";
        String channel2 = "2";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(channelId,
                    "Channel 1", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription("sm05.co.in");
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel);

            NotificationChannel notificationChannel2 = new NotificationChannel(channel2,
                    "Channel 2", NotificationManager.IMPORTANCE_MIN);

            notificationChannel.setDescription("sm05.co.in");
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel2);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ja) {
            Intent intent = new Intent(this, JobAlert.class);
            startActivity(intent);
        }
        if (id == R.id.prv) {
            Intent intent = new Intent(this, Prv.class);
            startActivity(intent);
        }
        if (id == R.id.rate) {
            String myUrl = "http://play.google.com/store/apps/details?id=andromo.ojob";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(myUrl)));
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadJSON1(){
        try{
            Client Clt = new Client();
            Server apiServer =
                    Clt.getClient().create(Server.class);
            Call<NsView> call = apiServer.getEightBook();
            call.enqueue(new Callback<NsView>() {
                @Override
                public void onResponse(Call<NsView> call, Response<NsView> response) {
                    List<NsModel> items = response.body().getEightBook();
                    spllist = new ArrayList<>();
                    NsAdp firstAdapter = new NsAdp(getApplicationContext(), items);
                    MultiSnapRecyclerView firstRecyclerView = (MultiSnapRecyclerView)findViewById(R.id.recycler_view);
                    LinearLayoutManager firstManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    firstRecyclerView.setLayoutManager(firstManager);
                    firstRecyclerView.setAdapter(firstAdapter);
                }

                @Override
                public void onFailure(Call<NsView> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MainActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed

                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback

            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback

            }

        });

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown

        interstitialAd.loadAd();
        adView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Toast.makeText(MainActivity.this, "Error: " + adError.getErrorMessage(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        });

        adView.loadAd();
        adView1.loadAd();
        adView2.loadAd();

    }

    @Override
    protected void onDestroy() {
        if (interstitialAd != null & adView != null)  {
            interstitialAd.destroy();
            adView.destroy();
        }
        super.onDestroy();
    }


}


