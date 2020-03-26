const mongoose = require('mongoose');
const validator = require('validator');
const bcrypt = require('bcryptjs');
var Schema = mongoose.Schema;

const partyASchema = new mongoose.Schema({
    name: {
        type: String,
        required: [true, 'Please fill your name']
    },
    email: {
        type: String,
        required: [true, 'Please fill your email'],
        unique: true,
        lowercase: true,
        validate: [validator.isEmail, 'Please provide a valid email']
    },
    phone:{
        type:String,
        required :true,
        unique:true
    },
    province:{
        type:String,
    },
    district:{
        type:String,
    },
    address:{
        type: String
    },
    industry:{
        type: String,
    },
    accs:[{ type: Schema.ObjectId, ref: "User" }]

});


const PartyA = mongoose.model('PartyA', partyASchema);
module.exports = PartyA;