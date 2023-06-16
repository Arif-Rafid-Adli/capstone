import Users from "../models/UserModel.js";
import bcrypt from "bcrypt"

export const getUser = async(req, res) => {
    try {
        const users = await Users.findAll({

            attributes : ['id','name','email']
        })
        res.json(users)
    } catch (error) {
        console.log(error)
    }
}

export const Register= async(req, res) => {
    const Users = await Users.findOne ({
        where:{
            email : req.body.email
        }
    })
    if (Users) return res.status(400).json({msg:"User alredy register"})

    const { name, email, password, confirmpassword} = req.body
    if (password !== confirmpassword) return res.status(400).json({msg:"Password dan Confirm Password tidak cocok"})
    const salt = await bcrypt.genSalt()
    const hashPassword = await bcrypt.hash(password, salt)
    try {
        await Users.create({
            name:name,
            email:email,
            password:hashPassword
        })
        res.json({msg:"Registrasi berhasil"})
        
    } catch (error) {
        console.log(error)
    }
}

export const Login = async(req, res) =>{
    try {
        const user = await Users.findAll({
            where:{
                email:req.body.email
            }
        })
        const match = await bcrypt.compare(req.body.password, user[0].password )
        if(!match) return res.status(400).json({msg:"Wrong Password"})
        
        res.json({msg :"Anda berhasil login"})
    } catch (error) {
        res.status(404).json({msg:"Email atau Password tidak ditemukan"})
    }
}

export const Logout = async (req, res ) => {
    const refreshToken = req.cookies.refreshToken
        if (!refreshToken) return res.sendStatus(204)
        const user = await Users.findAll({
            where:{
                refresh_token : refreshToken
            }
        })
        if (!user[0]) return res.sendStatus(204)
        const userId = user[0].id
        await Users.update ({ refresh_token:null},{
            where:{
                id:userId
            }
        })
        res.clearCookie("refreshToken")
        return res.sendStatus(200)
}