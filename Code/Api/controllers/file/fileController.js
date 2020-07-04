
var setting = require("./../../setting");
const colors=['r','g','b'];
const Jimp = require('jimp');
const md5 = require('md5');
const AppError = require('./../../utils/appError');
const SImage = require('../../models/image/sImage');
const agenda = require('../../utils/agenda');
const latLngSchema = require('../../models/other/latLng')
var util= require('util');

const uuid = require('uuid')
const uuidv4 = uuid.v4

const saveFile = (file,name) => {
    return new Promise((resolve, reject) => {
        uploadPath = setting.PROJECT_DIR + '/files/';
        file.mv(uploadPath+name, function(err) {
            if (err) {
              reject(err)
            }
            resolve(name);   
          });
    })
}
function getMessage(bytes){
  var bStr = new Uint8Array(bytes);
  var string = new util.TextDecoder("utf-8").decode(bStr);
  if(string.length>3){
      if(!string.includes('@!#')) return false;
  }
  if(string.length>120){
       return false;
  }
  if(string.includes('@!#')&&string.includes('#!@')){
      string = string.substring(0,string.indexOf('#!@'))
      string = string.substr(3);
      return string;
  }
  return null;
}
function md5Bitmap(img,beginRow){
  var nImg = img.clone();
  nImg.crop(0,beginRow,img.bitmap.width,img.bitmap.height-beginRow);
  return md5(nImg.bitmap.data);
}

function getHiddenData(message){
  var hash = message.substr(message.indexOf('___')+3);
  var text = message.substring(0,message.indexOf('___'));
  var info = text.split("__");
  console.log(info);
  
  var gpsArr = info[2].split(",");
  return {
      hash:hash,
      text:text,
      device:info[0],
      time:new Date(Number(info[1])),
      lat: Number(gpsArr[0]),
      long:Number(gpsArr[1])
  }
}
async function handleFile (id,job,done){

  var sImage = await SImage.findById(id);
  var path  = setting.PROJECT_DIR + '/files/'+sImage.name;
  console.log();
  
  Jimp.read(path).then(async (img) =>  {
    var byte = {
        val : 0x00,
        num : 0,
    };
    let ended = false;
     var message = null;
    var bArr=[];
    for (let row = 0; row < img.bitmap.height; row++) {
        if(ended) break;
         for (let col = 0; col < img.bitmap.width; col++) {
            if(ended) break;
            var rgba = Jimp.intToRGBA(img.getPixelColor(col,row));
            for(let ci in colors){
                var cVal2b = rgba[colors[ci]]&1;
                byte.val = (byte.val<<1)|cVal2b;
                byte.num+=1;
                if(byte.num==8){
                    bArr.push(byte.val);
                    byte.val = 0x00;
                    byte.num = 0;
                    message = getMessage(bArr);
                    if(message===false||message){
                        ended = true;
                        break;
                    }
                }
            }
        } 
    }
    if(message){
      console.log(message);
      
        var usingRows = Math.ceil(Buffer.byteLength(message, 'utf8')*8/3/img.bitmap.width);
         var md5Val = md5Bitmap(img,usingRows);
        var data = getHiddenData(message);
        if(data.hash!=md5Val){
          sImage.status = 0;
        }else{
   
          
          sImage.hidden_info = data.text;
          sImage.device = data.device;
          sImage.time = data.time;
     
            sImage.lat_lng.lat = data.lat;
            sImage.lat_lng.lng =data.long;
            sImage.status = 2;
        
           
           
        }
    }else{
        sImage.status = 0;
    } 
    
    await sImage.save();
    done();
  });
}

exports.upload = async (req, res, next) => {
    try {
  if (!req.files || Object.keys(req.files).length === 0) {
    return next(new AppError(200, 'fail', 'Không có file nào'), req, res, next);
  }
  var files = {};
  for(let id in req.files){
    let file = req.files[id];
    let newName = uuidv4()+'___'+file.name
    let path = await  saveFile(file,newName);
    files[id] = path;
  }

  res.status(200).json({
    status: 'success',
    data: files
});
  

    } catch (error) {
        next(error);
    }
};
exports.uploadSte = async (req, res, next) => {
  try {
if (!req.files || Object.keys(req.files).length === 0) {
  return next(new AppError(200, 'fail', 'Không có file nào'), req, res, next);
}
var files = {};
for(let id in req.files){
  let file = req.files[id];
  let newName = uuidv4()+'___'+file.name
  let path = await  saveFile(file,newName);
  var sImage = await SImage.create({
    name:newName
  })
  agenda.define('handle__image__'+newName,{priority: 'high'}, (job, done) => {
    handleFile(sImage._id,job,done);
  });
  (async function() {
    await agenda.start();
    await agenda.schedule('in 0 seconds', 'handle__image__'+newName);
  })();
  // agenda.now('handle__image__'+newName);
  // agenda.start('handle__image__'+newName);
  
  
  
  files[id] = sImage;
}

res.status(200).json({
  status: 'success',
  data: files
});


  } catch (error) {
      next(error);
  }
};

exports.getFile = async (req, res, next) => {
    try {
        var fol = setting.PROJECT_DIR + '/files/';
        var file = req.params.file;
        res.download(fol+file);

  
  

    } catch (error) {
        return next(new AppError(404, 'fail', 'File not found'), req, res, next);
    }
};


