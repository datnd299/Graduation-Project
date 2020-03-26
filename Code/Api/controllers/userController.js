const Account = require('../models/user/account');
const base = require('./baseController');
const PartyA = require('../models/user/partyA');
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


exports.partyASignUp = async (req, res, next) => {
    const session = await Account.startSession();
    session.startTransaction();
    try {
        

        const acc = await Account.create({
            name: req.body.name,
            email: req.body.email,
            password: req.body.password,
            role:'partyAAdmin',
            passwordConfirm: req.body.passwordConfirm,
        });
        const ptA = await PartyB.create({
            name: req.body.name,
            email: req.body.email,
            phone:req.body.phone,
            province: req.body.province,
            district: req.body.district,
            address: req.body.address,
            industry:req.body.industry,
            
            accs: [acc]
        });
        await session.commitTransaction();
        session.endSession();

        res.status(200).json({
            status: 'success',
            data: {
                acc,
                ptA
            }
        });

    } catch (err) {
        next(err);
    }

};

exports.partyBSignUp = async (req, res, next) => {
    const session = await Account.startSession();
        session.startTransaction();
    try {
        

        const acc = await Account.create({
            name: req.body.name,
            email: req.body.email,
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
            industry:req.body.industry,
            
            accs: acc
        });
        await session.commitTransaction();
        session.endSession();

        res.status(200).json({
            status: 'success',
            data: {
                acc,
                ptB
            }
        });

    } catch (err) {
        await session.abortTransaction()
        session.endSession()
        next(err);
    }

};




exports.getAllUsers = base.getAll(Account);
exports.getUser = base.getOne(Account);

// Don't update password on this 
exports.updateUser = base.updateOne(Account);
exports.deleteUser = base.deleteOne(Account);