var setting = require("./../../setting");

const AppError = require('./../../utils/appError');


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

exports.getFile = async (req, res, next) => {
    try {
        var fol = setting.PROJECT_DIR + '/files/';
        var file = req.params.file;
        res.download(fol+file);

  
  

    } catch (error) {
        return next(new AppError(404, 'fail', 'File not found'), req, res, next);
    }
};


