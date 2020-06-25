const AppError = require('./../../utils/appError');
const PartyA = require('../../models/user/partyA');
const PartyB = require('../../models/user/partyB');
var mongoose = require('mongoose');
const Signboard = require('../../models/signboard/signboard');
const Task = require('../../models/task/task');
const SImage = require('../../models/image/sImage');
const { getSecond } = require('../../utils/index');
const PlaceRental = require('../../models/place/placeRental');
exports.createNew = async (req, res, next) => {
    try {

        var acc = req.acc;
        var ptA = await PartyA.findOne({ "accs": acc._id });
        if (!ptA) {
            return next(new AppError(200, 'fail', 'Bạn không phải bên thêu'), req, res, next);
        }
        var taskInfo = {
            type: req.body.type,
            acc_created: req.acc._id,
            pt_a: ptA,
            note: req.body.note,
            start: req.body.start,
            end: req.body.end,
        }
        if (req.body.type == "setup") {
            taskInfo.setup_task = {
                accs: req.body.accs,
                signboards: req.body.signboards,
                place_rental: req.body.places,
            }
        } else if (req.body.type == "check") {
            taskInfo.check_task = {
                accs: req.body.accs,
                place_rental: req.body.places,
            }
        } else if (req.body.type == "fee") {
            var feeDetail = req.body.feeDetail;
            feeDetail.forEach(e => {
                e.report = {
                    em_confirm: false,
                    pt_confirm: false,
                    em_note: '',
                    pt_note: ''
                }
            })

            console.log(feeDetail);

            taskInfo.fee_task = {
                accs: req.body.accs,
                fee_detail: feeDetail,
            }
        } else if (req.body.type == "report") {
            taskInfo.report_task = {
                place_rental: req.body.places,
                root_task: null,
                repeat_type: req.body.typeRepeat,
            };
            if (taskInfo.report_task.repeat_type == 'repeat') {
                var repeatDetail = req.body.repeatDetail;
                fzVals = [];
                if (repeatDetail.fzUnit == 'day') {
                    fzVals.push(repeatDetail.fzValDay)
                } else {
                    fzVals = repeatDetail.fzVals
                }

                taskInfo.report_task.repeat_detail = {
                    fz_unit: repeatDetail.fzUnit,
                    fz_vals: fzVals,
                    remind_before: getSecond(repeatDetail.remindBeforeVal, repeatDetail.remindBeforeUnit),
                    random: repeatDetail.random,
                    time_to_complete: getSecond(repeatDetail.timeToComplete, 'minute'),
                    start: repeatDetail.start,
                    end: repeatDetail.end
                }
            }
        }
        var task = await Task.create(taskInfo);
        res.status(200).json({
            status: 'success',
            data: task
        });
    } catch (error) {
        next(error);
    }
};

exports.getAllOfMyPT = async (req, res, next) => {
    try {

        // SImage.create({
        //     name:"a4505059-d218-44ca-9002-ec3a7cbf74f6___maxresdefault.jpg",
        //     lat_lng:{
        //         lat:21.0042788,
        //         lng:105.8437013
        //     },
        //     time:new Date(),
        //     device:"Xiaomi Mi 8 Lite",
        //     hidden_info:"Xiaomi Mi 8 Lite_1592926934112_21.0042788,105.8437013"
        // })
        var acc = req.acc;
        var ptA = null;
        var ptB = null;
        if (acc.role.includes('partyA')) {
            ptA = await PartyA.findOne({ "accs": acc._id });
            if (!ptA) {
                return next(new AppError(200, 'fail', 'Bạn không phải bên thuê'), req, res, next);
            }
        } else {
            ptB = await PartyB.findOne({ "accs": acc._id });


        }

        var task = null;
        if (acc.role == 'partyAAdmin') {
            task = await Task.find({ pt_a: ptA }).populate('acc_created').populate({
                path: 'setup_task.accs',
            }).populate({
                path: 'setup_task.place_rental',
            }).populate({
                path: 'check_task.accs',
            }).populate({
                path: 'check_task.place_rental',
                populate: {
                    path: 'signboards',
                    model: Signboard
                }
            }).populate({
                path: 'check_task_report.place_rental.pl_id',
            }).populate({
                path: 'check_task_report.place_rental.signboards.s_id',
            }).populate({
                path: 'check_task_report.place_rental.signboards.imgs',
            }).populate({
                path: 'fee_task.accs',
            }).populate({
                path: 'fee_task.fee_detail.place_rental',
            }).populate({
                path: 'report_task.place_rental',
                populate: {
                    path: 'signboards',
                   
                }
            }).populate({
                path: 'report_task.place_rental',
                populate: {
                    path: 'place_id',
                    populate: {
                        path: 'owner'
                    }
                },
                
            }).populate({
                path: 'report_task_report.signboards.s_id'
            }).populate({
                path: 'report_task_report.signboards.imgs'
            }).sort([['start', -1]])
        } else if (acc.role == 'partyAEm') {
            task = await Task.find({ pt_a: ptA, type: { $ne: 'report' } }).populate('acc_created').populate({
                path: 'setup_task.accs',
            }).populate({
                path: 'setup_task.place_rental',
            }).populate({
                path: 'check_task.accs',

            }).populate({
                path: 'check_task.place_rental',
            }).populate({
                path: 'fee_task.accs',
            }).populate({
                path: 'fee_task.fee_detail.place_rental',
            });
            task = task.filter(e => {
                if (e.setup_task) {
                    var al = false;
                    e.setup_task.accs.forEach(a => {

                        if (a._id.toString() == acc._id.toString()) {

                            al = true;
                        }

                    });
                    return al;
                } else if (e.check_task) {
                    var al = false;
                    e.check_task.accs.forEach(a => {
                        if (a._id.toString() == acc._id.toString()) {
                            al = true;
                        }

                    });
                    return al;
                } else if (e.fee_task) {
                    var al = false;
                    e.fee_task.accs.forEach(a => {
                        if (a._id.toString() == acc._id.toString()) {
                            al = true;
                        }

                    });
                    return al;
                } else {
                    return false;
                }
            })
        } else {
            task = await Task.find({ type: 'report' }).populate('acc_created').populate({
                path: 'report_task.place_rental',
            }).populate({
                path: 'report_task.place_rental.place_id',
            });
        }



        res.status(200).json({
            status: 'success',
            data: task,
            acc: req.acc
        });
    } catch (error) {
        next(error);
    }
};

exports.getTaskById = async (req, res, next) => {
    try {

        var id = req.body.id;

        var task = null;

        task = await Task.findById(id).populate('acc_created').populate({
            path: 'setup_task.accs',
        }).populate({
            path: 'setup_task.place_rental',
        }).populate({
            path: 'setup_task.signboards',
        }).populate({
            path: 'setup_task_report.signboards.s_id',
        }).populate({
            path: 'setup_task_report.signboards.imgs',
        }).populate({
            path: 'check_task.accs',
        }).populate({
            path: 'check_task.place_rental',
            populate: {
                path: 'signboards',
                model: Signboard
            }
        }).populate({
            path: 'check_task_report.place_rental.pl_id',
        }).populate({
            path: 'check_task_report.place_rental.signboards.s_id',
        }).populate({
            path: 'check_task_report.place_rental.signboards.imgs',
        }).populate({
            path: 'fee_task.accs',
        }).populate({
            path: 'fee_task.fee_detail.place_rental',
        }).populate({
            path: 'report_task.place_rental',
            populate: {
                path: 'signboards',
               
            }
        }).populate({
            path: 'report_task.place_rental',
            populate: {
                path: 'place_id',
                populate: {
                    path: 'owner'
                }
            },
            
        }).populate({
            path: 'report_task_report.signboards.s_id'
        }).populate({
            path: 'report_task_report.signboards.imgs'
        })

        // task.report_task_report = {
        //     signboards: [{
        //                 s_id: mongoose.Types.ObjectId("5ef2f8ae5af936437832664a"),
        //                 imgs: [mongoose.Types.ObjectId("5ef322029b92a6224ca2eca6")],
        //                 rating:5,
        //                 note:'Biển tốt',
        //             },
        //         ]
        // }
        //  await task.save()

        

            //     task.setup_task_report = {
            //         new_lat_lng:{
            //            lat:21.045891,lng:105.8672733
            //         },
            //         signboards:[{
            //             s_id:mongoose.Types.ObjectId("5ef2f8ae5af936437832664a"),
            // imgs:[mongoose.Types.ObjectId("5ef22328a1e31a333ca4c71b"),mongoose.Types.ObjectId("5ef223849d8a3451c4f070fc")],
            // }
            //         ],
            //         note:"Nhiệm vụ hoàn thành",

            //     }
            //     await task.save();

        // task.check_task_report = {
        //     note: 'Đã hoàn thành',
        //     place_rental: [
                
        //         {
        //             pl_id: mongoose.Types.ObjectId("5ef2fabc5af9364378326657"),
        //             signboards: [{
        //                 s_id: mongoose.Types.ObjectId("5ef2f8ae5af936437832664a"),
        //                 imgs: [mongoose.Types.ObjectId("5ef321f39b92a6224ca2eca4"), mongoose.Types.ObjectId("5ef321fe9b92a6224ca2eca5")],
        //                 rating:5,
        //                 note:'Đối tác thực hiện tốt việc treo biển',
        //             }]
        //         },
        //     ]
        // }
        // await task.save()

        res.status(200).json({
            status: 'success',
            data: task,
            acc: req.acc
        });
    } catch (error) {
        next(error);
    }
};





