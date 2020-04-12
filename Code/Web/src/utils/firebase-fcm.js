import firebase from "firebase/app";
import {registerNToken} from '@/api/notification'
import "firebase/messaging";
var firebaseConfig = {
  apiKey: "AIzaSyAYJsyBG-IUpoToLW2bW4cVhCPQz8vHNWw",
  authDomain: "icpc1hn-8ed88.firebaseapp.com",
  databaseURL: "https://icpc1hn-8ed88.firebaseio.com",
  projectId: "icpc1hn-8ed88",
  storageBucket: "icpc1hn-8ed88.appspot.com",
  messagingSenderId: "224723205949",
  appId: "1:224723205949:web:f511eb55f2ace1fe"
};
import {SetNToken} from '@/utils/auth'
var messaging = null;
var channel = null;
try {
  firebase.initializeApp(firebaseConfig);
  messaging = firebase.messaging();
  // messaging.usePublicVapidKey('BIHOgNy0pYFpU9ePc0kS0BsUZmg_feJN5jPiZH3TuMRpzzJb-S9pM2iBCMNJYkCkALqrvmbuWHN9U33vlDX2Z3U')
  var fcmInit = ()=>{ messaging
    .requestPermission()
    .then(function() {
  
      return messaging.getToken();
    })
    .then(function(token) {
        console.log(token);
        
      // setNToken(token);
      registerNToken({
        ntoken: token
      }).then(res => {
          console.log(res);
          SetNToken(token);
          
      });
    })
    .catch(function(err) {
      console.log(err);
    });
  }
  messaging.fcmInit = fcmInit;
  channel = new BroadcastChannel("firebase-messaging-sw.js");
} catch (error) {
  console.log(error);
  
}
 



 export var     FCM = messaging;
 export var     BChanel = channel;
