const express = require('express');
const router = express.Router();

const authController = require('./../../controllers/authController');
const placeController = require('./../../controllers/party-a/placeController');
const userController = require('./../../controllers/userController');

router.use(authController.protect);



router.post('/rent-place', placeController.rentPlace);
router.post('/get-details', placeController.getDetail);
router.post('/get-places', placeController.getMyPlaces);
module.exports = router;