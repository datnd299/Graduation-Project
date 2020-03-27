const PlaceForRent = require('../../models/place/placeForRent');
const PartyB = require('../../models/user/partyB');
const AppError = require('./../../utils/appError');
exports.createNew = async (req, res, next) => {
    try {
        var acc = req.acc;
        var ptB = await PartyB.findOne({ "accs": acc._id});
        if(!ptB){
            return next(new AppError(200, 'fail', 'Bạn không phải đối tác'), req, res, next);
        }
        
        const pl = await PlaceForRent.create({
            name: req.body.name,
            
            address: req.body.address,
            lat_lng:{
                lat:req.body.location.lat,
                lng:req.body.location.lat,
            },
            price:req.body.price,
            time_unit:req.body.time_unit,
            imgs:req.body.imgs,
            
            accs: acc,
            owner:ptB
        });
        
        res.status(200).json({
            status: 'success',
            data: pl
        });


    } catch (error) {
        next(error);
    }
};

exports.getMine = async (req, res, next) => {
    try {
        var acc = req.acc;
        var ptB = await PartyB.findOne({ "accs": acc._id});
        if(!ptB){
            return next(new AppError(200, 'fail', 'Bạn không phải đối tác'), req, res, next);
        }
        
        const plLst = await PlaceForRent.find({
            owner:ptB._id
        })
        
        res.status(200).json({
            status: 'success',
            data: plLst
        });


    } catch (error) {
        next(error);
    }
};