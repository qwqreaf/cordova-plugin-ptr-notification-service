package tw.gov.tra.twtraffic.plugin;

import android.content.Intent;
import android.os.Bundle;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import tw.gov.tra.twtraffic.MainActivity;

/**
 * Created by ChrisTsai on 2018/11/26.
 */

public class NotificationServicePlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
            switch (action) {
                case "start":
                    String title = "null".equals(args.getString(0)) ? null : args.getString(0);
                    String message = "null".equals(args.getString(1)) ? null : args.getString(1);
                    startService(title, message);
                    break;

                case "stop":
                    stopService();
                    break;
            }
            callbackContext.success();
            return true;
        } catch (Exception e) {
            callbackContext.error(e.getMessage());
            return false;
        }
    }


    private void startService(String title, String message) {
        Intent intent = new Intent(cordova.getContext(), NotificationService.class);
        intent.setAction(NotificationService.ACTION_START);
        Bundle extras = intent.getExtras();
        extras.putString("title", title);
        extras.putString("message", message);
        cordova.getContext().startService(intent);
    }

    private void stopService() {
        Intent intent = new Intent(cordova.getContext(), NotificationService.class);
        intent.setAction(NotificationService.ACTION_STOP);
        cordova.getContext().stopService(intent);
    }

}
