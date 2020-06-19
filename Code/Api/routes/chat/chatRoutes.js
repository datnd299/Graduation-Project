const express = require('express');
const router = express.Router();
const chatController = require('../../controllers/chat/chatController');
const authController = require('./../../controllers/authController');

router.use(authController.protect);

router.post('/get-my-rooms',chatController.getMyRooms);
router.post('/get-messages',chatController.getMessages);


module.exports = router;