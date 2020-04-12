var PROJECT_ID = 'icpc1hn-8ed88';
var HOST = 'https://fcm.googleapis.com';
var { google } = require('googleapis');
var PATH = '/v1/projects/' + PROJECT_ID + '/messages:send';
var PATH2 = '/fcm/notification';
var MESSAGING_SCOPE = 'https://www.googleapis.com/auth/firebase.messaging';
var SERVER_KEY= 'AAAANFKL_z0:APA91bEFkt0XxBHSUoa2us3z34CTzGEqA367mE6ohwEDiDA1X0EeYvRnkWZATTyInwD20GIBvAFrfIVboFBLRQ4J1sqcusv_efnMTqCXe2C44chokTdP8l8nHZoT_tdq_gBZ6DPAm6J6';
var SCOPES = [MESSAGING_SCOPE];
var key = require('./credentials.json');
const https = require('https');
const request = require('request');
var getAccessToken = function () {
    return new Promise(function (resolve, reject) {

        var jwtClient = new google.auth.JWT(
            key.client_email,
            null,
            key.private_key,
            SCOPES,
            null
        );
        jwtClient.authorize(function (err, tokens) {
            if (err) {
                reject(err);
                return;
            }
            resolve(tokens.access_token);
        });
    })
}
function buildCommonMessage({token,title,body}) {
    return JSON.stringify({
        "message": {
           
            'token':token,
            "data" : {
                "body" : body,
                "title" : title,
                
            },
            
        }
      });
}
var sendToClient = function (messObj) {
    console.log(messObj);
    
    getAccessToken().then(function (accessToken) {
        var options = {
            url: HOST+PATH,
            body:buildCommonMessage(messObj),
            method: 'POST',
            // [START use_access_token]
            headers: {
                'Authorization': 'Bearer ' + accessToken
            }
            // [END use_access_token]
        };
        request(options, (err,res,body)=>{
            
           
            console.log(body);
            
            
        });
        
    });
}
module.exports = {
    PROJECT_ID: PROJECT_ID,
    HOST: HOST,
    SERVER_KEY:SERVER_KEY,
    PATH:PATH,
    GetAccessToken: getAccessToken,
    SendToClient:sendToClient
}