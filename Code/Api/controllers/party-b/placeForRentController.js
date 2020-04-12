const PlaceForRent = require('../../models/place/placeForRent');
const PartyB = require('../../models/user/partyB');
const AppError = require('./../../utils/appError');
const { makeCode } = require('../../utils/index');
exports.createNew = async (req, res, next) => {
    try {
        var acc = req.acc;
        var ptB = await PartyB.findOne({ "accs": acc._id });
        if (!ptB) {
            return next(new AppError(200, 'fail', 'Bạn không phải đối tác'), req, res, next);
        }
        let done = false;

        for (let i = 0; i < 5; i++) {
          try {
            const pl = await PlaceForRent.create({
                name: req.body.name,
                code:makeCode(),
                address: req.body.address,
                lat_lng: {
                    lat: req.body.location.lat,
                    lng: req.body.location.lng,
                },
                price: req.body.price,
                time_unit: req.body.time_unit,
                imgs: req.body.imgs,

                accs: acc,
                owner: ptB
            });
            res.status(200).json({
                status: 'success',
                data: pl
            });
            return;
          } catch (error) {
              
          }
                
        
            
        }
        if(!done){
            return next(new AppError(200, 'fail', 'Không tạo được mã thành công, thử lại sau'), req, res, next);
        }

        


    } catch (error) {
        next(error);
    }
};




exports.getMine = async (req, res, next) => {
    try {
        var acc = req.acc;
        var ptB = await PartyB.findOne({ "accs": acc._id });
        if (!ptB) {
            return next(new AppError(200, 'fail', 'Bạn không phải đối tác'), req, res, next);
        }

        const plLst = await PlaceForRent.find({
            owner: ptB._id
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
        if (!ptB) {
            return next(new AppError(200, 'fail', 'Không có đơn vị này'), req, res, next);
        }

        const plLst = await PlaceForRent.find({
            owner: ptB._id
        });
        ptB['places'] = plLst;

        res.status(200).json({
            status: 'success',
            data: {
                pt_b: ptB,
                places: plLst
            }

        });


    } catch (error) {
        next(error);
    }
};

exports.getDetails = async (req, res, next) => {
    try {
        var id = req.body.id;
        var pl_b = null
        if(id.length==6){
            pl_b= await PlaceForRent.findOne({code:id}).populate('owner');
        }else{
            pl_b= await PlaceForRent.findById(id).populate('owner');
        }
        var ptB = await PartyB.findOne({ "accs": req.acc._id });
        if (!ptB) {
            ptB={};
        }
         
        if (!pl_b) {
            return next(new AppError(200, 'fail', 'Không có đơn vị này'), req, res, next);
        }
        var is_mine_b=false;
     
        
        if(pl_b.owner.equals(ptB._id)){
            is_mine_b = true;
        }else{
            is_mine_b = false;
        }

        

        res.status(200).json({
            status: 'success',
            data: {
                pl_b,
                is_mine_b:is_mine_b
                
            }

        });


    } catch (error) {
        next(error);
    }
};


exports.genNewCode = async (req, res, next) => {
    try {
        var id = req.body.id;
        var pl_b = null
        if(id.length==6){
            pl_b= await PlaceForRent.findOne({code:id});
        }else{
            pl_b= await PlaceForRent.findById(id);
        }
         
        if (!pl_b) {
            return next(new AppError(200, 'fail', 'Không có đơn vị này'), req, res, next);
        }

        

        let done = false;

        for (let i = 0; i < 5; i++) {
          try {
              var nCode = makeCode();
            var l =await PlaceForRent.findByIdAndUpdate(pl_b._id,{code:nCode})
            done = true;
            l.code = nCode
            
            res.status(200).json({
                status: 'success',
                data: l
            });
     
            return;
          } catch (error) {
           
             
              
          }
                
            
        }


        if(!done){
            return next(new AppError(200, 'fail', 'Không tạo được mã thành công, thử lại sau'), req, res, next);
        }


    } catch (error) {
        next(error);
    }
};