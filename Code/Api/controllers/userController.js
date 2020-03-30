const Account = require('../models/user/account');
const base = require('./baseController');
const PartyA = require('../models/user/partyA');
const AppError = require('../utils/appError');
const PartyB = require('../models/user/partyB');
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

exports.approveParty = async (req, res, next) => {
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


exports.getAllUsers = base.getAll(Account);
exports.getUser = base.getOne(Account);

// Don't update password on this 
exports.updateUser = base.updateOne(Account);
exports.deleteUser = base.deleteOne(Account);