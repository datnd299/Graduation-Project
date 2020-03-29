const express = require('express');
const router = express.Router();

const authController = require('./../../controllers/authController');
const partyAAccountController = require('./../../controllers/party-a/accountController');
const userController = require('./../../controllers/userController');

router.use(authController.protect);



router.use(authController.restrictTo('partyAAdmin'));

router.post('/', partyAAccountController.getAccs);
router.post('/create-new', userController.createNewPAAccount);





module.exports = router;