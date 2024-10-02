import chunkService from "../services/chunk.service.js";

const embedChuncks = async (req, res) => {
    const { chunks } = req.body;

    const embedRes = await chunkService.embedChunks(chunks);

    res.success(embedRes);
}

export default { embedChuncks };