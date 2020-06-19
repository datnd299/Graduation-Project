const mongoose = require('mongoose');
const validator = require('validator');
var Schema = mongoose.Schema;


const roomSchema = new mongoose.Schema({
    pt_a:{ type: Schema.ObjectId, ref: "PartyA" },
    pt_b:{ type: Schema.ObjectId, ref: "PartyB" },
    admin:{type: Schema.ObjectId, ref: "Account" },
    status:{
        type:Number,
        default:1
    }
},{timestamps:true});


const Room = mongoose.model('Room', roomSchema);
module.exports = Room;