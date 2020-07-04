const express = require('express');
const router = express.Router();

const authController = require('./../../controllers/authController');
const taskController = require('./../../controllers/task/taskController');


router.use(authController.protect);



router.post('/create-new', taskController.createNew);
router.post('/get-all-of-my-pt', taskController.getAllOfMyPT);
router.post('/get-by-id', taskController.getTaskById);
router.post('/report', taskController.reportTask);
router.post('/approve', taskController.approveTask);

router.post('/get-by-place', taskController.getTasksByPlace);

module.exports = router;