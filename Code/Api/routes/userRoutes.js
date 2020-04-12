const express = require('express');
const router = express.Router();
const userController = require('../controllers/userController');
const authController = require('./../controllers/authController');


router.post('/login', authController.login);
router.post('/signup', authController.signup);
router.post('/change-password', authController.changePass);
router.post('/signup/party-a', userController.partyASignUp);
router.post('/signup/party-b', userController.partyBSignUp);
router.post('/logout', authController.logout);
// Protect all routes after this middleware
 router.use(authController.protect);




router.post('/my-info',userController.getMyInfo)

router.post('/update-my-info', userController.updateMyAcc);



// Only admin have permission to access for the below APIs
router.use(authController.restrictTo('partyAAdmin'));
router.post('/create-pa-account', userController.createNewPAAccount);



 router.use(authController.restrictTo('admin'));

router
    .route('/')
    .get(userController.getAllUsers);


module.exports = router;