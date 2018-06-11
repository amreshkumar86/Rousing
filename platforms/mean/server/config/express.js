/**
 * Express configuration
 */

'use strict';

import express from 'express';
import favicon from 'serve-favicon';
import morgan from 'morgan';
import compression from 'compression';
import bodyParser from 'body-parser';
import methodOverride from 'method-override';
import cookieParser from 'cookie-parser';
import errorHandler from 'errorhandler';
import path from 'path';
import lusca from 'lusca';
import config from './environment';
import passport from 'passport';
import session from 'express-session';
import connectMongo from 'connect-mongo';
import mongoose from 'mongoose';
var MongoStore = connectMongo(session);

export default function(app) {
    var env = app.get('env');

    if(env === 'development' || env === 'test') {
        app.use(express.static(path.join(config.root, '.tmp')));
    }

    if(env === 'production') {
        app.use(favicon(path.join(config.root, 'client', 'favicon.ico')));
    }

    app.set('appPath', path.join(config.root, 'client'));
    app.use(express.static(app.get('appPath')));
    app.use(morgan('dev'));

    app.set('views', `${config.root}/server/views`);
    app.engine('html', require('ejs').renderFile);
    app.set('view engine', 'html');
    app.use(compression());
    app.use(bodyParser.urlencoded({ extended: false }));
    app.use(bodyParser.json());
    app.use(methodOverride());
    app.use(cookieParser());
    app.use(passport.initialize());


    // Persist sessions with MongoStore / sequelizeStore
    // We need to enable sessions for passport-twitter because it's an
    // oauth 1.0 strategy, and Lusca depends on sessions
    app.use(session({
        secret: config.secrets.session,
        saveUninitialized: true,
        resave: false,
        store: new MongoStore({
            mongooseConnection: mongoose.connection,
            db: 'mean'
        })
    }));

    /**
     * Lusca - express server security
     * https://github.com/krakenjs/lusca
     */
    if(env !== 'test' && env !== 'development' && !process.env.SAUCE_USERNAME) { // eslint-disable-line no-process-env
        app.use(lusca({
            csrf: true,
            xframe: 'SAMEORIGIN',
            hsts: {
                maxAge: 31536000, //1 year, in seconds
                includeSubDomains: true,
                preload: true
            },
            xssProtection: true
        }));
    }

    if(env === 'development' || env === 'test') {
        app.use(errorHandler()); // Error handler - has to be last
    }

    app.use(function (req, res, next) {

        // Website you wish to allow to connect
        res.setHeader('Access-Control-Allow-Origin', 'https://www.rousingdesigns.com');

        // Request methods you wish to allow
        res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');

        // Request headers you wish to allow
        res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type,X-Auth-Token');

        // Set to true if you need the website to include cookies in the requests sent
        // to the API (e.g. in case you use sessions)
        res.setHeader('Access-Control-Allow-Credentials', true);

        // Pass to next layer of middleware
        next();
    });
}
