import chunkService from "../services/chunk.service.js";

const embedChuncks = async (req, res) => {
    console.log(req.body);
    const { documentUuid, chunks } = req.body;

    await chunkService.embedChunks(documentUuid, chunks);

    res.success("success");
}

export default { embedChuncks };