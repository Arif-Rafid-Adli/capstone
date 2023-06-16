import express from "express";
import { getUser, Register, Login, Logout } from "../controller/Users.js";

const router = express.Router();


router.get("/users", getUser);
router.post("/users", Register);
router.post("/login", Login);
router.delete("/logout", Logout);

export default router;
