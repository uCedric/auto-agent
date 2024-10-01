import express from "express";

import prompts from "./prompts.js";
import chunks from "./chunks.js";

const router = express.Router();

router.use("/spring-prompt", prompts);
router.use("/spring-prompt", chunks);

export default router;