package tw.gov.tra.twtraffic.plugin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;


/**
 * Created by ChrisTsai on 2018/11/26.
 */

public class NotificationServicePlugin extends CordovaPlugin {

    private final static String TAG = "NotificationService";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
            switch (action) {
                case "start":
                    String title = "null".equals(args.getString(0)) ? null : args.getString(0);
                    String message = "null".equals(args.getString(1)) ? null : args.getString(1);
                    String bigText = "null".equals(args.getString(2)) ? null : args.getString(2);
                    String openUrl = "null".equals(args.getString(3)) ? null : args.getString(3);
                    startService(title, message, bigText, openUrl);
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


    private void startService(String title, String message, String bigText, String openUrl) {
        Context context = cordova.getContext();
        try {
            Intent intent = new Intent(context, NotificationService.class);
            intent.setAction(NotificationService.ACTION_START);
            Bundle extras = new Bundle();
            extras.putString("title", title);
            extras.putString("message", message);
            extras.putString("bigText", bigText);
            extras.putString("openUrl", openUrl);
            intent.putExtras(extras);
            context.startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
//            fireEvent(Event.FAILURE, String.format("'%s'", e.getMessage()));
        }
    }

    private void stopService() {
        Intent intent = new Intent(cordova.getContext(), NotificationService.class);
        intent.setAction(NotificationService.ACTION_STOP);
        cordova.getContext().stopService(intent);
    }

}
