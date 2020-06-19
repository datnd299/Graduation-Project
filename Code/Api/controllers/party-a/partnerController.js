const AppError = require('../../utils/appError');
const PlaceRental = require('../../models/place/placeRental');
const PlaceForRent = require('../../models/place/placeForRent');
const PartyA = require('../../models/user/partyA');
const PartyB = require('../../models/user/partyB');
const notificationController = require('../../controllers/notification/notificationController')
const {changeAlias} = require('../../utils/index')
exports.getPartner = async (req, res, next) => {
    try {
        var q = req.body.q;
        const ptA = await PartyA.findOne({ "accs": req.acc._id});
        
        ptB = await PlaceRental.aggregate([
            {$match: { "party_renter": ptA._id }},
            { $lookup:
                {
                  from: 'placeforrents',
                  localField: 'place_id',
                  foreignField: '_id',
                  as: 'place',
                  
                }
              },
             {"$group" : {_id:"$place.owner", count:{$sum:1}}},
             { $lookup:
                {
                  from: 'partybs',
                  localField: '_id',
                  foreignField: '_id',
                  as: 'pt_b',
                  
                }
              },
        ])
        ptB.forEach((e,i)=>{
            
            
            if(e.pt_b[0]){
                e.pt_b[0].count_places = e.count;
                ptB[i] = e.pt_b[0];
                
                
            }
        })
        var pt = [];
        if(q&&q.length>0){
            ptB.forEach((e,i)=>{
            
              
                
                if(changeAlias(e.name+e.address+e.district+e.email+e.phone+e.province).includes(changeAlias(q))){
                    pt.push(e)
                }
            })
            
        }else{
            pt=ptB;
        }
        res.status(200).json({
            status: 'success',
            data: pt
        });


    } catch (error) {
        next(error);
    }
};