const dbConnection = require('../../config/dbConnection');
const conn = dbConnection();
const controller ={};


// Controlado de Index
controller.index = (req, res)=>{
var message = '';
res.render('index',{message: message});
};


//Controlador para que el usuario se registre
controller.signup = (req, res)=>{

    message = '';
 const { first_name,last_name,user_name, password } = req.body

 if(req.method == "POST"){

    conn.query( 'INSERT INTO users SET ? ', {
    first_name,
    last_name,
    user_name,
    password
    }  ,(err,result) => {

        message = "Felicitaciones! Su cuenta ha sido creada.";
        res.render('signup',{message: message});
       
        }); 
    } else {
        res.render('signup');
        }
       

};   
 

//Controlador para el Inicio de sesión
controller.login = (req, res)=>{
    var message = '';
    var sess = req.session;
   
    if(req.method == "POST"){
    var post = req.body;
    var name= post.user_name;
    var pass= post.password;
   
    conn.query("SELECT id, first_name, last_name, user_name FROM `users`WHERE `user_name`='"+name+"' and password = '"+pass+"'", (err,result)=>{
    if(result.length){
    sess.userId = result[0].id;
    sess.user = result[0];
    res.redirect('/home/dashboard');
    }
    else{
    message = 'Credenciales incorrectas.';
    res.render('index.ejs',{message: message});
    }
   
    });
    } else {
    res.render('index',{message: message});
    }
   };


   //Controlador para
controller.dashboard = (req, res, next)=>{

    var user = req.session.user,
    userId = req.session.userId;
   
    if(userId == null){
    res.redirect("/login");
    return;
}

conn.query("SELECT * FROM `login_details` WHERE`id`='"+userId+"'",(err,result)=>{
 res.render('dashboard', {user:user});

 });
};

controller.logout = (req, res)=>{

    req.session.destroy((err)=>{
        res.redirect('/login');
    })
};   


controller.ir = (req, res, next)=>{

    var user = req.session.user,
    userId = req.session.userId;
   
    if(userId == null){
    res.redirect("/login");
    return;
    }else{
        conn.query('SELECT * FROM news', (err, result)=>{
            if(err){
                res.json(err);
            }
        res.render ('news', {
            news:result
        
        });
        });
    }


};

controller.list= (req,res)=>{
    conn.query('SELECT * FROM users', (err, result)=>{
        if(err){
            res.json(err);
        }
    res.render ('users', {
        news:result
    
    });
    });
}

controller.Agregar = (req,res)=>{
    const {first_name,last_name,user_name,password} =req.body;
    
        conn.query('INSERT INTO users SET ?',
    {
        first_name,
        last_name,
        user_name,
        password
    }, (err,result)=>{
        res.redirect('/irUsuarios');
    });
};

controller.Eliminar = (req,res)=>{
        

    const id=req.params.id_usuario;
        conn.query('DELETE from users where id = ?',
   
        id
    
    , (err,result)=>{
        res.redirect('/irUsuarios');

    });
};


controller.irUsuarios = (req, res, next)=>{

    var user = req.session.user,
    userId = req.session.userId;
   
    if(userId == null){
    res.redirect("/login");
    return;
    }else{
        conn.query('SELECT * FROM users', (err, result)=>{
            if(err){
                res.json(err);
            }
        res.render ('users', {
            users:result
        
        });
        });
    }


};


controller.updateUsers= (req,res)=>{
   
    const id=req.params.id_usuario

    res.render ('Ausuarios', {
        actualizado:id
    
    });
    
}

controller.Actualizarusu = (req,res)=>{
    const {first_name,last_name,user_name,password,id} =req.body;
    
    
        conn.query('UPDATE users SET first_name = ?, last_name = ?, user_name = ?, password = ? where id = ?',
        [first_name, last_name, user_name, password,id]
        
        ,(err,result)=>{

        if(err){

        res.json(err);
    }

        res.redirect('/irUsuarios');
       
    });
    
}



module.exports = controller;
   


