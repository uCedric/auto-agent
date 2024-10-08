import embedConn from "../utils/embedInit.js";
import chunkModel from "../models/chunkModel.js";

const embedChunks = async (documentUuid, chunks) => {
    const embedInstance = await embedConn.getInstance();

    let embededObjects = [];
    for(let chunk of chunks){
        const vector = await embedInstance.embedToVector(chunk);
        embededObjects = [...embededObjects, {vector: vector.embedding, chunk}];
    }
    
    for(let object of embededObjects){
        await chunkModel.addChunk(documentUuid, object.vector, object.chunk);
    }
    
    return embededObjects;
};

export default { embedChunks };