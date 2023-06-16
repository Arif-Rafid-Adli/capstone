import { Sequelize } from "sequelize";

 const db = new Sequelize('auth-db','root','satudarah',{
    host :'34.101.241.24',
    dialect: 'mysql'
})

export default db