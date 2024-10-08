import express, { json } from "express";
import router from "./src/routers/index.js";
import successHandler from "./src/utils/successHandler.js";

const app = express();

app.use(json());

app.use(successHandler);
// TODO: Add error handler
// app.use(errorHandler);

app.get("/health", (_req, res) => {
    res.success("Server is running");
});

app.use(router);

app.all("*", (req) => {
    throw new NotFoundError(`This route "${req.path}" does not exist!!`);
});
 
export default app;