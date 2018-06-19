'use strict';

import {Router} from 'express';
import * as controller from './devices.controller';
import * as auth from '../../auth/auth.service';

var router = Router();

// router.get('/', auth.hasRole('admin'), controller.index);
// router.delete('/:id', auth.hasRole('admin'), controller.destroy);
router.get('/',auth.isAuthenticated(),controller.index);
router.get('/light/:lightId',auth.isAuthenticated(),controller.lightStatus);
router.get('/group/:groupId',auth.isAuthenticated(),controller.groupStatus);
// router.put('/:id/password', auth.isAuthenticated(), controller.changePassword);
// router.get('/:id', auth.isAuthenticated(), controller.show);
// router.post('/', auth.isAuthenticated(), controller.create);

module.exports = router;
