const express = require('express');
const router = express.Router();
const userController = require('../../controllers/userController');
const authController = require('../../controllers/authController');

// Protect all routes after this middleware
 router.use(authController.protect);

 router.use(authController.restrictTo('partyAAdmin','admin'));
 router.post('/approve-acc', userController.approveAcc);
// Only admin have permission to access for the below APIs
router.use(authController.restrictTo('admin'));
router.post('/partyas', userController.getAllPartyA);

router.post('/sysinfo', userController.getSystemInfo);

router.post('/partybs', userController.getAllPartyB);
router.post('/party-a/get-accs', userController.getPartyAAccs);

router.post('/party-a/approve-party', userController.approvePartyA);
router.post('/party-b/approve-party', userController.approvePartyB);




module.exports = router;