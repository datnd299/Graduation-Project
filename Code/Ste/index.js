
const colors=['r','g','b'];
const Jimp = require('jimp');
const md5 = require('md5');
const {gzip, ungzip} = require('node-gzip');
function getMessage(bytes){
    var bStr = new Uint8Array(bytes);
    var string = new TextDecoder("utf-8").decode(bStr);
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
    var gpsArr = info[2].split(",");
    return {
        hash:hash,
        text:text,
        device:info[0],
        time:new Date(1593350106367),
        lat: Number(gpsArr[0]),
        long:Number(gpsArr[1])
    }
}

Jimp.read('dat2.png').then(img => {
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
        var usingRows = Math.ceil(Buffer.byteLength(message, 'utf8')*8/3/img.bitmap.width);
        var md5Val = md5Bitmap(img,usingRows);
        var data = getHiddenData(message);
        if(data.hash!=md5Val){
            console.log("err"); 
        }else{
            console.log(data);
        }
    }else{
        console.log("err");
    } 
  });