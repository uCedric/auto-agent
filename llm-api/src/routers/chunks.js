import express from 'express';

import chunkController from '../controllers/chunkController.js';

const prompts = express.Router();

prompts.post('/chunks', chunkController.embedChuncks);

export default prompts;