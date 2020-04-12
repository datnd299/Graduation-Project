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

const placeRentalSchema = new mongoose.Schema({
    name: {
        type: String,
        required: [true, 'Hãy nhập tên']
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
    start:{
        type:Date,
    },
    end:{
        type:Date
    },
    place_id:{ type: Schema.ObjectId, ref: "PlaceForRent" },
    account_renter:{ type: Schema.ObjectId, ref: "Account" },
    party_renter:{ type: Schema.ObjectId, ref: "PartyA" },
    status:{
        type: Number,
        default:1
    },
},{timestamps:true});




const PlaceRental = mongoose.model('PlaceRental', placeRentalSchema);
module.exports = PlaceRental;