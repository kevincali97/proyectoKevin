const fs= require('fs');
fs.readFile('./Filesystem/data.txt','utf-8',(error,data)=> {
 if(error){
 console.log(`Error ${error}`);
 }else {
 console.log(data);
 }
});