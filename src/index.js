import express from "express";
import dotenv from "dotenv"
import db from "./config/database.js";
import router from "./routes/record.js";
dotenv.config()
const app = express();

try {
  await db.authenticate();
  console.log("Database is Connected...");
} catch (error) {
  console.log(error);
}

app.use(express.json());
app.use(router);

app.get ("/", (req, res) =>{
  console.log ("Response Success")
  res.send("Response Success")
})

const PORT = process.env.PORT || 9000
app.listen(PORT, () => {
    console.log("Server is up and listening on " + PORT)
})
