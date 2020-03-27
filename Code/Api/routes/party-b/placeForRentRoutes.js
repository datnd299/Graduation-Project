const express = require('express');
const router = express.Router();
const placeForRentController = require('../../controllers/party-b/placeForRentController');
const authController = require('./../../controllers/authController');

router.use(authController.protect);

router.use(authController.restrictTo('partyB'));

router.post('/place-for-rent/create-new-place', placeForRentController.createNew);
router.post('/place-for-rent/get-mine', placeForRentController.getMine);



module.exports = router;