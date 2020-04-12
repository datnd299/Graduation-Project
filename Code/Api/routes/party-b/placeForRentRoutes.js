const express = require('express');
const router = express.Router();
const placeForRentController = require('../../controllers/party-b/placeForRentController');
const placeRentalController = require('../../controllers/party-a/placeController');
const authController = require('./../../controllers/authController');

router.use(authController.protect);

router.post('/place-for-rent/get-details', placeForRentController.getDetails);

router.post('/place-for-rent/get-by-party', placeForRentController.getByParty);
router.use(authController.restrictTo('partyB'));

router.post('/place-for-rent/create-new-place', placeForRentController.createNew);
router.post('/place-for-rent/gen-new-code', placeForRentController.genNewCode);
router.post('/place-for-rent/get-mine', placeForRentController.getMine);
router.post('/place-for-rent/get-places-rental', placeRentalController.getMyPlaceRentals);

router.post('/place-for-rent/approve-place-rental', placeRentalController.approvePlaceRental);




module.exports = router;