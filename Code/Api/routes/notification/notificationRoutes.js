const express = require('express');
const router = express.Router();
const authController = require('../../controllers/authController');
const notificationController = require('./../../controllers/notification/notificationController');

router.post('/test', notificationController.test);
router.use(authController.protect);

router.post('', notificationController.getMine);
router.post('/register-n-token', notificationController.registerNToken);






module.exports = router;