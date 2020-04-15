const express = require('express');
const router = express.Router();

const authController = require('./../../controllers/authController');
const taskController = require('./../../controllers/task/taskController');


router.use(authController.protect);



router.post('/create-new', taskController.createNew);
router.post('/get-all-of-my-pt', taskController.getAllOfMyPT);
router.post('/get-by-id', taskController.getTaskById);


module.exports = router;