const mongoose = require('mongoose');
var Schema = mongoose.Schema;
const latLngSchema = require('../other/latLng')

const imageSchema = new mongoose.Schema({
    name:{type:String,required:true},
    lat_lng:{type:latLngSchema,default:{
        lat:null,
        lng:null
    }},
    time:{type:Date},
    device:{type:String},
    hidden_info:{type:String},
    status:{type:Number,default:1}
},{timestamps:true});


const SImage = mongoose.model('SImage', imageSchema);
module.exports = SImage;