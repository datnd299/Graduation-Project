const mongoose = require('mongoose');

var Schema = mongoose.Schema;


const notificationSchema = new mongoose.Schema({
    title: {
        type: String
    },
    content: {
        type: String
    },
    type:{
        type: String
    },
    to:{ type: Schema.ObjectId, ref: "Account" },
    to_p_a:{ type: Schema.ObjectId, ref: "PartyA" },
    to_p_b:{ type: Schema.ObjectId, ref: "PartyB" },

    from:{ type: Schema.ObjectId, ref: "Account" },

    accs:[{ type: Schema.ObjectId, ref: "Account" }],
    info:{
        type:Object
    },
    status:{
        type: Number,
        default:1
    },
},{timestamps:true});




const Notification = mongoose.model('Notification', notificationSchema);
module.exports = Notification;