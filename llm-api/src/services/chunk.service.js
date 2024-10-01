import embedConn from "../utils/embedInit.js";
import chunkModel from "../models/chunkModel.js";

const embedChunks = async (chunks) => {
    const embedInstance = await embedConn.getInstance();

    const vectors = await embedInstance.embedToVector(chunks);

    for(let vector of vectors){
        await chunkModel.addChunk({vector});
    }
    
    return vectors;
};

export default { embedChunks };