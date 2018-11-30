
const express = require('express');
const router = express.Router();
const usersController = require('../controllers/usersController');
router.get('/',usersController.index);
router.get('/login', usersController.index);
router.post('/login', usersController.login);
router.get('/home/dashboard', usersController.dashboard);
router.get('/signup', usersController.signup);
router.post('/signup', usersController.signup);
router.get('/logout', usersController.logout);
router.get('/ir', usersController.ir);
router.get('/irUsuarios', usersController.irUsuarios);
router.post('/users/Agregar', usersController.Agregar);
router.get('/users/Eliminar/:id_usuario', usersController.Eliminar);
router.get('/users/updateUsers/:id_usuario', usersController.updateUsers);
router.post('/users/actulizarusu', usersController.Actualizarusu);

module.exports= router;
