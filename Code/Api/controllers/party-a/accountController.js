const AppError = require('./../../utils/appError');
const PartyA = require('../../models/user/partyA');
exports.createNew = async (req, res, next) => {
    try {
        const {
            acc_name,
            name,
            email,
            password,
            password_confirm,
            role,
        } = req.body;

        
        
        res.status(200).json({
            status: 'success',
            data: pl
        });


    } catch (error) {
        next(error);
    }
};

exports.getAccs =async (req, res, next) => {
    try {
       
        

        const ptA = await PartyA.findOne({ "accs": req.acc._id}).populate('accs');
        


        res.status(200).json({
            status: 'success',
            data: ptA
        });
        
        
    } catch (error) {
        next(error);
    }
}