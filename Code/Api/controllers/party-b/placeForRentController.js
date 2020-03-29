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

exports.getByParty = async (req, res, next) => {
    try {
        var id = req.body.p_id;
        var ptB = await PartyB.findById(id);
        if(!ptB){
            return next(new AppError(200, 'fail', 'Không có đơn vị này'), req, res, next);
        }
        
        const plLst = await PlaceForRent.find({
            owner:ptB._id
        });
        ptB['places'] = plLst;

        res.status(200).json({
            status: 'success',
            data: {pt_b:ptB,
            places:plLst}
            
        });


    } catch (error) {
        next(error);
    }
};