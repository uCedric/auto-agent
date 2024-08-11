import express, { json } from "express";
import router from "./src/routers/index.js";
import successHandler from "./src/utils/successHandler.js";

const app = express();

app.use(json());

app.get("/health", (_req, res) => {
    res.success("Server is running");
});


app.use(successHandler);
// app.use(errorHandler);
app.use(router);

app.all("*", (req) => {
    throw new NotFoundError(`This route "${req.path}" does not exist!!`);
});
 
export default app;