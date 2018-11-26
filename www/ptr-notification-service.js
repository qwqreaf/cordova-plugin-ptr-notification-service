var exec = require('cordova/exec');

var ptrNotificationService = {
    start:function() {
        exec(null, null, "", "start", []);
    },
    stop:function() {
        exec(null, null, "", "stop", []);
    }
};

module.exports = ptrNotificationService;