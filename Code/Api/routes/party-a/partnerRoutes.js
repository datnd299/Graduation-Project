const express = require('express');
const router = express.Router();

const authController = require('./../../controllers/authController');
const partnerController = require('./../../controllers/party-a/partnerController');
const userController = require('./../../controllers/userController');

router.use(authController.protect);



router.post('/get', partnerController.getPartner);

module.exports = router;