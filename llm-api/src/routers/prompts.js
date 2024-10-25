import express from 'express';

import promptController from '../controllers/promptController.js';
import getRelatedInfo from '../middlewares/getRelatedInfo.js';

const prompts = express.Router();

prompts.post('/query', promptController.promptQuery);
prompts.post('/queryWithRelatedInfo', getRelatedInfo, promptController.promptQueryWithRelatedInfo);

export default prompts;