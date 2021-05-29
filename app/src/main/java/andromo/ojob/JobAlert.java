package andromo.ojob;


import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

public class JobAlert extends AppCompatActivity {
    private InterstitialAd interstitialAd ;
    @SuppressLint("MissingPermission")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview111);
        AudienceNetworkAds.initialize(this);
        interstitialAd = new InterstitialAd(this, "260652328292002_260654271625141");
        WebView wb = (WebView) findViewById(R.id.webview);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.getSettings().setDisplayZoomControls(true);
        wb.getSettings().setAllowFileAccess(true);
        wb.getSettings().setUseWideViewPort(true);
        wb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView abc, String url) {
                if(url.endsWith(".pdf")){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(url), "application/pdf");
                    try{
                        abc.getContext().startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                    }
                } else {
                    abc.loadUrl(url);
                }
                return true;
            }

        });
        wb.loadUrl("http://www.sm05.co.in/job/oj.html");

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

    }
    @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }
}

