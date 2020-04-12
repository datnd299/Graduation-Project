const mongoose = require('mongoose');
const validator = require('validator');
var Schema = mongoose.Schema;


const signboardSchema = new mongoose.Schema({
    name:{
        type:String,
        required:true
    },
    code:{
        type:String,
        unique:true,
    },
    creater:{ type: Schema.ObjectId, ref: "Account" },
    owner:{ type: Schema.ObjectId, ref: "PartyA" },
    imgs:[{
        type: String
    }],
    note:{
        type:String
    },
    status:{
        type:Number,
        default:1
    }
},{timestamps:true});




const Signboard = mongoose.model('Signboard', signboardSchema);
module.exports = Signboard;