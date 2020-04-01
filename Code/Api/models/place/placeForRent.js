const mongoose = require('mongoose');
const validator = require('validator');
var Schema = mongoose.Schema;
const latLngSchema = new mongoose.Schema({
    lat:{
        type:Number
    },
    lng:{
        type:Number
    }
})

const placeForRentSchema = new mongoose.Schema({
    name: {
        type: String,
        required: [true, 'Hãy nhập tên']
    },
    code:{
        type: String,
        unique:true
    },
    address: {
        type: String,
        required:true,
    
    },
    lat_lng:{
        type:latLngSchema,
    
    },
    price:{
        type:Number,
    },
    time_unit:{
        type:String,
    },
    imgs:[{
        type: String
    }],
    creater:{ type: Schema.ObjectId, ref: "Account" },
    owner:{ type: Schema.ObjectId, ref: "PartyB" }
    ,status:{
        type: Number,
        default:1
    },
},{timestamps:true});




const PlaceForRent = mongoose.model('PlaceForRent', placeForRentSchema);
module.exports = PlaceForRent;