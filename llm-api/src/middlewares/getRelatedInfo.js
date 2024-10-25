import { initializeEmbed } from '../utils/embedInit.js';
import chunkModel from '../models/chunkModel.js';

export default async (req, res, next) => {
    const { content } = req.body;

    //vectorize prompt
    const embedInstance = await initializeEmbed();
    const { embedding: promptVector } = await embedInstance.embedToVector(content);
    //get related info(chunks)
    const contents = await chunkModel.getRelatedChunks(promptVector);

    res.locals.relatedInfo = [...res.locals.relatedInfo, ...contents];
    
    next();
};