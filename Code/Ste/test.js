require('datejs');
const connectionString = "mongodb+srv://datnd:29091997q@cluster0-omy66.mongodb.net/SignboardMn?retryWrites=true&w=majority";
const Agenda = require('agenda');
const agenda = new Agenda({db: {address: connectionString}});
function getNextDay(nextdayLst){
    var date = Date.today().addDays(10);
    if(nextdayLst){
        console.log('a');
        
        for (let index = 0; index < 31; index++) {
            for(var d in nextdayLst){
                if(date.getDate()==nextdayLst[d]){
                    return date;
                }
            }
            date.addDays(1);
        }
    }
    return null;
}

// console.log(getNextDay([2,3,4,5,15]));

function randomIntFromInterval(min, max) { // min and max included 
    return Math.floor(Math.random() * (max - min + 1) + min);
  }

// console.log(new Date(randomIntFromInterval(1594049340000,1594052940000)).toString('dd/MM/yyyy HH:mm'));

// agenda.define('abc', (job, done) => {
//    console.log('a');
// //    done();
//   });
  
//   (async function() {
//     await agenda.start();
//     await agenda.schedule('in 0 seconds', 'abc');
//   })();

var t ={
    id:"df",
    report:[{
        pl_id:"fd",
        confirm:true,
        note:"sdf"}
    ]
}
console.log(JSON.stringify(t));
