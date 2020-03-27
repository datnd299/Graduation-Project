const express = require('express');
const router = express.Router();
const fileController = require('../../controllers/file/fileController');
const authController = require('./../../controllers/authController');





router.post('/upload', fileController.upload);

router.get('/get/:file', fileController.getFile);

module.exports = router;