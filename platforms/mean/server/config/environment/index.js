'use strict';
/*eslint no-process-env:0*/

import path from 'path';
import _ from 'lodash';

/*function requiredProcessEnv(name) {
  if(!process.env[name]) {
    throw new Error('You must set the ' + name + ' environment variable');
  }
  return process.env[name];
}*/

// All configurations will extend these options
// ============================================
var all = {
    env: process.env.NODE_ENV,

    // Root path of server
    root: path.normalize(`${__dirname}/../../..`),

    // dev client port
    clientPort: process.env.CLIENT_PORT || 3000,

    // Server port
    port: process.env.PORT || 443,

    // Server IP
    ip: process.env.IP || '0.0.0.0',

    // Should we populate the DB with sample data?
    seedDB: true,

    // Secret for session, you will want to change this and make it an environment variable
    secrets: {
        session: 'shhhhhhared-secret'
    },

    // MongoDB connection options
    mongo: {
        options: {
            db: {
                safe: true
            }
        }
    },

    facebook: {
        clientID: process.env.FACEBOOK_ID || 'id',
        clientSecret: process.env.FACEBOOK_SECRET || 'secret',
        callbackURL: `${process.env.DOMAIN || ''}/auth/facebook/callback`
    },

    google: {
        clientID: process.env.GOOGLE_ID || 'id',
        clientSecret: process.env.GOOGLE_SECRET || 'secret',
        callbackURL: `${process.env.DOMAIN || ''}/auth/google/callback`
    },

    lifx: {
        clientID: process.env.LIFX_ID || '28fc5a151c53c85053a3992b3eb42643aa4f26d821af7d28eed1b2e6e941c877',
        clientSecret: process.env.LIFX_SECRET || 'b2cd2dafd240a9b2eeebda328c73f9f8ec92f8a1bb022aea5bcd5ec8cf669f8c',
    }
};

// Export the config object based on the NODE_ENV
// ==============================================
module.exports = _.merge(
    all,
    require('./shared').default,
    require(`./${process.env.NODE_ENV}.js`) || {});
