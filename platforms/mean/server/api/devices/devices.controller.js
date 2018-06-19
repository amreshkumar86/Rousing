'use strict';

import ThirdPartyConnection from '../thirdPartyConnection/thirdPartyConnection.model';
import config from '../../config/environment';
import lifx from 'lifx-http-api';
import async from 'async';

function validationError(res, statusCode) {
    statusCode = statusCode || 422;
    return function(err) {
        return res.status(statusCode).json(err);
    };
}

function handleError(res, statusCode) {
    statusCode = statusCode || 500;
    return function(err) {
        return res.status(statusCode).send(err);
    };
}

// Gets a list of all ThirdPartyConnection for the current user
export function index(req,res,next) {
    return ThirdPartyConnection
    .find({user: req.user})
    .then(connections => {
    	async.map(connections,(connection,callback)=>{
            new lifx({bearerToken:connection.accessToken})
            .listLights('all').then(lights=>{
                callback(null,{name:connection.name,id:connection.id,lights})
            }, handleError(res));
        },(err,results)=>{
           res.json(results);
        });
    })
    .catch(handleError(res));
}

export function lightStatus(req,res,next) {
    var lightId = req.params.lightId;
    var locationId = req.headers.locationid;
    ThirdPartyConnection
    .find({_id: locationId})
    .then(connections => {
        if(connections && connections.length == 1) {
            var connection = connections[0];
            var accessToken = connection.accessToken;
            new lifx({bearerToken:connection.accessToken})
            .listLights('id:'+lightId).then(lights=>{
                if(lights && lights.length == 1) {
                    res.json({
                        success:true,
                        light:lights[0]
                    });
                }
                else {
                    res.json({success:false});
                }
            }, handleError(res));
        }
    })
    .catch(handleError(res));
}

export function groupStatus(req,res,next) {
    var groupId = req.params.groupId;
    var locationId = req.headers.locationid;
    ThirdPartyConnection
    .find({_id: locationId})
    .then(connections => {
        if(connections && connections.length == 1) {
            var connection = connections[0];
            var accessToken = connection.accessToken;
            new lifx({bearerToken:connection.accessToken})
            .listLights('group_id:'+groupId).then(lights=>{
                if(lights && lights.length > 0) {
                    res.json({
                        success:true,
                        lights:lights
                    });
                }
                else {
                    res.json({success:false});
                }
            }, handleError(res));
        }
    })
    .catch(handleError(res));
}

/**
 * Creates a third party connection
 */
// export function create(req, res) {
//     var newConn = new ThirdPartyConnection();
//     newConn.name = req.body.name;
//     newConn.authToken = req.body.authToken;
//     newConn.user = req.user;
//     return newConn.save()
//         .then(function(conn) {
//             res.json({ success: true });
//         })
//         .catch(validationError(res));
// }

// function validationError(res, statusCode) {
//     statusCode = statusCode || 422;
//     return function(err) {
//         return res.status(statusCode).json(err);
//     };
// }


// /**
//  * Get list of users
//  * restriction: 'admin'
//  */
// export function index(req, res) {
//     return User.find({}, '-salt -password').exec()
//         .then(users => {
//             res.status(200).json(users);
//         })
//         .catch(handleError(res));
// }

// /**
//  * Creates a new user
//  */
// export function create(req, res) {
//     var newUser = new User(req.body);
//     newUser.provider = 'local';
//     newUser.role = 'user';
//     return newUser.save()
//         .then(function(user) {
//             var token = jwt.sign({ _id: user._id }, config.secrets.session, {
//                 expiresIn: 60 * 60 * 5
//             });
//             res.json({ token });
//         })
//         .catch(validationError(res));
// }

// /**
//  * Get a single user
//  */
// export function show(req, res, next) {
//     var userId = req.params.id;

//     return User.findById(userId).exec()
//         .then(user => {
//             if(!user) {
//                 return res.status(404).end();
//             }
//             res.json(user.profile);
//         })
//         .catch(err => next(err));
// }

// *
//  * Deletes a user
//  * restriction: 'admin'
 
// export function destroy(req, res) {
//     return User.findByIdAndRemove(req.params.id).exec()
//         .then(function() {
//             res.status(204).end();
//         })
//         .catch(handleError(res));
// }

// /**
//  * Change a users password
//  */
// export function changePassword(req, res) {
//     var userId = req.user._id;
//     var oldPass = String(req.body.oldPassword);
//     var newPass = String(req.body.newPassword);

//     return User.findById(userId).exec()
//         .then(user => {
//             if(user.authenticate(oldPass)) {
//                 user.password = newPass;
//                 return user.save()
//                     .then(() => {
//                         res.status(204).end();
//                     })
//                     .catch(validationError(res));
//             } else {
//                 return res.status(403).end();
//             }
//         });
// }

// /**
//  * Get my info
//  */
// export function me(req, res, next) {
//     var userId = req.user._id;

//     return User.findOne({ _id: userId }, '-salt -password').exec()
//         .then(user => { // don't ever give out the password or salt
//             if(!user) {
//                 return res.status(401).end();
//             }
//             return res.json(user);
//         })
//         .catch(err => next(err));
// }

// /**
//  * Authentication callback
//  */
// export function authCallback(req, res) {
//     res.redirect('/');
// }
