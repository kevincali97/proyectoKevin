
const dbConnection= require('../../config/dbConnection');
const conn= dbConnection();
const controller= {};


controller.list= (req,res)=>{
conn.query('SELECT * FROM news', (err, result)=>{
    if(err){
        res.json(err);
    }
res.render ('news', {
    news:result

});
});
}

controller.add = (req,res)=>{
    const {title,news} =req.body;
    
        conn.query('INSERT INTO news SET ?',
    {
        title,
        news
    }, (err,result)=>{
        res.redirect('/news');
    });
    };
    

    controller.eliminar = (req,res)=>{
        

        const id=req.params.id_noticia;
            conn.query('DELETE from news where id_news = ?',
       
            id
        
        , (err,result)=>{
            res.redirect('/news');
        });
    };


   
   
 
module.exports= controller;
