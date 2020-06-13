const AppError = require('./../../utils/appError');
const PartyA = require('../../models/user/partyA');
const PartyB = require('../../models/user/partyB');
const Signboard = require('../../models/signboard/signboard');
const Task = require('../../models/task/task');
const { getSecond } = require('../../utils/index');
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
            taskInfo.fee_task = {
                accs: req.body.accs,
                feeDetail: req.body.feeDetail,
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
        console.log(req.body);
        
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
            console.log(ptB);
            
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
            }).populate({
                path: 'fee_task.accs',
            }).populate({
                path: 'fee_task.feeDetail.place_rental',
            }).populate({
                path: 'report_task.place_rental',
            }).populate({
                path: 'report_task.place_id',
            })
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
                path: 'fee_task.feeDetail.place_rental',
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
            task = await Task.find({  type:  'report'  }).populate('acc_created').populate({
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
            path: 'check_task.accs',
        }).populate({
            path: 'check_task.place_rental',
        }).populate({
            path: 'fee_task.accs',
        }).populate({
            path: 'fee_task.feeDetail.place_rental',
        }).populate({
            path: 'report_task.place_rental',
        }).populate({
            path: 'report_task.place_id',
        })




        res.status(200).json({
            status: 'success',
            data: task,
            acc: req.acc
        });
    } catch (error) {
        next(error);
    }
};





