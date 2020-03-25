const mongoose = require('mongoose');
var Schema = mongoose.Schema;


const accessTokenSchema = new mongoose.Schema({
    token: {
        type: String,
        required: true,
        unique: true
    },
    user: { type: Schema.ObjectId, ref: "User", required: true },


},{timestamps:true});

// encrypt the password using 'bcryptjs'
// Mongoose -> Document Middleware

const AccessToken = mongoose.model('AccessToken', accessTokenSchema);
module.exports = AccessToken;

