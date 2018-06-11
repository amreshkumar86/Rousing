/**
 * Main application routes
 */

'use strict';

import errors from './components/errors';
import path from 'path';
var url = require('url');
export default function(app) {
    // Insert routes below
    app.use('/api/things', require('./api/thing'));
    app.use('/api/users', require('./api/user'));
    app.use('/api/thirdPartyConnection', require('./api/thirdPartyConnection'));
    app.use('/api/devices', require('./api/devices'));
    app.use('/auth', require('./auth').default);
    app.use('/redirectAppUrl',function(req,res,next){
        
        var queryData = url.parse(req.url, true).query;
        console.log(queryData);
        //res.redirect('https://www.rousingdesigns.com/#!/redirectAppUrl'+req.url);
        res.writeHead(301,
          {Location: 'https://www.rousingdesigns.com/#!/redirectAppUrl/'+queryData['code']+'/'+queryData['scope']+'/'+queryData['state']}
        );
        res.end();
    });

    // All undefined asset or api routes should return a 404
    app.route('/:url(api|auth|components|app|bower_components|assets)/*')
        .get(errors[404]);

    // All other routes should redirect to the app.html
    app.route('/*')
        .get((req, res) => {
            res.sendFile(path.resolve(`${app.get('appPath')}/app.html`));
        });
}
