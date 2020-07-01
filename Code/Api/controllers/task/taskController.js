const AppError = require('./../../utils/appError');
const PartyA = require('../../models/user/partyA');
require('datejs');
const PartyB = require('../../models/user/partyB');
var mongoose = require('mongoose');
const Signboard = require('../../models/signboard/signboard');
const Task = require('../../models/task/task');
const SImage = require('../../models/image/sImage');
const { getSecond } = require('../../utils/index');
const {sendNewTaskToB} = require('../../controllers/notification/notificationController')
const PlaceRental = require('../../models/place/placeRental');

const agenda = require('../../utils/agenda');
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
                taskInfo.status = -1;
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

        if (task.type == 'report' && task.report_task.repeat_type == 'repeat') {
            scheduleTask(task);
        }
        res.status(200).json({
            status: 'success',
            data: task
        });
    } catch (error) {
        next(error);
    }
};
exports.reportTask = async (req, res, next) => {

    console.log(JSON.stringify(req.body));

    try {
        var task = await Task.findById(req.body.id);
        if (task.status != 1 && task.status != 2) {
            return next(new AppError(200, 'fail', 'Nhiệm vụ này không thể báo cáo'), req, res, next);
        }
        task.status = 2;
        if (task.type == "setup") {
            task.setup_task_report = req.body.report;
            await task.save();
        }
        if (task.type == "check") {
            task.check_task_report = req.body;
            await task.save();
        }
        if (task.type == "report") {
            task.report_task_report = req.body.report;
            await task.save();
        }
        if (task.type == "fee") {
            var isA = true;
            if(req.acc.role.includes("partyB")){
                isA = false;
            }
  
          
            req.body.report.forEach(rp=>{
               
                
                task.fee_task.fee_detail.forEach(fd=>{
                    
                    
                    if(fd.place_rental._id.toString()==rp.pl_id){
                        console.log('a');
                        if (!fd.report.em_confirm||!fd.report.pt_confirm) {
                            if(isA){
                                fd.report.em_confirm = rp.confirm;
                                fd.report.em_note = rp.note;
                            }else{
                                fd.report.pt_confirm = rp.confirm;
                                fd.report.pt_note = rp.note;
                            }
                        }else{
                            if(isA){
                                fd.report.em_note = rp.note;
                            }else{
                                fd.report.pt_note = rp.note;
                            }
                        }
                        
                    }
                })
            })
            
            await task.save();
        }
        res.status(200).json({
            status: 'success',
            data: task
        });

        // var t = {
        //     id: '5ef2fefc5af93643783266cf',
        //     report: {
        //         new_lat_lng: {
        //             lat: 21.045891, lng: 105.8672733
        //         },
        //         signboards: [{
        //             s_id: '5ef2f8ae5af936437832664a',
        //             imgs: ['5ef22328a1e31a333ca4c71b', '5ef223849d8a3451c4f070fc'],
        //         }
        //         ],
        //         note: "Nhiệm vụ hoàn thành123123",
        //     }
        // }

    }
    catch (error) {
        next(error);
    }
}

exports.approveTask = async (req, res, next) => {

    try {
        var task = await Task.findById(req.body.id);
        task.status = req.body.status;

        if (req.body.status == 3) {
            if (task.type == "setup") {
                var pl = await PlaceRental.findById(task.setup_task.place_rental);
                pl.lat_lng.lat = task.setup_task_report.new_lat_lng.lat;
                pl.lat_lng.lng = task.setup_task_report.new_lat_lng.lng;
                pl.signboards = task.setup_task.signboards;
                await pl.save();
            }
        }

        await task.save();
        res.status(200).json({
            status: 'success',
            data: task
        });

        // var t = {
        //     id: '5ef2fefc5af93643783266cf',
        //     report: {
        //         new_lat_lng: {
        //             lat: 21.045891, lng: 105.8672733
        //         },
        //         signboards: [{
        //             s_id: '5ef2f8ae5af936437832664a',
        //             imgs: ['5ef22328a1e31a333ca4c71b', '5ef223849d8a3451c4f070fc'],
        //         }
        //         ],
        //         note: "Nhiệm vụ hoàn thành123123",
        //     }
        // }

    }
    catch (error) {
        next(error);
    }
}

exports.getAllOfMyPT = async (req, res, next) => {
    try {
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
            task = await Task.find({ type: {$in:['report','fee']},status : { $gt : -1} }).populate('acc_created').populate({
                path: 'report_task.place_rental',
            }).populate({
                path: 'report_task.place_rental.place_id',
                // select:'owner',
                match: { owner: ptB._id}
            }).populate({
                path: 'fee_task.fee_detail.place_rental',
                populate:{
                    path:'place_id'
                }
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
            populate:{
                path:'place_id'
            }
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
        //             pl_id: "5ef2fabc5af9364378326657",
        //             signboards: [{
        //                 s_id: "5ef2f8ae5af936437832664a",
        //                 imgs: ["5ef321f39b92a6224ca2eca4", "5ef321fe9b92a6224ca2eca5"],
        //                 rating:5,
        //                 note:'Đối tác thực hiện tốt việc treo biển',
        //             }]
        //         },
        //     ]
        // }
        // await task.save()

        if(task.type=="fee"){
            task = task.toObject();
            if (req.acc.role.includes('partyB')) {
                task.role = 'B'
                ptB = await PartyB.findOne({ "accs": req.acc._id });
            
                task.fee_task.fee_detail=task.fee_task.fee_detail.filter((fd)=>{
                          
                            console.log(fd.place_rental.place_id.owner,ptB._id);
                            
                            return (fd.place_rental.place_id.owner==ptB._id.toString());
                               
                        })
                    
                
            }else{
                task.role = 'A'
            }
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


async function scheduleTask(task, done) {


    if (typeof task == 'string') {

        task = await Task.findById(task);
    }
    var rootTask = task;
    if(task.report_task.root_task){
        rootTask = await Task.findById(task.report_task.root_task);
    }
    if(task.status!=-1){
        if(task.status==-2){
            task.status==1;
            await task.save();
        }
        sendNewTaskToB(task.report_task.place_rental,task);
     }
    var newTask = new Task(rootTask);

    newTask._id = mongoose.Types.ObjectId();
    // newTask._id = mongoose.Types.ObjectId();
    newTask.isNew = true;
    var nextTaskDate = null;
    console.log(task.status);


    if (rootTask.report_task.repeat_type == 'repeat' && rootTask.status != 0 && rootTask.status != -3) {
        newTask.report_task.root_task = rootTask._id;
        var notiTime = null;
        if (rootTask.report_task.repeat_detail.fz_unit == "moth") {
            nextTaskDate = getNextDay(rootTask.report_task.repeat_detail.fz_vals);
        }
        if (rootTask.report_task.repeat_detail.fz_unit == "week") {
            nextTaskDate = getNextWeekDay(rootTask.report_task.repeat_detail.fz_vals);
            console.log(nextTaskDate.toString('dd/MM/yyyy'));

        }
        if (rootTask.report_task.repeat_detail.fz_unit == "day") {

            nextTaskDate = getXNextDay(rootTask.report_task.repeat_detail.fz_vals);
            console.log(nextTaskDate.toString('dd/MM/yyyy'));

        }
        if (rootTask.report_task.repeat_detail.random) {
            newTask.start = randomDateBetweenTime(nextTaskDate, rootTask.report_task.repeat_detail.start, rootTask.report_task.repeat_detail.end);
            newTask.end = new Date(newTask.start.getTime() + rootTask.report_task.repeat_detail.time_to_complete * 1000);
            notiTime = (new Date(newTask.start.getTime() - +rootTask.report_task.repeat_detail.remind_before * 1000));
            newTask.status = -2;

        } else {
            newTask.start = Date.parse(nextTaskDate.toString('yyyy-MM-dd') + ' ' + rootTask.report_task.repeat_detail.start);
            newTask.end = Date.parse(nextTaskDate.toString('yyyy-MM-dd') + ' ' + rootTask.report_task.repeat_detail.end);
            notiTime = (new Date(newTask.start.getTime() - 3600 * 1000));
            newTask.status = 1;
        }

        await newTask.save();
        var jobName = 'auto create task ' + Math.random().toString(36);
        agenda.define(jobName, function (job, done) {

            scheduleTask(newTask._id.toString(), done);
        });
        (async function () {
              notiTime = (new Date((new Date()).getTime() + 2 * 1000));
            await agenda.start();
            await agenda.schedule(notiTime, jobName);

        })();

        
        console.log('task created');
    }


    if (done) {
        done();
    }

}



function getNextDay(nextdayLst) {

    var date = Date.today();
    if (nextdayLst) {
        for (let index = 0; index < 31; index++) {
            date.addDays(1);
            for (var d in nextdayLst) {
                if (date.getDate() == nextdayLst[d]) {
                    return date;
                }
            }

        }
    }
    return null;
}
function getXNextDay(nextdayLst) {

    var date = Date.today();
    if (nextdayLst) {
           return date.addDays(nextdayLst[0]);
    }
            
    return null;
}
function getNextWeekDay(nextdayLst) {
    var date = Date.today();
    if (nextdayLst) {
        nextdayLst = nextdayLst.sort();
        for (let index = 0; index < 8; index++) {
            date.addDays(1);
            for (var d in nextdayLst) {
                if (date.getDay() == nextdayLst[d] || (date.getDay() == 0 && nextdayLst[d] == 7)) {
                    return date;
                }
            }

        }
    }
    return null;
}

function randomDateBetweenTime(date, start, end) {
    var min = Date.parse(date.toString('yyyy-MM-dd') + ' ' + start).getTime();
    var max = Date.parse(date.toString('yyyy-MM-dd') + ' ' + end).getTime();
    return new Date(Math.floor(Math.random() * (max - min + 1) + min));
}

