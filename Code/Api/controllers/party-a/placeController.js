const AppError = require('../../utils/appError');
const PlaceRental = require('../../models/place/placeRental');
const PlaceForRent = require('../../models/place/placeForRent');
const Task = require('../../models/task/task');
const PartyA = require('../../models/user/partyA');
const PartyB = require('../../models/user/partyB');
const notificationController = require('../../controllers/notification/notificationController')
exports.rentPlace = async (req, res, next) => {
    try {
        var id = req.body.p_id;
        var pl = null
        if(id.length==6){
            pl= await PlaceForRent.findOne({code:id});
        }else{
            pl= await PlaceForRent.findById(id);
        }
        if (!pl) {
            return next(new AppError(200, 'fail', 'Không có mã này'), req, res, next);
        }
        
        const ptA = await PartyA.findOne({ "accs": req.acc._id});
        const plsRented = await PlaceRental.find({place_id:pl._id,$or:[ {'party_renter':ptA._id,status:{ $in: [1, -1] }}, {status:2} ]});
        console.log(plsRented);
        
        if(plsRented.length>0){
            return next(new AppError(200, 'fail', 'Bạn không thể yêu cầu thuê điểm treo này nữa'), req, res, next);
        }

        
        const plR = await PlaceRental.create({
            name: req.body.name,
          
            address: req.body.address,
            lat_lng: {
                lat: req.body.location.lat,
                lng: req.body.location.lng,
            },
            price: req.body.price,
            time_unit: req.body.time_unit,
            imgs: pl.imgs,
            place_id:pl,
            start:req.body.start,
            end:req.body.end,
            account_renter:req.acc,
            party_renter:ptA,
            
            
        });
        notificationController.sendRentPlaceRq(req,pl._id,plR);
        
        res.status(200).json({
            status: 'success',
            data: plR
        });


    } catch (error) {
        next(error);
    }
};
exports.getMyPlaceRentals = async (req, res, next) => {
    try {
        
        
        const ptB = await PartyB.findOne({ "accs": req.acc._id});

        places = await PlaceRental.aggregate([
            { $lookup:
              {
                from: 'placeforrents',
                localField: 'place_id',
                foreignField: '_id',
                as: 'place',
                
              }
            },
            { $lookup:
                {
                  from: 'partyas',
                  localField: 'party_renter',
                  foreignField: '_id',
                  as: 'pt_a',
                  
                }
              },
            { $match: { "place.owner": ptB._id } }
             
          ]);
          places.forEach(e=>{
              e.place = e.place[0];
              e.pt_a = e.pt_a[0];
              e.place_name = e.place.name;
              e.pt_a_name = e.pt_a.name;
          })
        
        res.status(200).json({
            status: 'success',
            data: places
        });


    } catch (error) {
        next(error);
    }
};
exports.getMyPlaces = async (req, res, next) => {
    try {
        const ptA = await PartyA.findOne({ "accs": req.acc._id});
        places = await PlaceRental.find({ "party_renter": ptA._id }).populate({
            path : 'place_id',
            populate : {
              path : 'owner'
            }
        }).populate({
            path : 'signboards',
        })
          places.forEach(e=>{
              
              e.pt_a_name = e.place_id.owner.name;
          })
        
        res.status(200).json({
            status: 'success',
            data: places
        });
    } catch (error) {
        next(error);
    }
};
exports.getMyPlacesReport = async (req, res, next) => {
    try {
        const ptA = await PartyA.findOne({ "accs": req.acc._id});
        places = await PlaceRental.find({ "party_renter": ptA._id }).populate({
            path : 'signboards',
        })
        places = JSON.parse(JSON.stringify(places));
        
        var tasks = await Task.find({ pt_a: ptA, });
        places.forEach(p=>{
            p.report ={
                fee:0,
                task:0,
                img:0,

            }
        })
        places.forEach((p,pI)=>{
            tasks.forEach((t,index)=>{
            
                if(t.fee_task){
                 
                    for(let i in t.fee_task.fee_detail){
                        if(t.fee_task.fee_detail[i].place_rental.toString()==p._id.toString()){
                            console.log('a');
                            
                            p.report.task+=1;
                            p.report.fee +=t.fee_task.fee_detail[i].amount;
                           
                        }
                    }
                   
                }
                else if(t.report_task_report){
                    if(t.report_task.place_rental.toString()==p._id.toString()){
                        p.report.task+=1;
                        t.report_task_report.signboards.forEach(sb=>{
                            if(sb.imgs.length){
                                p.report.img+=sb.imgs.length
                            }
                            
                        })
                    }
                }
                else if(t.check_task_report){
     
                    t.check_task_report.place_rental.forEach(pl=>{
                        if(pl.pl_id.toString()==p._id.toString()){
                            p.report.task+=1;
                            pl.signboards.forEach(sb=>{
                                p.report.img+=sb.imgs.length
                            })
                            
                        }
                    })
                    
                }else if(t.setup_task_report){
                    if(t.setup_task.place_rental.toString()==p._id.toString()){
                        p.report.task+=1;
                        t.setup_task_report.signboards.forEach(sb=>{
                            p.report.img+=sb.imgs.length;
                        })
                    }
                }
            })
        })
        
        res.status(200).json({
            status: 'success',
            data: places
        });
    } catch (error) {
        next(error);
    }
};
exports.getDetail = async (req, res, next) => {
    try {
        
        
      
        

        places = await PlaceRental.findById(req.body.id).populate('party_renter').populate({
            path : 'place_id',
            populate : {
              path : 'owner'
            }
          })
        //   places.forEach(e=>{
        //       e.place = e.place[0];
        //       e.pt_a = e.pt_a[0];
        //       e.place_name = e.place.name;
        //       e.pt_a_name = e.pt_a.name;
        //   })
        
        res.status(200).json({
            status: 'success',
            data: places
        });


    } catch (error) {
        next(error);
    }
};

exports.approvePlaceRental = async (req, res, next) => {
    try {
        
        var now = Date.now()
        var nStatus = req.body.status;
        place = await PlaceRental.findById(req.body.id).populate('party_renter').populate({
            path : 'place_id',
            populate : {
              path : 'owner'
            }
        })
        if(place.status==1||place.status=='0'){
            if(nStatus == 0){
               var pl=  await PlaceRental.findByIdAndUpdate(req.body.id,{status:nStatus});
               res.status(200).json({
                status: 'success',
                data: pl
                });
    
            }else{
                var plRented = await PlaceRental.findOne({place_id:place.place_id,start: { $lt: now },end: { $gt: now }, status : 2});
                if(plRented){
                    return next(new AppError(200, 'fail', 'Địa điểm đang được người khác thuê'), req, res, next);
                }else{
                    var pl=  await PlaceRental.findByIdAndUpdate(req.body.id,{status:nStatus});
                    res.status(200).json({
                        status: 'success',
                        data: pl
                        });
                }
                
            }
        }else{
            res.status(200).json({
                status: 'false',
                data: ''
            });
    
        }
        

        
        //   places.forEach(e=>{
        //       e.place = e.place[0];
        //       e.pt_a = e.pt_a[0];
        //       e.place_name = e.place.name;
        //       e.pt_a_name = e.pt_a.name;
        //   })
        
        

    } catch (error) {
        next(error);
    }
};

