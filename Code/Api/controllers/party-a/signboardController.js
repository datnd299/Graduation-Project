const AppError = require('./../../utils/appError');
const PartyA = require('../../models/user/partyA');
const Signboard = require('../../models/signboard/signboard');
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