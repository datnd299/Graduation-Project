const mongoose = require('mongoose');
const validator = require('validator');
var Schema = mongoose.Schema;

const partyBSchema = new mongoose.Schema({
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
    accs:{ type: Schema.ObjectId, ref: "User" }

});


const PartyB = mongoose.model('PartyB', partyBSchema);
module.exports = PartyB;