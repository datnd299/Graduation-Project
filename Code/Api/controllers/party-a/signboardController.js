const AppError = require('./../../utils/appError');
const PartyA = require('../../models/user/partyA');
const Signboard = require('../../models/signboard/signboard');
const PlaceRental = require('../../models/place/placeRental');
const Task = require('../../models/task/task');
exports.createNew = async (req, res, next) => {
    try {
        const {
            name,
            code,
            imgs,
            note
        } = req.body;
        var acc = req.acc;
        var ptA = await PartyA.findOne({ "accs": acc._id });
        if (!ptA) {
            return next(new AppError(200, 'fail', 'Bạn không phải đối tác'), req, res, next);
        }
        const sb = await Signboard.create({
            name: name,
            code:ptA._id+"__"+code,
            imgs:imgs,
            owner:ptA,
            creater:acc,
            note:note
        })

        res.status(200).json({
            status: 'success',
            data: sb
        });


    } catch (error) {
        next(error);
    }
};
exports.allSignboardsReport = async (req, res, next) => {
    try {

        var acc = req.acc;
        var ptA = await PartyA.findOne({ "accs": acc._id });
        if (!ptA) {
            return next(new AppError(200, 'fail', 'Bạn không phải đối tác'), req, res, next);
        }
        let signboards = await Signboard.find({
            owner:ptA._id
        })
        signboards = JSON.parse(JSON.stringify(signboards));

        const places = await PlaceRental.find({
            party_renter:ptA._id
        })
        signboards.forEach(sb=>{
            sb.report = {
                fee:0,
                task:0,
                img:0,
                num:0,
            }
            sb.places=[];
            places.forEach(pl=>{
                if(pl.signboards&&pl.signboards.includes(sb._id)){
                    sb.places.push(pl);
                    sb.report.num +=1;
                }
            })
        })

        var tasks = await Task.find({ pt_a: ptA, });

        signboards.forEach((sb,pI)=>{
            if(sb.places.length==0) return;
            tasks.forEach((t,index)=>{
            
                if(t.fee_task){
                    let al = false;
                    t.fee_task.fee_detail.forEach(f=>{
                        sb.places.forEach(p=>{
                       
                            
                            if(p._id.toString()==f.place_rental.toString()){
                                al=true;
                                sb.report.task+=1;
                                sb.report.fee +=f.amount;
                            }
                        })
                    })
                    
                   
                   
                }
                else if(t.report_task_report){
                    t.report_task_report.signboards.forEach(s=>{
                        if(s.s_id.toString()==sb._id.toString()){
                            sb.report.task+=1;
                            sb.report.img += s.imgs.length;
                        }
                    })
                }
                else if(t.check_task_report){
     
                    t.check_task_report.place_rental.forEach(pl=>{
                       
                            pl.signboards.forEach(s=>{
                                if(s.s_id.toString()==sb._id.toString()){
                                    sb.report.task+=1;
                                    sb.report.img += s.imgs.length;
                                }
                               
                            })
                            
                        
                    })
                    
                }else if(t.setup_task_report){
                   
                        t.setup_task_report.signboards.forEach(s=>{
                            if(s.s_id.toString()==sb._id.toString()){
                                sb.report.task+=1;
                                sb.report.img += s.imgs.length;
                            }
                        })
                    
                }
            })
        })

        res.status(200).json({
            status: 'success',
            data: signboards
        });


    } catch (error) {
        next(error);
    }
};
exports.getMine = async (req, res, next) => {
    try {

        var acc = req.acc;
        var ptA = await PartyA.findOne({ "accs": acc._id });
        if (!ptA) {
            return next(new AppError(200, 'fail', 'Bạn không phải đối tác'), req, res, next);
        }
        const sbs = await Signboard.find({
            owner:ptA._id
        })

        res.status(200).json({
            status: 'success',
            data: sbs
        });


    } catch (error) {
        next(error);
    }
};
exports.getByID = async (req, res, next) => {
    try {

        var acc = req.acc;
        var ptA = await PartyA.findOne({ "accs": acc._id });
        if (!ptA) {
            return next(new AppError(200, 'fail', 'Bạn không phải đối tác'), req, res, next);
        }
        const sbs = await Signboard.findById(req.body.id);
        if(!sbs){
            return next(new AppError(200, 'fail', 'Biển không tồn tại'), req, res, next);
        }
        sbs.code = sbs.code.substr(26);
        res.status(200).json({
            status: 'success',
            data: sbs
        });


    } catch (error) {
        next(error);
    }
};
