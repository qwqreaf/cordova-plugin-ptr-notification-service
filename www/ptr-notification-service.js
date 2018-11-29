var exec = require('cordova/exec');

var ptrNotificationService = {
    start:function(title, message, bigText, openUrl) {
        exec(null, null, "PtrNotificationService", "start", [title, message, bigText, openUrl]);
    },
    stop:function() {
        exec(null, null, "PtrNotificationService", "stop", []);
    }
};

module.exports = ptrNotificationService;
