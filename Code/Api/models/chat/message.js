const mongoose = require('mongoose');
const validator = require('validator');
var Schema = mongoose.Schema;


const messageSchema = new mongoose.Schema({
    room:{type: Schema.ObjectId, ref: "Room" },
    sender:{type: Schema.ObjectId, ref: "Account" },
    type:{type:String,
        enum:['public','private']},
    content: {type:String, default:null},
    status:{
        type:Number,
        default:1
    }
},{timestamps:true});


const Message = mongoose.model('Message', messageSchema);
module.exports = Message;