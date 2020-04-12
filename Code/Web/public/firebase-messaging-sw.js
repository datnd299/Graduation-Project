importScripts('https://www.gstatic.com/firebasejs/7.13.2/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/7.13.2/firebase-messaging.js');
var firebaseConfig = {

  apiKey: "AIzaSyD4fqLCVKpZNtW79ypTbsgg0pKiuP-osuQ",
  authDomain: "icpc1hn-8ed88.firebaseapp.com",
  databaseURL: "https://icpc1hn-8ed88.firebaseio.com",
  projectId: "icpc1hn-8ed88",
  storageBucket: "icpc1hn-8ed88.appspot.com",
  messagingSenderId: "224723205949",
  appId: "1:224723205949:web:f511eb55f2ace1fe"

};
console.log('ádfsdf');

firebase.initializeApp(firebaseConfig);
const messaging = firebase.messaging();



messaging.setBackgroundMessageHandler(function (payload) {

  console.log(payload);

  const channel = new BroadcastChannel('firebase-messaging-sw.js');
  channel.postMessage(payload);
  console.log('a');
  
  




  var notificationTitle = payload.data.title;
  var notificationOptions = {
    body: payload.data.body,
    icon: 'https://icpc1hn.work/img/cpc1hn-logo.png',
    data: { url: payload.data.link }, //the url which we gonna use later
    actions: [{ action: "open_url", title: "Truy cập trang web" }],
    click_action: payload.data.link
  };

  var noti = self.registration.showNotification(notificationTitle,
    notificationOptions);

  return noti
});





self.addEventListener('notificationclick', function (event) {

  switch (event.action) {
    case 'open_url':
      clients.openWindow(event.notification.data.url); //which we got from above


      break;
    case 'any_other_action':
      clients.openWindow("https://icpc1hn.work");
      break;
  }
}
  , false);