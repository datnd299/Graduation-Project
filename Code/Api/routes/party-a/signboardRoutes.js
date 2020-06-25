const express = require('express');
const router = express.Router();

const authController = require('./../../controllers/authController');
const signboardController = require('./../../controllers/party-a/signboardController');


router.use(authController.protect);



router.post('/create-new', signboardController.createNew);
router.post('/get', signboardController.getMine);
router.post('/get-by-id', signboardController.getByID);
router.post('/report/all', signboardController.allSignboardsReport);

module.exports = router;