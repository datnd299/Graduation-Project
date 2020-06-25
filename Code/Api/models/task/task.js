const mongoose = require('mongoose');
const validator = require('validator');

var Schema = mongoose.Schema;

const latLngSchema = require('../other/latLng')

const signboardImagesReportSchema = new mongoose.Schema({
    s_id:{ type: Schema.ObjectId, ref: "Signboard",required:true },
    imgs:[{type:Schema.ObjectId, ref: "SImage"}],
    rating:{type:Number},
    note:{type:String},
    status:{
        type: Number,
        default:1
    }
})

const setupTaskSchema = new mongoose.Schema({
    accs:[{ type: Schema.ObjectId, ref: "Account",required:true }],
    signboards:[{ type: Schema.ObjectId, ref: "Signboard",required:true }],
    place_rental:{ type: Schema.ObjectId, ref: "PlaceRental",required:true },
    status:{
        type: Number,
        default:1
    }
})
const setupTaskReportSchema = new mongoose.Schema({
    new_lat_lng:{
        type: latLngSchema
    },
    signboards:[
        {type:signboardImagesReportSchema}
    ],
    note:{type:String},
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
const checkTaskReportSchema = new mongoose.Schema({
    place_rental:[{
        pl_id:{ type: Schema.ObjectId, ref: "PlaceRental",required:true },
        signboards:[
            {type:signboardImagesReportSchema}
        ]
    }
    ],
    note:{type:String},
    status:{
        type: Number,
        default:1
    }
})
const feeTaskReportSchema = new mongoose.Schema({
    note:{type:String},
    status:{
        type: Number,
        default:1
    }
})
const feeTaskReportLineSchema = new mongoose.Schema({
    em_confirm:{type:Boolean,default:false},
    pt_confirm:{type:Boolean,default:false},
    em_note:{type:String},
    pt_note:{type:String}
})
const placeWithFeeSchema = new mongoose.Schema({
    place_rental:{ type: Schema.ObjectId, ref: "PlaceRental",required:true },
    times:[{type:Number,require:true}],
    amount:{type:Number},
    time_unit:{type:String},
    report:{
        type:feeTaskReportLineSchema,
        required:true
    },
    status:{
        type: Number,
        default:1
    }
})
const feeTaskSchema = new mongoose.Schema({
    accs:[{ type: Schema.ObjectId, ref: "Account",required:true }],
    fee_detail:[placeWithFeeSchema],
    
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
    start:{type:String},
    end:{type:String}

});
const reportTaskReportSchema = new mongoose.Schema({
    signboards:[
        {type:signboardImagesReportSchema}
    ],
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
    setup_task_report:setupTaskReportSchema,
    check_task:checkTaskSchema,
    check_task_report:checkTaskReportSchema,
    fee_task:feeTaskSchema,
    fee_task_report:feeTaskReportSchema,
    report_task:reportTaskSchema,
    report_task_report:reportTaskReportSchema,
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