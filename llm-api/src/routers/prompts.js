import express from 'express';

import promptController from '../controllers/promptController.js';

const prompts = express.Router();

prompts.post('/query', promptController.promptQuery);

export default prompts;