const mongoose = require('mongoose');
var Schema = mongoose.Schema;

const socketSchema = new mongoose.Schema({
    acc:{ type: Schema.ObjectId, ref: "Account" },
    socket:{type:String},
    status:{
        type: Number,
        default:1
    },
},{timestamps:true});


const Socket = mongoose.model('Socket', socketSchema);
module.exports = Socket;