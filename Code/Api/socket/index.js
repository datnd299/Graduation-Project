
const jwt = require('jsonwebtoken');
const Account = require('../models/user/account');
const PartyA = require('../models/user/partyA');
const PartyB = require('../models/user/partyB');
const Socket = require('../models/access/socket');
const Room = require('../models/chat/room');
const Message = require('../models/chat/message');
const {
	promisify
} = require('util');

var ioEvents = function (io) {


	// Chatroom namespace
	io.on('connection', function (socket) {
		
		
		socket.acc = null;
		socket.on('authenticate', async function (data) {
			var token = data.token;
			if (token) {
				const decode = await promisify(jwt.verify)(token, process.env.JWT_SECRET);
				if (decode) {
					const acc = await Account.findById(decode.id);
					if (acc) {
							socket.acc = acc;
							var nAccSocket = await Socket.create({
								acc: acc._id,
								socket:socket.id
							})

						
					}
				}
			}
		});

		socket.on('newMessage', async function (data) {


			if (socket.acc.role.includes('partyA')) {
				if (data.to_pt) {
					const ptA = await PartyA.findOne({ "accs": socket.acc._id });
					const ptB = await PartyB.findById(data.to_pt) ;
					let room = await Room.findOne({ pt_a: ptA, pt_b: ptB });
					if (room) {
					} else {
						room = await Room.create({ pt_a: ptA, pt_b: ptB })
					}
					var mess = await Message.create({
						room:room,
						sender: socket.acc,
						content: data.content,
						type: data.type
					})
					var accs =[];
					ptA.accs.forEach(e=>{
						accs.push(e);
					})
					accs.push(ptB.accs)
					
					
					
					 var socks = await Socket.find({acc: {$in: accs}});
					 mess =mess.toObject();
					 socks.forEach(e => {
						 
						 if(e.acc.toString()==socket.acc._id.toString()){
							 mess.s = 'me'
				
							 
						 }else{
					
							mess.s = null
						 }
						io.to(e.socket).emit('newMessageSended',mess);
					 });
					 
					 
					
				}




			}else if(socket.acc.role.includes('partyB')){
				if (data.to_pt) {
					const ptB = await PartyB.findOne({ "accs": socket.acc._id });
					const ptA = await PartyA.findById(data.to_pt) ;
					let room = await Room.findOne({ pt_a: ptA, pt_b: ptB });
					if (room) {
					} else {
						room = await Room.create({ pt_a: ptA, pt_b: ptB })
					}
					 var mess = await Message.create({
						room:room,
						sender: socket.acc,
						content: data.content,
						type: data.type
					})
					var accs =[];
					ptA.accs.forEach(e=>{
						accs.push(e);
					})
					accs.push(ptB.accs)
					
					
					
					 var socks = await Socket.find({acc: {$in: accs}});
					 mess =mess.toObject();
					 socks.forEach(e => {
						
				
							
							if(e.acc.toString()==socket.acc._id.toString()){
								mess.s = 'me'
				   
								
							}else{
					   
							   mess.s = null
							}
						io.to(e.socket).emit('newMessageSended',mess);
					 });
				}
			} else {
				console.log('unauthenticated');

				socket.emit('unauthenticated', 'unauthenticated');
			}

		});

		// setTimeout(function(){
		// 	if (!socket.acc) {
		// 	  console.log("Disconnecting socket ", socket.id);
		// 	  socket.disconnect('unauthenticated');
		// 	}
		//   }, 2000);

		socket.on('disconnect', async function () {
			
			console.log(socket.id+' - disconnected');
			

			await Socket.deleteOne({socket:socket.id});
			// if (socket.acc) {
			// 	const accSocket = await Socket.findOne({ acc: socket.acc.id });
			// 	for (var i in accSocket.sockets) {
			// 		if (accSocket.sockets[i].id == socket.id) {
			// 			accSocket.sockets.splice(i, 1);
			// 		}
			// 	}
			// 	await accSocket.save();
			// }


		});




	});
}



var init = function (app) {

	var server = require('http').Server(app);
	var io = require('socket.io')(server, { origins: '*:*' });
	io.set('transports', ['websocket']);






	// io.use((socket, next) => {
	// 	require('../session')(socket.request, {}, next);
	// });


	ioEvents(io);


	return server;
}

module.exports = init;