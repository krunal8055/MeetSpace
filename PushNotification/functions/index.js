//'user-strict'
const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendNotification=functions.database.ref('/User/{user_id}/MyBooking/{booking_id}/MyInviteList/{invite_id}').onCreate((snap,context) => {
	const data = snap.val();
	 const Invited_User_token_id = data.Invited_User_token_id;
	 const Message = data.Message;
	 const payload = {
        notification: {
            title:"Invitation",
            body: "You have an Invitation from MeetSpace User.For More Info Click Here!",
            sound: "default"
        },
        data:{
        	token: Invited_User_token_id
        }
    };
    //console.log(payload);
     	console.log("Notification Sent!");
		  return admin.messaging().sendToDevice(Invited_User_token_id, payload);
});