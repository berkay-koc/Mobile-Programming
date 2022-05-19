package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if("com.example.EXAMPLE_ACTION".equals(intent.getAction())){
            String receivedText = intent.getStringExtra("com.example.EXTRA_TEXT");
            if(receivedText.equals("masada")){
                System.out.println("Volume Up");
                
            }
            else{
                System.out.println("Volume Down");
            }
        }
    }
}
