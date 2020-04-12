const mongoose = require('mongoose');
const validator = require('validator');
var Schema = mongoose.Schema;

const setupTaskSchema = new mongoose.Schema({
    accs:[{ type: Schema.ObjectId, ref: "Account" }],
    status:{
        type: Number,
        default:1
    }
})

const taskSchema = new mongoose.Schema({
    type: {
        type: String,
        required: [true, 'Hãy nhập tên'],
        enum:['setup','check','report','fee']
    },
    setup_task:setupTaskSchema,
    acc_created:{ type: Schema.ObjectId, ref: "Account" },
    pt_a:{ type: Schema.ObjectId, ref: "PartyA" },
    place_rental_id:{ type: Schema.ObjectId, ref: "PlaceRental" },
    status:{
        type: Number,
        default:1
    },
},{timestamps:true});




const PlaceRental = mongoose.model('PlaceRental', placeRentalSchema);
module.exports = PlaceRental;