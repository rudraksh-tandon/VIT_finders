package com.ieeeias.vit_finders.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.ieeeias.vit_finders.MainActivity;
import com.ieeeias.vit_finders.R;
import com.ieeeias.vit_finders.MainActivity2;
import com.ieeeias.vit_finders.utils.PrefManager;

public class SplashScreenActivity extends AppCompatActivity {
    Context context = null;
    PrefManager prefManager;
    //private final int updatereqcode=1600;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.splash_screen_activity);
        prefManager = new PrefManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(prefManager.getLogin()){
                    intent = new Intent(SplashScreenActivity.this, MainActivity2.class);
                }
                else {
                    intent = new Intent(SplashScreenActivity.this,MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 3000);


    }
//    private void appupdate(){
//        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
//
//// Returns an intent object that you use to check for an update.
//        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
//
//// Checks that the platform will allow the specified type of update.
//        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
//            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
//                    // This example applies an immediate update. To apply a flexible update
//                    // instead, pass in AppUpdateType.FLEXIBLE
//                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
//                // Request the update.
//                try {
//
//
//                appUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.IMMEDIATE
//                        ,SplashScreenActivity.this,updatereqcode);
//                }
//                catch (IntentSender.SendIntentException exception){
//                    Toast.makeText(this, "Calling in app update", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }
//    protected void onActivityResult(int requestcc, int resultcode, @Nullable Intent data) {
//
//        super.onActivityResult(requestcc, resultcode, data);
//        if(data==null)return;
//        if(requestcc==updatereqcode){
//            Toast.makeText(this,"Downloading started",Toast.LENGTH_LONG).show();
//
//        }
//        if(requestcc==updatereqcode){
//            Toast.makeText(this,"failed to make request",Toast.LENGTH_LONG).show();
//
//        }
//
//    }
}















//public class SplashScreenActivity extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
//        setContentView(R.layout.splash_screen_activity);
//        try {
//            VideoView videoHolder = new VideoView(this);
//            setContentView(videoHolder);
//            Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.app_intro);
//            videoHolder.setVideoURI(video);
////            videoHolder.start();
//
//            videoHolder.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                public void onCompletion(MediaPlayer mp) {
//                    jump();
//                }
//            });
//            videoHolder.start();
//        } catch (Exception ex) {
//            jump();
//        }
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        jump();
//        return true;
//    }
//
//    private void jump() {
//        if (isFinishing())
//            return;
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
//    }
//}