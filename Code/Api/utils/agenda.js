const connectionString = process.env.DATABASE;
const Agenda = require('agenda');
const agenda = new Agenda({db: {address: connectionString}});

module.exports = agenda;