const mongoose = require('mongoose');
var Schema = mongoose.Schema;

const nTokenSchema = new mongoose.Schema({
    token: {
        type: String,
        required: true,
        unique:true
    },
    acc:{ type: Schema.ObjectId, ref: "Account" }
    ,status:{
        type: Number,
        default:1
    },
},{timestamps:true});


const NToken = mongoose.model('NToken', nTokenSchema);
module.exports = NToken;