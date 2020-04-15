const express = require('express');
const rateLimit = require('express-rate-limit');
const helmet = require('helmet');
const mongoSanitize = require('express-mongo-sanitize');
const xss = require('xss-clean');
const hpp = require('hpp');
const cors = require('cors');


const userRoutes = require('./routes/userRoutes');
const placeForRentRoutes = require ('./routes/party-b/placeForRentRoutes');
const fileRoutes = require ('./routes/file/fileRoutes');
const pAAcountRoute = require('./routes/party-a/accountRoutes');
const placeRoutes = require('./routes/party-a/placeRoutes')
const partnerARoutes = require('./routes/party-a/partnerRoutes')
const signboardRoutes = require('./routes/party-a/signboardRoutes')

const notificationRoutes = require('./routes/notification/notificationRoutes');
const taskRoutes = require('./routes/task/taskRoutes');

const adminUserRoute = require('./routes/admin/adminUserRoutes');
const globalErrHandler = require('./controllers/errorController');
const AppError = require('./utils/appError');
const app = express();
var serveStatic = require('serve-static');
const fileUpload = require('express-fileupload');

app.use(serveStatic(__dirname + "/web"));
// Allow Cross-Origin requests
app.use(cors());

// Set security HTTP headers
app.use(helmet());

// Limit request from the same API 
const limiter = rateLimit({
    max: 1500,
    windowMs: 60 * 60 * 1000,
    message: 'Too Many Request from this IP, please try again in an hour'
});
app.use('/api', limiter);

// Body parser, reading data from body into req.body
app.use(express.json({
    limit: '15kb'
}));

var bodyParser = require('body-parser');
app.use(bodyParser.json({limit: '50mb'}));
app.use(bodyParser.urlencoded({limit: '50mb', extended: true}));

app.use(fileUpload());
// Data sanitization against Nosql query injection
app.use(mongoSanitize());

// Data sanitization against XSS(clean user input from malicious HTML code)
app.use(xss());

// Prevent parameter pollution
app.use(hpp());


// Routes
app.use('/api/v1/users', userRoutes);

app.use('/api/v1/admin/users', adminUserRoute);
app.use('/api/v1/party-b',placeForRentRoutes);
app.use('/api/v1/file',fileRoutes);
app.use('/api/v1/party-a/accounts',pAAcountRoute);
app.use('/api/v1/party-a/places',placeRoutes);
app.use('/api/v1/party-a/partners',partnerARoutes);
app.use('/api/v1/party-a/signboards',signboardRoutes);
app.use('/api/v1/tasks',taskRoutes);

app.use('/api/v1/ntf',notificationRoutes);


// handle undefined Routes
app.use('*', (req, res, next) => {
    const err = new AppError(404, 'fail', 'undefined route');
    next(err, req, res, next);
});

app.use(globalErrHandler);

module.exports = app;