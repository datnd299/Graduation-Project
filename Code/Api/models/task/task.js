const mongoose = require('mongoose');
const validator = require('validator');
var Schema = mongoose.Schema;

const setupTaskSchema = new mongoose.Schema({
    accs:[{ type: Schema.ObjectId, ref: "Account",required:true }],
    signboards:[{ type: Schema.ObjectId, ref: "Signboard",required:true }],
    place_rental:{ type: Schema.ObjectId, ref: "PlaceRental",required:true },
    status:{
        type: Number,
        default:1
    }
})
const checkTaskSchema = new mongoose.Schema({
    accs:[{ type: Schema.ObjectId, ref: "Account",required:true }],
    place_rental:[{ type: Schema.ObjectId, ref: "PlaceRental",required:true }],
    status:{
        type: Number,
        default:1
    }
})
const placeWithFeeSchema = new mongoose.Schema({
    place_rental:{ type: Schema.ObjectId, ref: "PlaceRental",required:true },
    times:[{type:Number,require:true}],
    amount:{type:Number},
    time_unit:{type:String},
    status:{
        type: Number,
        default:1
    }
})
const feeTaskSchema = new mongoose.Schema({
    accs:[{ type: Schema.ObjectId, ref: "Account",required:true }],
    feeDetail:[placeWithFeeSchema],
    
    status:{
        type: Number,
        default:1
    }
})
const repeatDetailSchema = new mongoose.Schema({
    fz_unit:{type:String,required:true},
    fz_vals:[{type:Number,required:true}],
    remind_before:{type:Number,required:true}, //dv giây
    random:{type:Boolean,required:true},
    time_to_complete:{type:Number,required:true}, //dv giây

});
const reportTaskSchema = new mongoose.Schema({
    place_rental:{ type: Schema.ObjectId, ref: "PlaceRental",required:true },
    repeat_type:{
        type:String,
        enum:['once','repeat'],
        required:true
    },
    root_task:{ type: Schema.ObjectId, ref: "TaskSchema"},
    repeat_detail:repeatDetailSchema,
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
    check_task:checkTaskSchema,
    fee_task:feeTaskSchema,
    report_task:reportTaskSchema,
    acc_created:{ type: Schema.ObjectId, ref: "Account" },
    pt_a:{ type: Schema.ObjectId, ref: "PartyA" },
    note:{
        type:String,
        required:true
    },
    start:{
        type:Date,
        required:true
    },
    end:{
        type:Date,
        required:true
    },
    status:{
        type: Number,
        default:1
    },
},{timestamps:true});




const TaskSchema = mongoose.model('Task', taskSchema);
module.exports = TaskSchema;