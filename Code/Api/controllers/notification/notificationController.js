const NToken = require('../../models/user/ntoken');
var { SendToClient } = require('./fcm');
const Notification = require('../../models/notificationModel');
const PlaceForRent = require('../../models/place/placeForRent');
const PlaceRental = require('../../models/place/placeRental');
const PartyB = require('../../models/user/partyB');
const PartyA = require('../../models/user/partyA');
exports.registerNToken = async (req, res, next) => {
    try {
        var ntoken = req.body.ntoken
        await NToken.updateOne(
            { token: ntoken },   // Query parameter
            {                     // Replacement document
                token: ntoken,

                acc: req.acc._id
            },
            { upsert: true }      // Options
        )


        console.log(ntoken);
        

        res.status(200).json({
            status: 'success',
            data: ntoken
        });


    } catch (error) {
        next(error);
    }
};
exports.getMine = async (req, res, next) => {
    try {
        var acc = req.acc;
        var data = [];
        if(acc.role.includes('partyA')){
            acc.ptA = await PartyA.findOne({accs:acc._id});
        }else if(acc.role.includes('partyB')){
         
            
            acc.ptB = await PartyB.findOne({accs:acc._id});
            data = await Notification.find();
        }
        
       
            
        
    
        res.status(200).json({
            status: 'success',
            data: data
        });


    } catch (error) {
        next(error);
    }
};
exports.sendToPartyB = async (req,plBId,type,info) => {
    try {

        var PlaceRental= await PlaceForRent.findById(plBId).populate('owner')
        
        
        var tokens = await NToken.find({acc:plB.owner.accs})
        
        await Notification.create({
            title:title,
            content:content,
            type:type,
            to_p_a:plBId,
            from:req.acc._id,
            info:info

        });
        

        SendToClient({
            token: 'd4jKe11QsRM:APA91bEdKWJfcwTFbyI26GTynTogaKtHOF8_iym_RgVaHxcOomsPbUefHzYF6gm0ObqiUOlWvrgE6tOH10ZfPWUpfZcoJiey1SBGZzC7TvGlOPW9k6neMbmFf33YfsXDPQJyU42u__Ap',
            title: 'sdfasd', body: 'asdfasdf'
        })
        res.status(200).json({
            status: 'success',

        });



    } catch (error) {
        console.log(error);
        
        // next(error);
    }
};

exports.sendRentPlaceRq = async (req,plBId,info) => {
    try {
        
        var plB= await PlaceForRent.findById(plBId).populate('owner')
        
        
        var tokens = await NToken.find({acc:plB.owner.accs})
        
        const nt = await Notification.create({
            title:"Yêu cầu làm đối tác",
            content:info.party_renter.name,
            type:'rentPlaceRq',
            to_p_a:plBId,
            from:req.acc._id,
            info:info

        });
        tokens.forEach(t=>{
            SendToClient({
                token: t.token,
                title: nt.title, 
                body: nt.content
            })
        })
        
    } catch (error) {
        console.log(error);
        
        // next(error);
    }
}
exports.sendNewTaskToB = async (plRt,info) => {
    console.log();
    
    try {
        
        var pl= await PlaceRental.findById(plRt).populate({
            path:'place_id',
            populate:'owner'
        });
        
        
        console.log(pl);
        
        
         var tokens = await NToken.find({acc:pl.place_id.owner.accs})
        console.log(tokens);
        
        const nt = await Notification.create({
            title:"Nhiệm vụ mới",
            content:'Lúc ' +info.start.toString('dd/MM/yyyy HH:mm'),
            type:'task',
            to_p_a:pl.place_id.owner,
            to:pl.place_id.owner.accs,
            from:null,
            info:info

        });
         tokens.forEach(t=>{
            SendToClient({
                 token: t.token,
                // token:'d4jKe11QsRM:APA91bEdKWJfcwTFbyI26GTynTogaKtHOF8_iym_RgVaHxcOomsPbUefHzYF6gm0ObqiUOlWvrgE6tOH10ZfPWUpfZcoJiey1SBGZzC7TvGlOPW9k6neMbmFf33YfsXDPQJyU42u__Ap',
                title: nt.content, 
                body: nt.title
            })
         })
        
    } catch (error) {
        console.log(error);
        
        // next(error);
    }
}


exports.test = async (req, res, next) => {
    try {



        SendToClient({
            token: 'clWRXny5UQCmfpoNWZ4haY:APA91bHa_Frbhk9XsaZU8nexveLTcEVz4tPndS97mJ07l84-ULen0oF7D3-7jhfRneoxc8tnR3EHsnB5KBpGTwXQNuZ19xLj9Us1xoRGd1fHflqtULitiPr7s6pn4B2k4KpZvYXc5VAC',
            title: 'sdfasd', body: 'asdfasdf'
        })
        res.status(200).json({
            status: 'success',

        });



    } catch (error) {
        next(error);
    }
};