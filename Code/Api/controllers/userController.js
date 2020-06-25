const Account = require('../models/user/account');
const base = require('./baseController');
const PartyA = require('../models/user/partyA');
const AppError = require('../utils/appError');
const PartyB = require('../models/user/partyB');
const Socket = require('../models/access/socket')
const mongoose = require('mongoose');
var setting = require("./../setting");
// var os = require('os');
var osu = require('node-os-utils')
var cpu = osu.cpu
var os = osu.os
var mem = osu.mem
const util = require('util')
const getSize = require('get-folder-size');
const fs = require('fs');
const { cpuUsage } = require('process');
function getFolderSize(){
    
}

exports.deleteMe = async (req, res, next) => {
    try {
        await User.findByIdAndUpdate(req.user.id, {
            active: false
        });

        res.status(204).json({
            status: 'success',
            data: null
        });


    } catch (error) {
        next(error);
    }
};
exports.getSystemInfo = async (req, res, next) => {
    try {
        var mongoStats = await mongoose.connection.db.stats()
        
     var cpuPercentage = await   cpu.usage();
     var cpuModal =cpu.model();
     

     var memInfo = await mem.info()
  
  var fileFolder = setting.PROJECT_DIR + '/files/';
  const fileFolderSizePromise = util.promisify(getSize)
  var fileFolderSize =await fileFolderSizePromise(fileFolder);
  const readdirPromise = util.promisify(fs.readdir)
  var files = await readdirPromise(fileFolder);

  
  
  var ptACount = await PartyA.where({ status: { $ne: 0 } }).estimatedDocumentCount();
  var ptBCount = await PartyB.where({ status: { $ne: 0 } }).estimatedDocumentCount();
  var accCount = await Account.where({ status: { $ne: 0 } }).estimatedDocumentCount();
  var online = await Socket.find().distinct('acc');

  
  var info = {
    db:mongoStats,
    cpu:{
        model:cpuModal,
        use:cpuPercentage,
        free:100-cpuPercentage
    },
    mem:memInfo,
    file:{
        size:(fileFolderSize / 1024 / 1024).toFixed(2) + ' MB',
        count:files.length
    },
    user:{
        a:ptACount,
        b:ptBCount,
        acc:accCount,
        online:online.length
    }
}

  

        res.status(200).json({
            status: 'success',
            data: info
        });


    } catch (error) {
        next(error);
    }
};

exports.getAllPartyA = async (req, res, next) => {
    try {
         const lst = await PartyA.find({});

        res.status(200).json({
            status: 'success',
            data: lst
        });


    } catch (error) {
        next(error);
    }
};
exports.getAllPartyB = async (req, res, next) => {
    try {
         const lst = await PartyB.find({});

        res.status(200).json({
            status: 'success',
            data: lst
        });


    } catch (error) {
        next(error);
    }
};
exports.getPartyAAccs = async (req, res, next) => {
    try {
        var pId = req.body.p_id;
        var ptA = await PartyA.findById(pId).populate('accs');
        if(!ptA){
            return next(new AppError(200, 'fail', 'Không có đơn vị này'), req, res, next);
        }

       res.status(200).json({
           status: 'success',
           data: ptA
       });


   } catch (error) {
       next(error);
   }
}

exports.approvePartyA = async (req, res, next) => {
    try {
        var pId = req.body.p_id;
        var status = req.body.status;
        var ptA = await PartyA.findById(pId);
        if(!ptA){
            return next(new AppError(200, 'fail', 'Không có đơn vị này'), req, res, next);
        }
        ptA.status = status;
        await ptA.save()
       res.status(200).json({
           status: 'success',
           data: ptA
       });


   } catch (error) {
       next(error);
   }
}
exports.approvePartyB = async (req, res, next) => {
    try {
        var pId = req.body.p_id;
        var status = req.body.status;
        var ptB = await PartyB.findById(pId);
        if(!ptB){
            return next(new AppError(200, 'fail', 'Không có đơn vị này'), req, res, next);
        }
        ptB.status = status;
        await ptB.save()
       res.status(200).json({
           status: 'success',
           data: ptB
       });


   } catch (error) {
       next(error);
   }
}


exports.partyASignUp = async (req, res, next) => {
    const sessionA = await Account.startSession();
    sessionA.startTransaction();
    const sessionPt = await PartyA.startSession();
    sessionPt.startTransaction();
    try {
        

        const acc = await Account.create({
            name: req.body.name,
            acc_name: req.body.email,
            email: req.body.email,
            password: req.body.password,
            role:'partyAAdmin',
            passwordConfirm: req.body.passwordConfirm,
        });
        const ptA = await PartyA.create({
            name: req.body.name,
            email: req.body.email,
            phone:req.body.phone,
            province: req.body.province,
            district: req.body.district,
            address: req.body.address,
            industry:req.body.industry,
            
            accs: [acc]
        });
        await sessionA.commitTransaction();
        await sessionPt.commitTransaction();
        sessionA.endSession();
        
        sessionPt.endSession();

        res.status(200).json({
            status: 'success',
            data: {
                acc,
                ptA
            }
        });

    } catch (err) {
        await sessionA.abortTransaction()
        sessionA.endSession()
        await sessionPt.abortTransaction()
        sessionPt.endSession()
        next(err);
    }

};


exports.partyBSignUp = async (req, res, next) => {
    const sessionA = await Account.startSession();
    sessionA.startTransaction();
    const sessionPt = await PartyB.startSession();
    sessionPt.startTransaction();
    try {
        

        const acc = await Account.create({
            name: req.body.name,
            email: req.body.email,
            acc_name:req.body.email,
            password: req.body.password,
            role:'partyB',
            passwordConfirm: req.body.passwordConfirm,
        });
        const ptB = await PartyB.create({
            name: req.body.name,
            email: req.body.email,
            phone:req.body.phone,
            province: req.body.province,
            district: req.body.district,
            address: req.body.address,

            
            accs: acc
        });
        await sessionA.commitTransaction();
        await sessionPt.commitTransaction();
        sessionA.endSession();
        
        sessionPt.endSession();

        res.status(200).json({
            status: 'success',
            data: {
                acc,
                ptB
            }
        });

    } catch (err) {
        await sessionA.abortTransaction()
        sessionA.endSession()
        await sessionPt.abortTransaction()
        sessionPt.endSession()
        next(err);
    }

};



exports.createNewPAAccount = async (req, res, next) => {
    const sessionA = await Account.startSession();
    sessionA.startTransaction();
    const sessionPt = await PartyA.startSession();
    sessionPt.startTransaction();
    try {
        
        const {
            acc_name,
            name,
            email,
            password,
            password_confirm,
            role,
        } = req.body;
         const ptA = await PartyA.findOne({ "accs": req.acc.id});
       
         const acc = await Account.create({
            name: name,
            email: email,
            acc_name:acc_name,
            password: password,
            role:role,
            passwordConfirm: password_confirm,
            status:2
        });
        ptA.accs.push(acc);
        await ptA.save();
       
        
        // const acc = await Account.create({
        //     name: name,
        //     email: email,
        //     acc_name:acc_name,
        //     password: password,
        //     role:role,
        //     passwordConfirm: password_confirm,
        // });
       
        await sessionA.commitTransaction();
        await sessionPt.commitTransaction();
        sessionA.endSession();
        
        sessionPt.endSession();
        res.status(200).json({
            status: 'success',
            data: {
                acc
            }
        });

    } catch (err) {
        await sessionA.abortTransaction()
        sessionA.endSession()
        await sessionPt.abortTransaction()
        sessionPt.endSession()
        next(err);
    }
};

exports.approveAcc = async (req, res, next) => {
    try {
        var aId = req.body.id;
        var status = req.body.status;
        var acc = await Account.findById(aId);

        if(!acc){
            return next(new AppError(200, 'fail', 'Không có tk này'), req, res, next);
        }
        acc.status = status;
        await Account.findByIdAndUpdate(acc._id,{status:status})

       res.status(200).json({
           status: 'success',
           data: acc
       });


   } catch (error) {
       next(error);
   }
}

exports.getMyInfo = async (req, res, next) => {
    try {
        
        var acc = req.acc;
        var ptA = null;
        var ptB = null;
        if(acc.role.includes('partyA')){
            ptA = await PartyA.findOne({accs:acc._id});
        }

        if(acc.role.includes('partyB')){
             ptB = await PartyB.findOne({ "accs": acc._id });
        }

       res.status(200).json({
           status: 'success',
           data: {
               acc,
               ptA,
               ptB
           }
       });


   } catch (error) {
       next(error);
   }
}

exports.updateMyAcc = async (req, res, next) => {
    try {
        
        var acc = req.acc;
        
        const nAcc = await Account.findById(acc._id).select('+password');
  
        

        

        if(req.body.change_pass){
            if(req.body.password!=req.body.password_confirm){
                return next(new AppError(200, 'fail', 'Mật khẩu mới không khớp'), req, res, next);
            }
            if (!nAcc || !await nAcc.correctPassword(req.body.old_pass, nAcc.password)) {
                return next(new AppError(200, 'fail', 'Mật khẩu cũ không đúng'), req, res, next);
            }
            
            nAcc.name = req.body.name;
            nAcc.acc_name = req.body.acc_name;
            nAcc.password = req.body.password;
            nAcc.passwordConfirm = req.body.password;
        }else{
            
            
            nAcc.name = req.body.name;
            nAcc.acc_name = req.body.acc_name;
            nAcc.passwordConfirm = nAcc.password;
           
        }
        await nAcc.save();

       res.status(200).json({
           status: 'success',
           data: 
               nAcc
           
       });


   } catch (error) {
       next(error);
   }
}


exports.getAllUsers = base.getAll(Account);
exports.getUser = base.getOne(Account);

// Don't update password on this 
exports.updateUser = base.updateOne(Account);
exports.deleteUser = base.deleteOne(Account);