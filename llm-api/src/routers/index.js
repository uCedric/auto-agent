import express from "express";

import prompt from "./prompts.js";

const router = express.Router();

router.use("/spring-prompt", prompt);

export default router;