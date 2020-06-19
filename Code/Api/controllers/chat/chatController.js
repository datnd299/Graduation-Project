const AppError = require('./../../utils/appError');
const Room = require('../../models/chat/room');
const Message = require('../../models/chat/message');
const PartyA = require('../../models/user/partyA');
const PartyB= require('../../models/user/partyB');
exports.getMyRooms = async (req, res, next) => {
    try {
        if(req.acc.role.includes('partyA')){
            const ptA = await PartyA.findOne({ "accs": req.acc._id });
            var rooms = await Room.find({pt_a:ptA}).populate('pt_b');
    
            
            var rooms2 = [];
            rooms.forEach(e=>{
                rooms2.push(e.pt_b);
            })
            res.status(200).json({
                status: 'success',
                data: rooms2
            });
        }else{
            const ptB = await PartyB.findOne({ "accs": req.acc._id });
            var rooms = await Room.find({pt_b:ptB}).populate('pt_a');
            var rooms2 = [];
            rooms.forEach(e=>{
                rooms2.push(e.pt_a);
            })
            res.status(200).json({
                status: 'success',
                data: rooms2
            });
            
        }
        
    } catch (error) {
        next(error);
    }
};

exports.getMessages = async (req, res, next) => {
    try {
  
       
        if(req.acc.role.includes('partyA')){
            const ptA = await PartyA.findOne({ "accs": req.acc._id });
            var room = await Room.findOne({pt_a:ptA,pt_b:req.body.pt_id});
            if(room){
                var mess = await Message.find({room:room}).sort({ createAt : 'asc'}).populate('sender');
                mess = JSON.parse(JSON.stringify(mess));
              
                mess.forEach(e=>{
                   
                    
                    if(e.sender.role.includes('partyA')){
                      
                        e.s = 'me';
                        
                       
                    }
           
                })
                res.status(200).json({
                    status: 'success',
                    data: mess
                });
            }else{
                res.status(200).json({
                    status: 'success',
                    data: []
                });
            }
            
        }else{
            const ptB = await PartyB.findOne({ "accs": req.acc._id });
            var room = await Room.findOne({pt_a:req.body.pt_id,pt_b:ptB});
            if(room){
                var mess = await Message.find({room:room}).sort({ createAt : 'asc'}).populate('sender');
                mess = JSON.parse(JSON.stringify(mess));
              
                mess.forEach(e=>{
                   
                    
                    if(e.sender.role.includes('partyB')){
                      
                        e.s = 'me';
                        
                       
                    }
           
                })
                res.status(200).json({
                    status: 'success',
                    data: mess
                });
            }else{
                res.status(200).json({
                    status: 'success',
                    data: []
                });
            }
            
        }
        
    } catch (error) {
        next(error);
    }
};