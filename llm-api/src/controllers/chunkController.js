import chunkService from "../services/chunk.service.js";

const embedChuncks = async (req, res) => {
    console.log(req.body);
    const { chunks } = req.body;

    await chunkService.embedChunks(chunks);

    res.success("success");
}

export default { embedChuncks };