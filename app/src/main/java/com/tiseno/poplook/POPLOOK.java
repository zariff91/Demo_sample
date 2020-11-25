package com.tiseno.poplook;

import android.app.Application;
import android.content.Context;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.karumi.dexter.Dexter;
import com.useinsider.insider.Insider;
//import com.useinsider.insider.config.Geofence;
//import com.useinsider.insider.config.Push;

/**
 * Created by rahnrazamai on 07/09/16.
 */
public class
POPLOOK extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Dexter.initialize(this);

        Insider.Instance.init(this,"poplook");
        Insider.Instance.setSplashActivity(SplashActivity.class);

//        Insider.Instance.init(this,
//                /* Insider Partner Name (Should be lowercase) */
//                "poplook",
//                /* Sender ID */
//                "549922517099",
//                /* Your Landing Class Path */
//                MainActivity.class,
//                /* Push Collapsing Option */
//                /* WILL_NOT_COLLAPSE, WILL_COLLAPSE */
//                Push.WILL_COLLAPSE,
//         /* Geofence Options:
//            NOT_ENABLED, EVERY_10_SECONDS,
//            EVERY_30_SECONDS, EVERY_60_SECONDS,EVERY_90_SECONDS,
//            EVERY_120_SECONDS, EVERY_180_SECONDS,
//            EVERY_240_SECONDS ,EVERY_300_SECONDS
//           We recommend choosing 60 seconds.
//         */
//                Geofence.EVERY_60_SECONDS
//        );

    }
}
