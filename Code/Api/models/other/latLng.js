const mongoose = require('mongoose');
var Schema = mongoose.Schema;
const latLngSchema = new mongoose.Schema({
    lat:{
        type:Number
    },
    lng:{
        type:Number
    }
})

// encrypt the password using 'bcryptjs'
// Mongoose -> Document Middleware

// const AccessToken = mongoose.model('AccessToken', accessTokenSchema);
module.exports = latLngSchema;